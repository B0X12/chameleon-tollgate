package com.chameleon.tollgate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("tollgate", "App started");
        Log.d("tollgate", "Tocken : " + FirebaseMessaging.getInstance().getToken().getResult());
    }
}