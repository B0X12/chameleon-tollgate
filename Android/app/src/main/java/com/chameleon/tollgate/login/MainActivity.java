package com.chameleon.tollgate.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chameleon.tollgate.HomeActivity;
import com.chameleon.tollgate.R;
import com.chameleon.tollgate.Util;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.LogLevel;
import com.chameleon.tollgate.util.tollgateLog.dto.code.faceCode;
import com.chameleon.tollgate.util.tollgateLog.tollgateLog;
import com.chameleon.tollgate.pattern.PatternMsg;
import com.google.firebase.messaging.FirebaseMessaging;

import com.chameleon.tollgate.define.LogTag;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String CFGFIlE = "config.cfg";
    public static String USER_ID = null;
    public static String SERVER_IP = null; //10.0.2.2
    private final LoginHandler handler = new LoginHandler(this);

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

    static {
        System.loadLibrary("opencv_java4");
        System.loadLibrary("native-lib");
    }

    Button btn_tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LogTag.TOLLGATE, "App started");

        MainActivity activity = this;

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        tollgateLog.setLogPath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Tollgate/Logs");

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText editID = findViewById(R.id.edit_id);
                    MainActivity.USER_ID = editID.getText().toString();
                    String pwd = Util.getHash(((TextView) findViewById(R.id.edit_pwd)).getText().toString());
                    LoginTask rest = new LoginTask(activity, handler, pwd, Util.getTimestamp());
                    Boolean result = rest.execute().get();
                    if(result == null)
                        return;
                    else if(!result) {
                        Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show();
                        MainActivity.USER_ID = null;
                        return;
                    }
                    if(editID.isEnabled()) {
                        RegistTokenTask regitTask = new RegistTokenTask(activity, handler, FirebaseMessaging.getInstance().getToken().getResult(), Util.getTimestamp());
                        Boolean registed = regitTask.execute().get();
                        if (!registed) {
                            Toast.makeText(activity, "기기를 등록하지 못했습니다.", Toast.LENGTH_SHORT).show();
                            MainActivity.USER_ID = null;
                            return;
                        }
                    }

                    Intent homeIntent = new Intent(activity, HomeActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);
                } catch (Exception ex) {
                    Log.d(LogTag.ACCOUNT, "Exception : " + ex.getMessage());
                    MainActivity.USER_ID = null;
                }
            }
        });

        EditText editPwd = findViewById(R.id.edit_pwd);
        editPwd.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode != KeyEvent.KEYCODE_ENTER)
                    return false;
                Button btnLogin = findViewById(R.id.btn_login);
                btnLogin.callOnClick();
                return false;
            }
        });

        EditText editServer = findViewById(R.id.edit_server);
        editServer.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode != KeyEvent.KEYCODE_ENTER)
                    return false;
                LoginService loginService = new LoginService();
                String ip = ((EditText)v).getText().toString();
                if(!loginService.checkValidIP(ip)) {
                    Toast.makeText(activity, "서버 주소의 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    v.requestFocus();
                    return true;
                }
                loginService.setServerIP(activity, ip);
                try {
                    if (!loginService.checkServer(activity, handler)) {
                        Toast.makeText(activity, "서버와 연결할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        loginService.unsetServerIP(activity);
                        return true;
                    }

                    if(!loginService.saveServerIP(activity)) {
                        Toast.makeText(activity, "서버 주소를 저장할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        loginService.unsetServerIP(activity);
                        return true;
                    }

                    loginService.beforeSetID(activity);
                    String id = loginService.getID(activity, handler);
                    if(id == null)
                        return false;
                    loginService.setID(activity, id);
                    return false;
                } catch (Exception e) {
                    Toast.makeText(activity, "서버와 연결할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    loginService.unsetServerIP(activity);
                    return true;
                }
            }
        });

        LoginService loginService = new LoginService();
        loginService.beforeSetServer(this);
        try {
            String ip = loginService.getServerIP(this);
            if(ip == null)
                return;
            loginService.setServerIP(this, ip);

            if(!loginService.checkServer(this, this.handler)) {
                Toast.makeText(this, "서버와 연결할 수 없습니다.", Toast.LENGTH_SHORT).show();
                loginService.unsetServerIP(this);
                return;
            }

            loginService.beforeSetID(this);
            String id = loginService.getID(this, this.handler);
            if(id == null)
                return;
            loginService.setID(this, id);
        } catch (IOException e) {
            Toast.makeText(this, "설정 파일을 열 수 없습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "서버와 연결할 수 없습니다.", Toast.LENGTH_SHORT).show();
            loginService.unsetServerIP(activity);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}