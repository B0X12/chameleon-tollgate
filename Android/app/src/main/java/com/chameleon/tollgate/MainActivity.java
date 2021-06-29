package com.chameleon.tollgate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chameleon.tollgate.faceauth.AuthActivity;
import com.chameleon.tollgate.faceauth.TrainActivity;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    public final String USER_ID;

    public MainActivity(){
        USER_ID = "tester";
    }

    static {
        System.loadLibrary("opencv_java4");
        System.loadLibrary("native-lib");
    }

    private Button btn_tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Tollgate_FCM", "App started");


        btn_tmp = findViewById(R.id.btn_tmp);
        btn_tmp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Tollgate_FCM", "Tocken : " + FirebaseMessaging.getInstance().getToken().getResult());
    }
}