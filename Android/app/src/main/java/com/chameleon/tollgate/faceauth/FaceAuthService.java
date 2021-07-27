package com.chameleon.tollgate.faceauth;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
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
    private FaceVar.ActivationMode mode;
    private static Context context;
    private Handler handler;

    private Rect rcFace;
    private Mat OriginImage, PortraitImage, GrayImage, GrayResizedImage, CroppedImage, CropResizedImage;
    private ArrayList<Mat> trainImages;

    private CascadeClassifier cascade;
    private FaceRecognizer recognizer;

    private String faceDir, cascadeName, modelName;
    private Scalar rectColor;

    public FaceAuthService(FaceVar.ActivationMode mode, Context context, Handler handler){
        this.mode = mode;
        this.context = context;
        this.handler = handler;

        this.rcFace = new Rect();
        this.trainImages = new ArrayList<>();

        this.cascade = new CascadeClassifier();
        this.recognizer = LBPHFaceRecognizer.create();

        this.faceDir = "faceauth";
        this.cascadeName = "haar_frontalface.xml";
        this.modelName = "trainedFace.fa";

        this.OriginImage = new Mat();
        this.PortraitImage = new Mat();
        this.GrayImage = new Mat();
        this.GrayResizedImage = new Mat();
        this.CroppedImage = new Mat();
        this.CropResizedImage = new Mat();

        this.rectColor = new Scalar(255,0,0);

        loadCascade();
    }

    public static Mat getOriginImage(@NonNull Mat matImage){
        // 뒤집혀 있는 이미지를 flip 한다.
        Core.flip(matImage, matImage, 0);
        return matImage;
    }

    public boolean loadCascade() {
        // 이미 얼굴 검출 모델이 로드되었을 경우
        if(!cascade.empty()){
            Log.d(FaceVar.TAG, "loadCascade : Face Detection Model Already Loaded.");
            return true;
        }

        InputStream is = null;
        FileOutputStream os = null;

        is = context.getResources().openRawResource(R.raw.haarcascade_frontalface_default);
        File faceauthDir = context.getDir(faceDir, Context.MODE_PRIVATE);
        File cascadeFile = new File(faceauthDir, cascadeName);
        try {
            os = new FileOutputStream(cascadeFile);

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
            Log.d(FaceVar.TAG, "loadCascade : Face Detection Model Load Failed");
            cascade = null;
            return false;
        } else
            Log.d(FaceVar.TAG, "loadCascade : Face Detection Model Loaded");

        if(cascadeFile != null)
            cascadeFile.delete();

        // 성공 시 true
        return true;
    }


    public void setImage(@NonNull Mat matInput) {
        this.OriginImage = matInput;
        Core.rotate(OriginImage, PortraitImage, Core.ROTATE_90_COUNTERCLOCKWISE);
        Imgproc.cvtColor(PortraitImage, GrayImage, Imgproc.COLOR_BGRA2GRAY);
        Imgproc.resize(GrayImage, GrayResizedImage, new Size(PortraitImage.width() / 5, PortraitImage.height() / 5));
    }

    public boolean preprocessImage() {
        rcFace = getFaceRect();

        if(rcFace != null) {
            CroppedImage = new Mat(GrayImage, new Rect(rcFace.x, rcFace.y, rcFace.width, rcFace.height));
            Imgproc.resize(CroppedImage, CropResizedImage, new Size(200, 200));

            if(mode.equals(FaceVar.ActivationMode.TRAIN))
                addCurrnentImage2List();

            return true;
        }
        Log.d(FaceVar.TAG, "preprocessImage : Image Preprocessing Failed");
        return false;
    }

    public boolean isTrainPossible(){
        if(trainImages.size() == FaceVar.TRAIN_NUM) return true;
        else return false;
    }

    private Rect getFaceRect(){
        MatOfRect faceRect = new MatOfRect();
        if(cascade.empty())
            loadCascade();

        cascade.detectMultiScale(GrayResizedImage, faceRect, 1.2, 3, 0, new Size(40, 40));

        if(faceRect.empty()){
            Log.d(FaceVar.TAG, "getFaceRect : Face Not Found");
            return null;
        }

        Rect rc = faceRect.toList().get(0);
        rc.x *= 5;
        rc.y *= 5;
        rc.width *= 5;
        rc.height *= 5;

        return rc;
    }

    public Mat getFaceWithRectImage(){
        if(PortraitImage.empty()) {
            Log.d(FaceVar.TAG, "getFaceWithRectImage : PortraitImage is null");
            return null;
        }

        Mat matOutput = PortraitImage;
        if(rcFace!=null) {
            if (mode.equals(FaceVar.ActivationMode.TRAIN))
                putText(matOutput, (String.format("%.2f", (((float) trainImages.size() / FaceVar.TRAIN_NUM) * 100))) + "% ", new Point(rcFace.x, rcFace.y), FONT_HERSHEY_SIMPLEX, 1, rectColor, 4);

            rectangle(matOutput, rcFace, rectColor, 5);
        }
        Imgproc.cvtColor(matOutput, matOutput, COLOR_BGRA2BGR);
        Core.rotate(matOutput, matOutput, Core.ROTATE_90_CLOCKWISE);
        return matOutput;
    }

    public boolean addCurrnentImage2List() {
        if(!(trainImages.size() < FaceVar.TRAIN_NUM)) {
            return false;
        }

        trainImages.add(CropResizedImage);
        return true;
    }

    public String trainFace() {
        if(isTrainPossible()){
            File faceauthDir = context.getDir(faceDir, Context.MODE_PRIVATE);
            File mFaceModel = new File(faceauthDir, modelName);

            recognizer.train(trainImages, Mat.zeros(trainImages.size(), 1, CvType.CV_32SC1));
            recognizer.save(mFaceModel.getAbsolutePath());

            String hashValue = file2SHA512String(mFaceModel.getAbsolutePath());

            if(hashValue != null)
                return hashValue;
        }
        return null;
    }

    public boolean isUser() {
        if(recognizer.empty())
            recognizer.read(getModelPath());

        int label[] = new int[1];
        double predict[] = new double[1];
        recognizer.predict(CropResizedImage, label, predict);

        if(label[0] != -1 && (int)predict[0] < FaceVar.DISTANCE){
            Log.d(FaceVar.TAG, "isUser : Face Matched");
            return true;
        }
        return false;
    }

    public static String file2SHA512String(@NonNull String path) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("sha-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        try {
            System.out.println("testtest:  "+path);
            File file = new File(path);

            if(file.exists()){
                System.out.println("testtest");
            }
            else{
                System.out.println("testtestjffffffjfskldjflskdfjlsjkdfj");
            }

            FileInputStream fIS = new FileInputStream(path);

            byte[] dataBytes = new byte[4096];
            int nRead;
            while ((nRead = fIS.read(dataBytes)) != -1)
                md.update(dataBytes, 0, nRead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] mdBytes = md.digest();

        StringBuilder strBuilder = new StringBuilder();
        for (byte b : mdBytes){
            strBuilder.append(String.format("%02x",b));
        }

        return strBuilder.toString();
    }

    public String getModelPath(){
        File cascadeDir = context.getDir(faceDir, Context.MODE_PRIVATE);
        File mCascadeFile = new File(cascadeDir, "trainedFace.fa");

        if(mCascadeFile.exists())
            return mCascadeFile.getAbsolutePath();
        return null;
    }

}
