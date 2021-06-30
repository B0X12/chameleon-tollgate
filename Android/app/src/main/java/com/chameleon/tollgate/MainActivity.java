package com.chameleon.tollgate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chameleon.tollgate.faceauth.AuthActivity;
import com.google.firebase.messaging.FirebaseMessaging;

import com.chameleon.tollgate.define.LogTag;

public class MainActivity extends AppCompatActivity {
    public static String USER_ID = null;
    public static String SERVER_IP = null;

    public MainActivity(){
        USER_ID = "tester";
        SERVER_IP = "10.0.2.2";
    }

    static {
        System.loadLibrary("opencv_java4");
        System.loadLibrary("native-lib");
    }

    private Button btn_tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LogTag.TOLLGATE, "App started");

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
        Log.d(LogTag.FCM, "Token : " + FirebaseMessaging.getInstance().getToken().getResult());
    }
}