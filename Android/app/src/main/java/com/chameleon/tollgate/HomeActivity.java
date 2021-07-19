package com.chameleon.tollgate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chameleon.tollgate.Utils.Log.dto.logMode;
import com.chameleon.tollgate.Utils.Log.dto.logPriority;
import com.chameleon.tollgate.Utils.Log.dto.logResult;
import com.chameleon.tollgate.Utils.Log.dto.logType;
import com.chameleon.tollgate.Utils.Log.tollgateLog;
import com.chameleon.tollgate.faceauth.AuthFaceActivity;

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

        // 로그 사용
        tollgateLog log = tollgateLog.getInstance(getApplicationContext()); // 인자는 getApplicatioinContext로 해주세여, getBaseContext 안되요~
        log.put(logPriority.INFO, logMode.NULL, logType.LOGIN, logResult.SUCCESS, "테스트 로그");
    }
}