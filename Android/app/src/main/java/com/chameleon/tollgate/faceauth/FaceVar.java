package com.chameleon.tollgate.faceauth;

import android.content.Context;
import android.os.Handler;

import lombok.Getter;

@Getter
public class FaceVar {
    public static final String TAG = "FaceAuth";
    public static final int MULTI_PERMISSION_CODE = 200;
    public static final int DISTANCE = 55;
    public enum Camera {BACK, FRONT}
    public enum ActivationMode {
        AUTH("auth"),
        TRAIN("train");

        private final String mode;

        ActivationMode(String mode){
            this.mode = mode;
        }
    };
}
