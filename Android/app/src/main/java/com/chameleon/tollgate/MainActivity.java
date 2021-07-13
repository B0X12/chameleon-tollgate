package com.chameleon.tollgate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chameleon.tollgate.Utils.Log.define.logRecord;
import com.chameleon.tollgate.Utils.Log.tollgateLog;
import com.google.firebase.messaging.FirebaseMessaging;

import com.chameleon.tollgate.define.LogTag;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String USER_ID = null;
    public static String SERVER_IP = null;

    public MainActivity(){
        USER_ID = "tester";
//        SERVER_IP = "10.0.2.2";
        SERVER_IP = "192.168.0.17";
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

        tollgateLog log = tollgateLog.getInstance(getApplicationContext());

        btn_tmp = findViewById(R.id.btn_tmp);
        btn_tmp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // 임시 기능 테스트 버튼
//                Intent trainIntent = new Intent(getApplicationContext(), AuthFaceActivity.class);
//                trainIntent.putExtra("mode", "train");
//                startActivity(trainIntent);

//                log.put(priority.INFO, logMode.AUTH, logType.FACE, "test");
                ArrayList<logRecord> logs = log.get();
                for(logRecord record : logs){
                    System.out.print(record.getPriority());
                    System.out.print(record.getTimestamp());
                    System.out.print(record.getMode());
                    System.out.print(record.getType());
                    System.out.print(record.getMessage());
                    System.out.println("");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LogTag.FCM, "Token : " + FirebaseMessaging.getInstance().getToken().getResult());
    }

}