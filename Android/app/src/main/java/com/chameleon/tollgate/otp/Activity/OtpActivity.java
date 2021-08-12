package com.chameleon.tollgate.otp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.chameleon.tollgate.R;
import com.chameleon.tollgate.Util;
import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.login.MainActivity;
import com.chameleon.tollgate.otp.Activity.RestServer.RestOtpPack;
import com.chameleon.tollgate.otp.Activity.RestServer.RestOtpTask;
import com.chameleon.tollgate.otp.Activity.RestServer.ReturnMessage;
import com.chameleon.tollgate.otp.OtpConfig;
import com.chameleon.tollgate.otp.module.TOtp;
import java.text.SimpleDateFormat;
import java.util.Locale;
public class OtpActivity extends AppCompatActivity {

    //Debug
    public static final boolean DEBUGOTP = true;

    //CurrentTime
    private long GetCurrentTimeTicks = 0;

    //Timer
    private CountDownTimer countDownTimer;
    private boolean timerIsRunning = false;
    private long timerOtpCycleTime = 0;

    //function Conntect
    private TextView textView_centerOtpValue1,textView_centerOtpValue2,textView_centerOtpValue3,
            textView_centerOtpValue4,textView_centerOtpValue5,textView_centerOtpValue6,
            textView_topOtpShowCount;

    //SecretKey
    String secretKey = null;

    //RestSecretKey
    Context context;
    Handler handler;
    //---------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Otp Timer
        secretKey = GetSecretKey();
        if(! secretKey.contains("tollgate-"))
        {
            onBackPressed();
            ToastMessageShow("정보를 가져오지 못했습니다.");
        }

        Log.d(LogTag.AUTH_OTP, "#OTP - SecretKey : " + secretKey);

        //init
        context = getApplicationContext();
        handler = new Handler();

        timerIsRunning=true;
        OtpTimeOutTimerStart();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        textView_centerOtpValue1 = findViewById(R.id.textView_centerOtpValue1);
        textView_centerOtpValue2 = findViewById(R.id.textView_centerOtpValue2);
        textView_centerOtpValue3 = findViewById(R.id.textView_centerOtpValue3);
        textView_centerOtpValue4 = findViewById(R.id.textView_centerOtpValue4);
        textView_centerOtpValue5 = findViewById(R.id.textView_centerOtpValue5);
        textView_centerOtpValue6 = findViewById(R.id.textView_centerOtpValue6);
        textView_topOtpShowCount = findViewById(R.id.textView_topOtpShowCount);

        if(DEBUGOTP)
        {
            Log.d(LogTag.AUTH_OTP, "#OTP - SecretKey : " + secretKey);
            Log.d(LogTag.AUTH_OTP, "#OTP - CreateCycle : " + OtpConfig.createCycle);
            Log.d(LogTag.AUTH_OTP, "#OTP - OtpSize : " + OtpConfig.otpSize);
            Log.d(LogTag.AUTH_OTP, "#OTP - hashType : " + OtpConfig.hashType);
        }

    }


    @Override
    protected void onDestroy(){
        super.onDestroy();

        if(timerIsRunning)
            countDownTimer.cancel();
    }


    // ----------------------------------------------------------------------------

    private String GetSecretKey()
    {
        String result = null;
        try {

            if(DEBUGOTP)
                Log.d(LogTag.AUTH_OTP, "#OTP GetSecretKey - Start userId : " + MainActivity.USER_ID);

            RestOtpPack ROP = new RestOtpPack(MainActivity.USER_ID,System.currentTimeMillis(),null);
            RestOtpTask ROT = new RestOtpTask(ROP, context, handler);
            result = ROT.execute().get();


            if(DEBUGOTP)
                Log.d(LogTag.AUTH_OTP, "#OTP - GetServerSecretKey: " + result);

        } catch(Exception e) { };

        return result;
    }

    private void OtpTimeOutTimerStart()
    {
        //OTP TimeOut Timer
        countDownTimer = new CountDownTimer(2147000000,1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                if(timerOtpCycleTime <= 1 )
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {};
                }
                GetCurrentTimeTicks = System.currentTimeMillis();
                int currentSeconds = Integer.parseInt(new SimpleDateFormat("ss").format(GetCurrentTimeTicks));
                timerOtpCycleTime = (OtpConfig.createCycle) - ((currentSeconds < OtpConfig.createCycle) ? currentSeconds : currentSeconds-(OtpConfig.createCycle));
                textView_topOtpShowCount.setText(String.format(Locale.getDefault(),"%d초",timerOtpCycleTime));

                if(DEBUGOTP)
                {
                    Log.d(LogTag.AUTH_OTP, "#OTP - CurrentTime : " + Long.parseLong(new SimpleDateFormat("ss").format(GetCurrentTimeTicks)));
                    Log.d(LogTag.AUTH_OTP, "#OTP - timerOtpCycleTime : " + timerOtpCycleTime);
                    Log.d(LogTag.AUTH_OTP, "#OTP - currentSeconds-60 : " + (currentSeconds - OtpConfig.createCycle));
                    Log.d(LogTag.AUTH_OTP, "#OTP - SecretKey : " + secretKey);
                    Log.d(LogTag.AUTH_OTP, "#OTP - timestamp : " + GetCurrentTimeTicks);
                }

                TOtp totp = new TOtp(secretKey, OtpConfig.createCycle, OtpConfig.otpSize, OtpConfig.hashType);
                String otpValue = totp.ComputeTotp(GetCurrentTimeTicks);
                try {
                    textView_centerOtpValue1.setText(String.format(Locale.getDefault(), "%c", otpValue.charAt(0)));
                    textView_centerOtpValue2.setText(String.format(Locale.getDefault(), "%c", otpValue.charAt(1)));
                    textView_centerOtpValue3.setText(String.format(Locale.getDefault(), "%s", otpValue.charAt(2)));
                    textView_centerOtpValue4.setText(String.format(Locale.getDefault(), "%s", otpValue.charAt(3)));
                    textView_centerOtpValue5.setText(String.format(Locale.getDefault(), "%s", otpValue.charAt(4)));
                    textView_centerOtpValue6.setText(String.format(Locale.getDefault(), "%s", otpValue.charAt(5)));
                }catch(Exception e){};
            }
            @Override
            public void onFinish() {
                timerIsRunning=false;
                onBackPressed();
            }
        };
        countDownTimer.start(); //Timer Start
    }

    private void ToastMessageShow(final String content)
    {
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
    }

    private void AlertMessageShow(final String Title, final String Message)
    {
        //Otp TimeOut Message
        AlertDialog.Builder AlertMessageBuilder = new AlertDialog.Builder(OtpActivity.this)
                .setTitle(Title)
                .setMessage(Message)
                .setCancelable(false);// 버튼안누르고 화면눌렀을때 방지
        AlertMessageBuilder.setPositiveButton("확인",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { onBackPressed(); }
                } ).show();
    }
}
