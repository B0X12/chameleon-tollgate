package com.chameleon.tollgate.faceauth;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.chameleon.tollgate.R;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.opencv.imgproc.Imgproc.COLOR_BGRA2BGR;
import static org.opencv.imgproc.Imgproc.COLOR_BGRA2GRAY;
import static org.opencv.imgproc.Imgproc.FONT_HERSHEY_COMPLEX;
import static org.opencv.imgproc.Imgproc.FONT_HERSHEY_SIMPLEX;
import static org.opencv.imgproc.Imgproc.putText;
import static org.opencv.imgproc.Imgproc.rectangle;

class FaceException extends Exception{
    FaceException(){
        super();
    }
    FaceException(String message){
        super(message);
    }
}

public class FaceAuthService implements FaceService {

    private Mat matInput, matOutput, matFace, matGray, matResized;
    private int m_cnt = 0;

    private Context context;
    private ArrayList<Mat> m_trainImages = new ArrayList<>();
    public CascadeClassifier cascade;
    private final FaceRecognizer recognize = LBPHFaceRecognizer.create();

    public FaceAuthService(Context context){
        this.context = context;

        matInput = new Mat();
        matOutput = new Mat();
        matFace = new Mat();
        matGray = new Mat();
        matResized = new Mat();

        try {
            loadCascade();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showToast(String text){
        ContextCompat.getMainExecutor(context).execute(()->{
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        });
    }
    public void executeFinish(){
        ContextCompat.getMainExecutor(context).execute(()->{
            ((Activity)context).finish();
        });
    }

    public int getM_cnt() {
        return m_cnt;
    }

    @Override
    public boolean loadCascade() throws IOException {
            // 파일 데이터 읽어오기
            InputStream is = context.getResources().openRawResource(R.raw.haarcascade_frontalface_default);
            // 폴더 생성
            File cascadeDir = context.getDir("cascade", Context.MODE_PRIVATE);
            // 생성한 폴더 아래에 파일  생성
            File mCascadeFile = new File(cascadeDir, "haarcascade_frontalface_default.xml");
            // 파일에 데이터를 쓰기 위한 OutputStream 객체 생성
            FileOutputStream os = new FileOutputStream(mCascadeFile);

            // 파일에 데이터 쓰기
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();

            // haarcascade_frontalface_default.xml 읽어오기
            cascade = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            // 파일을 읽어오지 못했으면 false 반환
            if (cascade.empty()) {
                Log.e(TAG, "Failed to load cascade classifier");
                cascade = null;
                return false;
            } else {
                Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());
            }

        mCascadeFile.delete();
        cascadeDir.delete();

        return true;
    }


    public static Mat getOriginImage(Mat matInput){
        Core.flip(matInput, matInput, 0);
        return matInput;
    }
    @Override
    public Rect detectFace(Mat matInput) throws FaceException {

        Core.rotate(matInput, matOutput, Core.ROTATE_90_COUNTERCLOCKWISE);

        // 흑백 변환
        Imgproc.cvtColor(matOutput, matGray, Imgproc.COLOR_BGRA2GRAY);
        // 크기 변경
        Imgproc.resize(matGray, matResized, new Size(matOutput.width()/5, matOutput.height()/5));

        MatOfRect faces = new MatOfRect();
        cascade.detectMultiScale(matResized, faces, 1.2, 3, 0, new Size(40, 40));

        if(faces.empty())
            return null;

        Rect rc = faces.toList().get(0);
        rc.x *= 5;
        rc.y *= 5;
        rc.width *= 5;
        rc.height *= 5;
        return rc;
    }

    @Override
    public Mat drawRect(Mat matInput, Rect rc, boolean isPercentage){
        Core.rotate(matInput, matOutput, Core.ROTATE_90_COUNTERCLOCKWISE);
        if(isPercentage)
            putText(matOutput, (String.format("%.2f",(((float)m_cnt/m_trainCnt)*100)))+"% ", new Point(rc.x,rc.y), FONT_HERSHEY_SIMPLEX, 1, new Scalar(255,0,0), 4);

        rectangle(matOutput, rc, new Scalar(255, 0, 0), 5);
        Imgproc.cvtColor(matOutput, matOutput, COLOR_BGRA2BGR);
        Core.rotate(matOutput, matFace, Core.ROTATE_90_CLOCKWISE);
        return matFace;
    }


    public void addTrainFaceImage(Mat matInput, Rect rc) throws FaceException{
        if(!(m_cnt < m_trainCnt)) {
            throw new FaceException("더 이상 이미지를 추가할 수 없습니다.");
        }

        Core.rotate(matInput, matOutput, Core.ROTATE_90_COUNTERCLOCKWISE);
        Imgproc.cvtColor(matOutput, matOutput, COLOR_BGRA2GRAY);
        // 얼굴만 이미지 자르기
        Mat cropped = new Mat(matOutput, new Rect(rc.x, rc.y, rc.width, rc.height));
        Mat saved = new Mat();
        // 얼굴만 자른 이미지를 학습을 위해 크기를 변경한다.
        Imgproc.resize(cropped, saved, new Size(200, 200));


        m_trainImages.add(saved);
        m_cnt += 1;
    }

    public String file2SHA512String(String path) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("sha-512");
        FileInputStream fIS = new FileInputStream(path);

        byte[] dataBytes = new byte[4096];
        int nRead = 0;
        while((nRead = fIS.read(dataBytes)) != -1)
            md.update(dataBytes, 0, nRead);

        byte[] mdBytes = md.digest();

        StringBuilder strBuilder = new StringBuilder();
        for (byte b : mdBytes){
            strBuilder.append(String.format("%02x",b));
        }

        return strBuilder.toString();
    }

