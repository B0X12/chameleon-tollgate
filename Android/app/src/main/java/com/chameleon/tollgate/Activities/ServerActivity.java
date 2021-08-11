package com.chameleon.tollgate.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chameleon.tollgate.R;
import com.chameleon.tollgate.Util;
import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.login.LoginService;
import com.chameleon.tollgate.login.LoginTask;
import com.chameleon.tollgate.login.MainActivity;
import com.chameleon.tollgate.login.RegistTokenTask;
import com.chameleon.tollgate.pattern.PatternMsg;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ServerActivity extends AppCompatActivity {

    private final LoginHandler handler = new LoginHandler(this);

    private static class LoginHandler extends Handler {
        //private final WeakReference<MainActivity> reference;
        ServerActivity activity;

        public LoginHandler(ServerActivity activity) {
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

    private ServerActivity activity;

    private ServerFragment serverFragment;
    private LoginFragment loginFragment;

    private FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        activity = this;

        serverFragment = new ServerFragment();
        loginFragment = new LoginFragment();

        fm.beginTransaction()
                .add(R.id.fragmentContainer, serverFragment)
                .commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void checkServer(){
        LoginService loginService = new LoginService();
        try {
            String ip = loginService.getServerIP(this);
            if(ip == null)
                return;
            loginService.setServerIP(this, ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void helloServer(boolean onStack){
        LoginService loginService = new LoginService();

        EditText editIP = fm.findFragmentById(R.id.fragmentContainer).getView().findViewById(R.id.edit_IP);
        String ip = editIP.getText().toString();


        if(ip.isEmpty()){
            Toast.makeText(getApplicationContext(), ip + " : 서버 주소를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!loginService.checkValidIP(ip)){
            Toast.makeText(getApplicationContext(), "서버 주소의 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        loginService.setServerIP(activity, ip);

        try {
            if (!loginService.checkServer(activity, handler)) {
                Toast.makeText(getApplicationContext(), "서버와 연결할 수 없습니다.", Toast.LENGTH_SHORT).show();
                loginService.unsetServerIP(activity);
                return;
            }

            if(!loginService.saveServerIP(activity)) {
                Toast.makeText(getApplicationContext(), "서버 주소를 저장할 수 없습니다.", Toast.LENGTH_SHORT).show();
                loginService.unsetServerIP(activity);
                return;
            }
            onChangeFragment(onStack);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "서버와 연결할 수 없습니다.", Toast.LENGTH_SHORT).show();
            loginService.unsetServerIP(activity);
            return;
        }
    }

    public void initLogin(){

        LoginService loginService = new LoginService();
        String id = null;
        try {
            id = loginService.getID(activity, handler);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(id == null)
            return;
        loginService.setID(activity, id);
    }

    public void LoginAction(){
        try {
            EditText editID = findViewById(R.id.edit_ID);
            MainActivity.USER_ID = editID.getText().toString();
            String pwd = Util.getHash(((EditText) findViewById(R.id.edit_password)).getText().toString());
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
                else{
                    startMainActivity(true);
                    return;
                }
            }
            startMainActivity(false);


        } catch (Exception ex) {
            Log.d(LogTag.ACCOUNT, "Exception : " + ex.getMessage());
            MainActivity.USER_ID = null;
        }
    }

    private long backPressedTime = 0;
    @Override
    public void onBackPressed(){

        if(System.currentTimeMillis() > backPressedTime + 2000){
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로가기 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        else if(System.currentTimeMillis() <= backPressedTime + 2000){
            finish();
        }
    }
    public void onChangeFragment(boolean onStack){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.fragmentContainer, loginFragment);
        fragmentTransaction.commit();
    }

    public void setServerStep(){
        ImageView iv = findViewById(R.id.step_group);
        iv.setImageResource(R.drawable.server_step_group);
    }

    public void setLoginStep(){
        ImageView iv = findViewById(R.id.step_group);
        iv.setImageResource(R.drawable.login_step_group);
    }

    public void startMainActivity(boolean isFirstLogin){
        Intent homeIntent;
        if(!isFirstLogin) {
            homeIntent = new Intent(getApplicationContext(), MainActivity.class);
        }
        else{
            homeIntent = new Intent(getApplicationContext(), SubMainActivity.class);
        }
        startActivity(homeIntent);
        this.finish();
    }
}