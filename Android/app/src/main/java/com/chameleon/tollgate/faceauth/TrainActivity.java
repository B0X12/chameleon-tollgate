package com.chameleon.tollgate.faceauth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;

import com.chameleon.tollgate.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class TrainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private FaceAuthService m_faceAS;
    private CameraBridgeViewBase m_CameraView;

    private static final int MULTI_PERMISION_CODE = 200;

    private BaseLoaderCallback m_LoaderCallback = new BaseLoaderCallback(this) {

        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    m_CameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        m_CameraView = (CameraBridgeViewBase)findViewById(R.id.activity_train_view);
        m_CameraView.setVisibility(SurfaceView.VISIBLE);
        m_CameraView.setCvCameraViewListener(this);
        m_CameraView.setCameraIndex(FaceAuthService.m_frontCam);

        m_faceAS = new FaceAuthService(this);
    }

    @Override
    protected void onPause(){
        super.onPause();

        if(m_CameraView!= null){
            m_CameraView.disableView();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(!OpenCVLoader.initDebug()){
            Log.d(FaceAuthService.TAG, "onResume : Internal OpenCV Library Not Found!");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, m_LoaderCallback);
        }
        else{
            Log.d(FaceAuthService.TAG, "onResume : Internal OpenCV Library Found inside Package!");
            m_LoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        if(m_CameraView != null){
            m_CameraView.disableView();
        }
    }

    private void checkEqual(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림").setMessage("얼굴을 다시 등록해 주세요.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // 권한 부여
    @Override
    protected void onStart(){
        super.onStart();

        boolean _CamPermission = true;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            List permissions = new ArrayList();

            if(checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(CAMERA);
                _CamPermission = false;
            }

            if(checkSelfPermission(READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                permissions.add(READ_EXTERNAL_STORAGE);

            if(checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                permissions.add(WRITE_EXTERNAL_STORAGE);

            String[] arr_permissions = (String[]) permissions.toArray(new String[permissions.size()]);

            if(permissions.isEmpty()){
//                Toast.makeText(this, "권한이 모두 허용되었습니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                requestPermissions(arr_permissions, MULTI_PERMISION_CODE);
            }
        }
        if(_CamPermission){
            onCameraPermissionGranted();
        }
        Toast.makeText(this, "얼굴 등록을 시작합니다.", Toast.LENGTH_SHORT).show();
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
        return Collections.singletonList(m_CameraView);
    }


    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat originImage = m_faceAS.getOriginImage(inputFrame.rgba());
        m_faceAS.setImage(originImage);

        if(m_faceAS.preprocessImage()){

            Mat outputImage = m_faceAS.getFaceImage(true);
            if(m_faceAS.getM_cnt() < m_faceAS.m_trainCnt)
                m_faceAS.addCurrnentImage2List();
            else
                m_faceAS.trainFace();

            return outputImage;
        }

        return originImage;
    }
}