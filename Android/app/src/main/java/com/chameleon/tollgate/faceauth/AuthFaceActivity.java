package com.chameleon.tollgate.faceauth;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;

import com.chameleon.tollgate.R;
import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.faceCode;
import com.chameleon.tollgate.util.tollgateLog.tollgateLog;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.CAMERA;

public class AuthFaceActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    int mSec = 0;
    Context context = this;


    private FaceAuthService mFaceAS;
    private CameraBridgeViewBase mCameraView;

    private String hashValue;
    private String timestamp;
    private FaceVar.ActivationMode mode;
    private boolean timeout = false;


    private BaseLoaderCallback m_LoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    mCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case FaceMsg.TOAST_MSG:
                    Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case FaceMsg.TOAST_ERROR:
                    Toast.makeText(getApplicationContext(), "Error : " + (String)msg.obj, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_face);

        tollgateLog.i(LogFactor.FACE, faceCode.START_AUTH);


        Intent parentIntent = getIntent();
        if(parentIntent != null){
            String activationMode = parentIntent.getStringExtra("mode");

            if(activationMode == null) {
                tollgateLog.e(LogFactor.FACE, faceCode.UNKNOWN_AUTH);
                Log.d(FaceVar.TAG, "onCreate : Unknown Activation Mode");
                finish();
            }
            else if(activationMode.compareTo("train") == 0){
                tollgateLog.i(LogFactor.FACE, faceCode.TRAIN_AUTH);
                Log.d(FaceVar.TAG, "onCreate : Start With Train Mode");
                mode = FaceVar.ActivationMode.TRAIN;
                hashValue = null;
                timestamp = null;
                mFaceAS = new FaceAuthService(mode, this, handler);
            }
            else if(activationMode.compareTo("auth") == 0){
                tollgateLog.i(LogFactor.FACE, faceCode.AUTH_AUTH);
                Log.d(FaceVar.TAG, "onCreate : Start With Authentication Mode");
                mode = FaceVar.ActivationMode.AUTH;
                hashValue = parentIntent.getStringExtra("hashValue");
                timestamp = parentIntent.getStringExtra("timestamp");
                mFaceAS = new FaceAuthService(mode, this, handler);

                String modelPath = mFaceAS.getModelPath();

                if(modelPath == null){
                    tollgateLog.e(LogFactor.FACE, faceCode.FACE_NOT_FOUND);
                    Log.d(FaceVar.TAG, "onCreate : Face Model file not found");
                    FaceRestTask faceRest
                            = new FaceRestTask(new FacePack(hashValue, "auth", false, Long.parseLong(timestamp)), this, handler);
                    boolean result = false;
                    try {
                        if(faceRest != null)
                            result = faceRest.execute().get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), "얼굴 정보를 등록해 주세요", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(hashValue.compareTo(FaceAuthService.file2SHA512String(modelPath)) != 0){
                    tollgateLog.e(LogFactor.FACE, faceCode.FACE_WRONG);
                    Log.d(FaceVar.TAG, "onCreate : Hash Value Not Matched");

                    FaceRestTask faceRest
                            = new FaceRestTask(new FacePack(hashValue, "auth", false, Long.parseLong(timestamp)), this, handler);
                    boolean result = false;
                    try {
                        if(faceRest != null)
                            result = faceRest.execute().get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), "얼굴 정보를 다시 등록해 주세요", Toast.LENGTH_SHORT).show();
                    finish();
                }
                Log.d(FaceVar.TAG, "onCreate : Hash Value Matched");
            }
        }


        mCameraView = (CameraBridgeViewBase)findViewById(R.id.activity_auth_view);
        mCameraView.setVisibility(SurfaceView.VISIBLE);
        mCameraView.setCvCameraViewListener(this);
        mCameraView.setCameraIndex(FaceVar.Camera.FRONT.ordinal());
    }
    @Override
    protected void onPause(){
        super.onPause();

        if(mCameraView!= null){
            mCameraView.disableView();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();

        if(!OpenCVLoader.initDebug()){
            Log.d(FaceVar.TAG, "onResume : Internal OpenCV Library Not Found!");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, m_LoaderCallback);
        }
        else{
            Log.d(FaceVar.TAG, "onResume : Internal OpenCV Library Found inside Package!");
            m_LoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();

        if(mCameraView != null){
            mCameraView.disableView();
        }
    }

    @Override
    public void onBackPressed(){

        if(timeout == true) {
            FaceRestTask faceRest = null;
            if (mode.compareTo(FaceVar.ActivationMode.AUTH) == 0)
                faceRest = new FaceRestTask(new FacePack(hashValue, "auth", false, Long.parseLong(timestamp)), this, handler);

            boolean result = false;
            try {
                if (faceRest != null)
                    result = faceRest.execute().get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onStart(){
        super.onStart();

        if(isGranted(CAMERA)){
            Log.d(FaceVar.TAG, "onStart : Camera Permission Granted");
            onCameraPermissionGranted();
        }
    }

    protected void onCameraPermissionGranted(){
        List<? extends CameraBridgeViewBase> cameraViews = getCameraViewList();
        if (cameraViews == null) {
            return;
        }
        for (CameraBridgeViewBase cameraBridgeViewBase: cameraViews) {
            if (cameraBridgeViewBase != null) {
                cameraBridgeViewBase.setCameraPermissionGranted();
            }
        }
    }
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(mCameraView);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isGranted(String permission){
        return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        if(mode.equals(FaceVar.ActivationMode.TRAIN))
            Toast.makeText(getApplicationContext(), "학습을 시작합니다.", Toast.LENGTH_SHORT).show();
        else if (mode.equals(FaceVar.ActivationMode.AUTH))
            Toast.makeText(getApplicationContext(), "인증을 시작합니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat matOutput = mFaceAS.getOriginImage(inputFrame.rgba());
        try {
            Thread.sleep(1);

            mFaceAS.setImage(matOutput);
            if(mFaceAS.preprocessImage()) { // 전처리

                if(mode.equals(FaceVar.ActivationMode.TRAIN)){
                    if(mFaceAS.isTrainPossible()){
                        tollgateLog.i(LogFactor.FACE, faceCode.FACE_REGISTER);
                        String hashValue = mFaceAS.trainFace();

                        FaceRestTask faceRest = new FaceRestTask(new FacePack(hashValue, "train", true, System.currentTimeMillis()), this, handler);
                        boolean result = false;
                        try {
                            result = faceRest.execute().get();
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.d(LogTag.AUTH_FACEID, "Face Registration result : " + result);

                        if(!result) {
                            Message msg = handler.obtainMessage(FaceMsg.TOAST_ERROR, "Invalid response.");
                            handler.sendMessage(msg);
                        }
                        else{
                            Message msg = handler.obtainMessage(FaceMsg.TOAST_MSG, "얼굴 정보를 등록했습니다.");
                            handler.sendMessage(msg);
                        }

                        finish();
                    }
                }
                else if(mode.equals(FaceVar.ActivationMode.AUTH)){
                    if(mFaceAS.isUser()) {
                        FaceRestTask faceRest = null;

                        faceRest = new FaceRestTask(new FacePack(hashValue, "auth", true, Long.parseLong(timestamp)), this, handler);
                        Boolean result = false;
                        try {
                            result = faceRest.execute().get();
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.d(LogTag.AUTH_FACEID, "Face Auth Result : " + result);

                        if(result == null){
                            Message msg = handler.obtainMessage(FaceMsg.TOAST_MSG, "인증 시간이 만료되었습니다.");
                            timeout = true;
                            handler.sendMessage(msg);
                        }
                        else if(result == false) {
                            Message msg = handler.obtainMessage(FaceMsg.TOAST_ERROR, "Invalid response.");
                            handler.sendMessage(msg);
                        }
                        else {
                            Message msg = handler.obtainMessage(FaceMsg.TOAST_MSG, "인증에 성공했습니다.");
                            handler.sendMessage(msg);
                        }

                        finish();
                    }
                }

                return mFaceAS.getFaceWithRectImage();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return matOutput;
    }
}