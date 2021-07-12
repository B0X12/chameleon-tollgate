package com.chameleon.tollgate.faceauth;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.opencv.imgproc.Imgproc.COLOR_BGRA2BGR;
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

public class FaceAuthService {

    public static final String TAG = "OpenCV";
    public static final int m_backCam = 0; // 0: 후면, 1: 전면
    public static final int m_frontCam = 1;
    public static final int m_train = 0;
    public static final int m_auth = 1;

    public int m_trainCnt = 50;

    private Mat matOrigin, matPortrait, matGray, matGrayResized, matFace, matCropped, matCropResized, matOutput;
    private Rect rcFace;

    private int m_cnt = 0;

    private Context context;
    private ArrayList<Mat> m_trainImages = new ArrayList<>();
    public CascadeClassifier cascade;
    private final FaceRecognizer recognize = LBPHFaceRecognizer.create();

    public FaceAuthService(Context context){
        this.context = context;
        rcFace = new Rect();
        matPortrait = new Mat();
        matFace = new Mat();
        matGray = new Mat();
        matGrayResized = new Mat();
        matOutput = new Mat();
        matCropped = new Mat();
        matCropResized = new Mat();
        cascade = new CascadeClassifier();

        loadCascade();
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
        return m_trainImages.size();
    }
    public void setM_trainCnt(@NonNull int m_trainCnt) {
        this.m_trainCnt = m_trainCnt;
    }

    public Mat getOriginImage(@NonNull Mat matInput){
        // 뒤집혀 있는 이미지를 flip 한다.
        Core.flip(matInput, matInput, 0);
        return matInput;
    }

