package com.chameleon.tollgate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    public final String USER_ID;

    public MainActivity(){
        USER_ID = "tester";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Tollgate_FCM", "App started");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Tollgate_FCM", "Tocken : " + FirebaseMessaging.getInstance().getToken().getResult());
    }
}