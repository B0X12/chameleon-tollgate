package com.chameleon.tollgate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chameleon.tollgate.faceauth.AuthFaceActivity;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.faceCode;
import com.chameleon.tollgate.util.tollgateLog.tollgateLog;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Context context = this;


        Button btnTest = findViewById(R.id.btn_test);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setFace = new Intent(context, AuthFaceActivity.class);

                setFace.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                setFace.putExtra("mode", "train");
                startActivity(setFace);

            }
        });

    }
}