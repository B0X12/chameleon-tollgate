package com.chameleon.tollgate.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chameleon.tollgate.Activities.HistoryActivity;
import com.chameleon.tollgate.Activities.ServerActivity;
import com.chameleon.tollgate.R;
import com.chameleon.tollgate.Util;
import com.chameleon.tollgate.faceauth.AuthFaceActivity;
import com.chameleon.tollgate.pattern.SetPatternActivity;
import com.chameleon.tollgate.otp.Activity.OtpActivity;
import com.chameleon.tollgate.qr.Activity.QrActivity;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.faceCode;
import com.chameleon.tollgate.util.tollgateLog.tollgateLog;
import com.chameleon.tollgate.pattern.PatternMsg;
import com.chameleon.tollgate.viewitem.authAdapter;
import com.chameleon.tollgate.viewitem.authItem;
import com.chameleon.tollgate.viewitem.regAdapter;

import com.chameleon.tollgate.define.LogTag;

import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.ArrayList;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity {
    public static final String CFGFIlE = "config.cfg";
    public static String USER_ID = null;
    public static String SERVER_IP = null; //10.0.2.2
    static final int PERMISSIONS_REQUEST = 0x0000001;

    private final MainActivity.LoginHandler handler = new MainActivity.LoginHandler(this);

    private static class LoginHandler extends Handler {
        //private final WeakReference<MainActivity> reference;
        MainActivity activity;

        public LoginHandler(MainActivity activity) {
            super(Looper.getMainLooper());
            this.activity = activity;
            //this.reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case PatternMsg.TOAST_MSG:
                    Toast.makeText(activity, (String)msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case PatternMsg.TOAST_ERROR:
                    Toast.makeText(activity, "Exception : " + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

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


        ((ImageButton)findViewById(R.id.logout_button)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("로그아웃");
                alertDialogBuilder.setMessage("정말로 로그아웃 하시겠십니까?")
                        .setCancelable(false)
                        .setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try {
                                            LogoutTask rest = new LogoutTask(activity, handler, MainActivity.USER_ID, Util.getTimestamp());
                                            Boolean result = rest.execute().get();
                                            if(result == null)
                                                return;
                                            else if(!result) {
                                                Toast.makeText(getApplicationContext(), "로그아웃에 실패했습니다.", Toast.LENGTH_LONG).show();
                                                return;
                                            }
                                            else{
                                                File faceauthDir = getApplicationContext().getDir("faceauth", Context.MODE_PRIVATE);
                                                File mFaceModel = new File(faceauthDir, "trainedFace.fa");
                                                if(mFaceModel.exists())
                                                    mFaceModel.delete();

                                                new File(getApplicationContext().getFilesDir(), MainActivity.CFGFIlE).delete();

                                                Intent beginIntent = new Intent(getApplicationContext(), ServerActivity.class);
                                                beginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(beginIntent);
                                            }
                                        } catch (Exception ex) {
                                            Log.d(LogTag.ACCOUNT, "Exception : " + ex.getMessage());
                                        }
                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // 다이얼로그를 취소한다
                                        dialog.cancel();
                                    }
                                });

                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();
                // 다이얼로그 보여주기
                alertDialog.show();

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
                            Intent intent = new Intent(getApplicationContext(), OtpActivity.class);
                            startActivity(intent);
                        }
                    })
            );

            authList.add(new authItem(R.drawable.main_auth_qr_group,
                    new authItem.OnItemClickListener() {
                        @Override
                        public void onClick() {
                            Intent intent = new Intent(getApplicationContext(), QrActivity.class);
                            startActivity(intent);
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
                    StartFingerRegisterActivity();
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
                    Intent intent = new Intent(getApplicationContext(), SetPatternActivity.class);
                    startActivity(intent);
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

    public void StartFingerRegisterActivity(){
        Intent FingerEnroll = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
        startActivity(FingerEnroll);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}