package com.chameleon.tollgate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.fcm.FCMService;
import com.chameleon.tollgate.pattern.SetPatternActivity;

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
                Intent setPattern = new Intent(context, SetPatternActivity.class);
                setPattern.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(setPattern);
            }
        });
    }
}