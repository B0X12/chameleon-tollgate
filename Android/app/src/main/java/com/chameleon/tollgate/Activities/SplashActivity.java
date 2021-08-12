package com.chameleon.tollgate.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.chameleon.tollgate.R;
import com.chameleon.tollgate.login.MainActivity;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        Intent intent = new Intent(getApplicationContext(), ServerActivity.class);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                {
                    System.loadLibrary("opencv_java4");
//                    System.loadLibrary("native-lib");
                }

                startActivity(intent);
                finish();
            }
        }, 10);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}