    public String getModelPath(){
        // 폴더 생성
        File cascadeDir = context.getDir("cascade", Context.MODE_PRIVATE);
        // 생성한 폴더 아래에 파일  생성
        File mCascadeFile = new File(cascadeDir, "trainedFace.yml");

        return mCascadeFile.getAbsolutePath();
    }

    @Override
    public boolean trainFace() throws FaceException, NoSuchAlgorithmException, IOException {

        if(m_trainImages.size() == m_trainCnt){
            Log.w("testtest", m_trainImages.size()+", ");
            recognize.train(m_trainImages, new Mat().zeros(m_trainImages.size(), 1, CvType.CV_32SC1));

            // 폴더 생성
            File cascadeDir = context.getDir("cascade", Context.MODE_PRIVATE);
            // 생성한 폴더 아래에 파일  생성
            File mCascadeFile = new File(cascadeDir, "trainedFace.yml");
            Log.d("testtest", "path : "+mCascadeFile.getAbsolutePath());

            recognize.save(mCascadeFile.getAbsolutePath());

            String hashValue = file2SHA512String(mCascadeFile.getAbsolutePath());

            FaceRestTask faceRest = new FaceRestTask("https://192.168.0.17:8080/register/face/tester", hashValue, ((Activity)context).getBaseContext());
            faceRest.execute();

            return true;
        }
        else
            throw new FaceException("학습시킬 이미지를 충분히 입력하지 않았습니다.");
    }

    @Override
    public Mat recogFace(Mat matInput, Rect rc) throws MalformedURLException {
        Core.rotate(matInput, matOutput, Core.ROTATE_90_COUNTERCLOCKWISE);
        Imgproc.cvtColor(matOutput, matGray, COLOR_BGRA2GRAY);
        // 얼굴만 이미지 자르기
        Mat cropped = new Mat(matGray, new Rect(rc.x, rc.y, rc.width, rc.height));
        Mat resized = new Mat();
        // 얼굴만 자른 이미지를 학습을 위해 크기를 변경한다.
        Imgproc.resize(cropped, resized, new Size(200, 200));

        if(recognize.empty())
            recognize.read(getModelPath());


        int label[] = new int[1];
        double predict[] = new double[1];
        recognize.predict(resized, label, predict);

        System.out.println(predict[0]);

        if(label[0] != -1 && (int)predict[0] < 55){
            putText(matOutput, "you are user", new Point(rc.x, rc.y), FONT_HERSHEY_COMPLEX, 1, new Scalar(0, 250, 150), 4);

            FaceRestTask faceRest = new FaceRestTask("https://192.168.0.17:8080/auth/face/tester", "true", ((Activity)context).getBaseContext());
            faceRest.execute();
            executeFinish();
        }
        else{
            putText(matOutput, "who are you?", new Point(rc.x, rc.y), FONT_HERSHEY_COMPLEX, 1, new Scalar(250, 100,  0), 4);
        }

        Imgproc.cvtColor(matOutput, matOutput, COLOR_BGRA2BGR);
        Core.rotate(matOutput, matInput, Core.ROTATE_90_CLOCKWISE);
        return matInput;
    }

}
