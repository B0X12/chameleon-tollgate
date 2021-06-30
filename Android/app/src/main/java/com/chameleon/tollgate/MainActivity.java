package com.chameleon.tollgate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.rest.*;
import com.chameleon.tollgate.rest.define.*;

public class MainActivity extends AppCompatActivity {
    public static String USER_ID = null;
    public static String SERVER_IP = null;

    public MainActivity(){
        USER_ID = "tester";
        SERVER_IP = "10.0.2.2";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LogTag.TOLLGATE, "App started");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LogTag.FCM, "Token : " + FirebaseMessaging.getInstance().getToken().getResult());
    }
}