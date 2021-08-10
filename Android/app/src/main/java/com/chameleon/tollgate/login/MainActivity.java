package com.chameleon.tollgate.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chameleon.tollgate.Activities.HistoryActivity;
import com.chameleon.tollgate.R;
import com.chameleon.tollgate.faceauth.AuthFaceActivity;
import com.chameleon.tollgate.faceauth.FaceVar;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.faceCode;
import com.chameleon.tollgate.util.tollgateLog.tollgateLog;
import com.chameleon.tollgate.pattern.PatternMsg;
import com.chameleon.tollgate.viewitem.authAdapter;
import com.chameleon.tollgate.viewitem.authItem;
import com.chameleon.tollgate.viewitem.regAdapter;

import com.chameleon.tollgate.define.LogTag;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity {
    public static final String CFGFIlE = "config.cfg";
    public static String USER_ID = null;
    public static String SERVER_IP = null; //10.0.2.2
    static final int PERMISSIONS_REQUEST = 0x0000001;

    public MainActivity(){
    }


    private RecyclerView authRecyclerView, regRecyclerView, etcRecyclerView;
    private ArrayList<authItem> authList, regList, etcList;

    private long backPressedTime = 0;
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > backPressedTime + 2000){
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로가기 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        else if(System.currentTimeMillis() <= backPressedTime + 2000){
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LogTag.TOLLGATE, "App started");

        MainActivity activity = this;

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, CAMERA}, MODE_PRIVATE);
        tollgateLog.setLogPath(getExternalFilesDir("Tollgate/Logs/").getAbsolutePath());
        tollgateLog.i(LogFactor.LOGIN, faceCode.UNKNOWN_AUTH);

        authRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_auth);
        regRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_reg);

        UITask uiInit = new UITask();
        uiInit.execute(activity);



        TextView userID = findViewById(R.id.text_userID);
        userID.setText(MainActivity.USER_ID);

        TextView historyUser = findViewById(R.id.history_userID_text);
        historyUser.setText(MainActivity.USER_ID);

        Button historyButton = findViewById(R.id.history_button);
        historyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(getApplicationContext(), HistoryActivity.class);
                historyIntent.putExtra("userID", MainActivity.USER_ID);
                startActivity(historyIntent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSIONS_REQUEST:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    StartFaceRegisterActivity();
                }
                else{
                    Toast.makeText(getApplicationContext(), "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isGranted(String permission){
        return (ActivityCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    class UITask extends AsyncTask<MainActivity, Void, Void> {

        @Override
        protected Void doInBackground(MainActivity... activities) {
            authList = new ArrayList<>();
            authList.add(new authItem(R.drawable.main_auth_otp_group,
                    new authItem.OnItemClickListener() {
                        @Override
                        public void onClick() {
                            // otp 기능 실행
                            Toast.makeText(getApplicationContext(), "test Toast", Toast.LENGTH_SHORT).show();
                        }
                    })
            );

            authList.add(new authItem(R.drawable.main_auth_qr_group,
                    new authItem.OnItemClickListener() {
                        @Override
                        public void onClick() {
                            // qr 기능 실행
                            Toast.makeText(getApplicationContext(), "test Toast", Toast.LENGTH_SHORT).show();
                        }
                    })
            );
            authRecyclerView.setAdapter(new authAdapter(authList));
            authRecyclerView.setLayoutManager(new LinearLayoutManager(activities[0], RecyclerView.HORIZONTAL, false));
            authRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

            regList = new ArrayList<>();
            regList.add(new authItem(R.drawable.main_register_fingerprint_group, new authItem.OnItemClickListener(){
                @Override
                public void onClick(){
                    // 지문 등록 기능
                    Toast.makeText(getApplicationContext(), "reg test1", Toast.LENGTH_SHORT).show();
                }
            }));
            regList.add(new authItem(R.drawable.main_register_faceid_group, new authItem.OnItemClickListener(){
                @Override
                public void onClick(){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        if(!isGranted(CAMERA)){
                            if(ActivityCompat.shouldShowRequestPermissionRationale(activities[0], CAMERA)){
                                ActivityCompat.requestPermissions(activities[0], new String[]{CAMERA}, PERMISSIONS_REQUEST);
                            }
                            else{
                                ActivityCompat.requestPermissions(activities[0], new String[]{CAMERA}, PERMISSIONS_REQUEST);
                            }
                        }
                        else{
                            // 얼굴 등록 기능
                            StartFaceRegisterActivity();
                        }
                    }
                }
            }));
            regList.add(new authItem(R.drawable.main_register_pattern_group, new authItem.OnItemClickListener(){
                @Override
                public void onClick(){
                    // 패턴 등록 기능
                    Toast.makeText(getApplicationContext(), "reg test3", Toast.LENGTH_SHORT).show();
                }
            }));
            regRecyclerView.setAdapter(new regAdapter(regList));
            regRecyclerView.setLayoutManager(new LinearLayoutManager(activities[0]));
            regRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);


            return null;
        }
    }

    public void StartFaceRegisterActivity(){

        Intent setFace = new Intent(getApplicationContext(), AuthFaceActivity.class);
        setFace.putExtra("mode", "train");
        startActivity(setFace);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}