    public void setImage(@NonNull Mat matOrigin){
        this.matOrigin = matOrigin;
        Core.rotate(matOrigin, matPortrait, Core.ROTATE_90_COUNTERCLOCKWISE);
        Imgproc.cvtColor(matPortrait, matGray, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.resize(matGray, matGrayResized, new Size(matPortrait.width()/5, matPortrait.height()/5));

    }

    public boolean loadCascade() {
        // 이미 얼굴 검출 모델이 로드되었을 경우

        if(!cascade.empty()){
            Log.d(TAG, "Face Detection Model Already Loaded.");
            return true;
        }

        InputStream is = null;
        FileOutputStream os = null;

        // 얼굴 검출 모델 데이터 읽어오기
        is = context.getResources().openRawResource(R.raw.haarcascade_frontalface_default);

        // 내부 저장소에 폴더 생성
        File faceauthDir = context.getDir("facauth", Context.MODE_PRIVATE);
        // 생성한 폴더 아래에 파일  생성
        File cascadeFile = new File(faceauthDir, "haarcascade_frontalface_default.xml");

        try {
            // 파일에 데이터를 쓰기 위한 OutputStream 객체 생성
            os = new FileOutputStream(cascadeFile);

            // 파일에 데이터 쓰기
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        cascade = new CascadeClassifier(cascadeFile.getAbsolutePath());

        // 파일을 읽어오지 못했으면 false 반환
        if (cascade.empty()) {
            Log.d(TAG, "Face Detection Model Load Failed");
            cascade = null;
            return false;
        } else
            Log.d(TAG, "Face Detection Model Loaded");

        if(cascadeFile != null)
            cascadeFile.delete();
        if(faceauthDir != null)
            faceauthDir.delete();

        // 성공 시 true
        return true;
    }

    public boolean preprocessImage() {
        rcFace = getFaceRect();

        if(rcFace!=null) {
            matCropped = new Mat(matGray, new Rect(rcFace.x, rcFace.y, rcFace.width, rcFace.height));
            Imgproc.resize(matCropped, matCropResized, new Size(200, 200));
            return true;
        }
        return false;
    }

    private Rect getFaceRect(){
        MatOfRect faceRect = new MatOfRect();
        cascade.detectMultiScale(matGrayResized, faceRect, 1.2, 3, 0, new Size(40, 40));

        if(faceRect.empty()){
            Log.d(TAG, "얼굴을 찾을 수 없습니다.");
            return null;
        }

        Rect rc = faceRect.toList().get(0);
        rc.x *= 5;
        rc.y *= 5;
        rc.width *= 5;
        rc.height *= 5;

        return rc;
    }

    public Mat getFaceImage(boolean isPercentage){
        if(matPortrait.empty())
            return null;

        Mat matImage = matPortrait;
        if(rcFace!=null) {
            if (isPercentage && rcFace != null)
                putText(matImage, (String.format("%.2f", (((float) m_trainImages.size() / m_trainCnt) * 100))) + "% ", new Point(rcFace.x, rcFace.y), FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 0, 0), 4);

            rectangle(matImage, rcFace, new Scalar(255, 0, 0), 5);
        }
        Imgproc.cvtColor(matImage, matImage, COLOR_BGRA2BGR);
        Core.rotate(matImage, matOutput, Core.ROTATE_90_CLOCKWISE);
        return matOutput;

    }

    public boolean addCurrnentImage2List() {
        if(!(m_trainImages.size() < m_trainCnt)) {
            return false;
        }

        m_trainImages.add(matCropResized);
        return true;
    }

    public boolean trainFace() {
        if(m_trainImages.size() != m_trainCnt)
            return false;
        else{
            File faceauthDir = context.getDir("faceauth", Context.MODE_PRIVATE);
            File mFaceModel = new File(faceauthDir, "trainedFace.fa");

            recognize.train(m_trainImages, new Mat().zeros(m_trainImages.size(), 1, CvType.CV_32SC1));
            recognize.save(mFaceModel.getAbsolutePath());

            String hashValue = null;
            try {
                hashValue = file2SHA512String(mFaceModel.getAbsolutePath());
            }catch (IOException | NoSuchAlgorithmException e){
                e.printStackTrace();
                return false;
            }

            // 서버에 해시 전송
            FaceRestTask faceRest = null;
            try {
                faceRest = new FaceRestTask("https://192.168.0.17:8080/register/face/tester", hashValue, ((Activity)context).getBaseContext());
                faceRest.execute();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            }

            showToast("얼굴을 성공적으로 등록하였습니다.");
            executeFinish();
            return true;
        }
    }

    public Mat recogFace() {
        if(recognize.empty())
            recognize.read(getModelPath());

        int label[] = new int[1];
        double predict[] = new double[1];
        recognize.predict(matCropResized, label, predict);

        Mat matImage = matPortrait;
        if(label[0] != -1 && (int)predict[0] < 55){
            putText(matImage, "you are user", new Point(rcFace.x, rcFace.y), FONT_HERSHEY_COMPLEX, 1, new Scalar(0, 250, 150), 4);

            FaceRestTask faceRest = null;
            try {
                faceRest = new FaceRestTask("https://192.168.0.17:8080/auth/face/tester", "true", ((Activity)context).getBaseContext());
                faceRest.execute();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            showToast("인증되었습니다.");
            executeFinish();
        }
        else{
            putText(matImage, "who are you?", new Point(rcFace.x, rcFace.y), FONT_HERSHEY_COMPLEX, 1, new Scalar(250, 100,  0), 4);
        }

        Imgproc.cvtColor(matImage, matImage, COLOR_BGRA2BGR);
        Core.rotate(matImage, matOutput, Core.ROTATE_90_CLOCKWISE);
        return matOutput;
    }

    public String file2SHA512String(@NonNull String path) throws NoSuchAlgorithmException, IOException {
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
        File cascadeDir = context.getDir("faceauth", Context.MODE_PRIVATE);
        // 생성한 폴더 아래에 파일  생성
        File mCascadeFile = new File(cascadeDir, "trainedFace.fa");

        return mCascadeFile.getAbsolutePath();
    }

}
