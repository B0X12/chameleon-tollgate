package com.chameleon.tollgate.faceauth;

import android.content.Context;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

interface FaceService {
    public static final String TAG = "OpenCV";
    public static final int m_backCam = 0; // 0: 후면, 1: 전면
    public static final int m_frontCam = 1;
    public static final int m_train = 0;
    public static final int m_auth = 1;

    public int m_trainCnt = 50;


    boolean loadCascade() throws IOException;
    Rect detectFace(Mat matInput) throws FaceException;
    Mat drawRect(Mat matInput, Rect rc, boolean isPercentage);
    boolean trainFace() throws FaceException, IOException, NoSuchAlgorithmException;
    Mat recogFace(Mat matInput, Rect rc) throws MalformedURLException;
}
