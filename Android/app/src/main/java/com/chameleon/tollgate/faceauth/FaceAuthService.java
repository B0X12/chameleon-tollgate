package com.chameleon.tollgate.faceauth;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.chameleon.tollgate.MainActivity;

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

import java.util.ArrayList;

import static org.opencv.imgproc.Imgproc.COLOR_BGRA2BGR;
import static org.opencv.imgproc.Imgproc.FONT_HERSHEY_SIMPLEX;
import static org.opencv.imgproc.Imgproc.putText;
import static org.opencv.imgproc.Imgproc.rectangle;

public class FaceAuthService {

    public static final String TAG = "OpenCV";
    public static final int m_backCam = 0; // 0: 후면, 1: 전면
    public static final int m_frontCam = 1;
    public static final int m_train = 0;
    public static final int m_auth = 1;

    private static final CascadeClassifier cascade = new CascadeClassifier();
    private static final FaceRecognizer recognize = LBPHFaceRecognizer.create();

    private Mat matInput, matOutput, matFace, matGray, matResized;
    private Context context;
    private static final int m_trainCnt = 60;
    private int m_cnt = 0;
    private ArrayList<Mat> m_trainImages = new ArrayList<>();

    FaceAuthService(Context context){
        this.context = context;

        matInput = new Mat();
        matOutput = new Mat();
        matFace = new Mat();
        matGray = new Mat();
        matResized = new Mat();

        if(isCascade()) loadCascade();
    }


    private boolean loadCascade(){
        if(!isCascade()){
            if(!cascade.load(Environment.getExternalStorageDirectory().getAbsolutePath() + "/haarcascade_frontalface_default.xml")){
                showToast("xml 파일을 찾을 수 없습니다.");
            }
            else{
                showToast("xml 파일을 찾았습니다.");
                return true;
            }
        }
        return false;
    }
    private boolean isCascade(){
        if(!cascade.empty()) return true;
        else return false;
    }
    private void showToast(String text){
        ContextCompat.getMainExecutor(context).execute(()->{
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        });
    }
    private void executeFinish(){
        ContextCompat.getMainExecutor(context).execute(()->{
            ((Activity)context).finish();
        });
    }

    public static Mat getOriginImage(Mat matInput){
        Core.flip(matInput, matInput, 0);
        return matInput;
    }
    public Mat getDetectedFace(Mat matInput, int method){
        if(!isCascade()) loadCascade();

        Core.rotate(matInput, matOutput, Core.ROTATE_90_COUNTERCLOCKWISE);

        Imgproc.cvtColor(matOutput, matGray, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.resize(matGray, matResized, new Size(matOutput.width()/5, matOutput.height()/5));

        MatOfRect faces = new MatOfRect();
        cascade.detectMultiScale(matResized, faces, 1.2, 3, 0, new Size(40, 40));

        if(faces.empty())
            return matInput;

        Rect rc = faces.toList().get(0);

        Mat cropped = new Mat(matResized, new Rect(rc.x, rc.y, rc.width, rc.height));
        Mat saved = new Mat();
        Imgproc.resize(cropped, saved, new Size(200, 200));

        switch (method) {
            case m_train: {
                if (m_trainImages.size() < m_trainCnt) {
                    m_trainImages.add(saved);
                    m_cnt += 1;
                } else {
                    recognize.train(m_trainImages, new Mat().zeros(m_trainCnt, 1, CvType.CV_32SC1));
                    recognize.save(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Images/test.yml");
                    showToast("얼굴을 저장했습니다.");
                    executeFinish();
                }
            }break;
            case m_auth:{
                // 서버로 이미지 전송 => 결과 반환
            }break;
        }

        rc.x *= 5;
        rc.y *= 5;
        rc.width *= 5;
        rc.height *= 5;

        putText(matOutput, (String.format("%.2f",(((float)m_cnt/m_trainCnt)*100)))+"% ", new Point(rc.width/2,rc.height/2), FONT_HERSHEY_SIMPLEX, 1, new Scalar(255,0,0), 4);
         
        rectangle(matOutput, rc, new Scalar(255, 0, 0), 5);
        Imgproc.cvtColor(matOutput, matOutput, COLOR_BGRA2BGR);
        Core.rotate(matOutput, matFace, Core.ROTATE_90_CLOCKWISE);

        return matFace;
    }

}
