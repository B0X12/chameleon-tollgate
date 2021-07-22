package com.chameleon.tollgate.otp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.chameleon.tollgate.R;
import com.chameleon.tollgate.Util;
import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.otp.module.TOtp;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    //Debug
    public static final boolean DebugTest = true;

    //CurrentTime
    private long GetCurrentTimeTicks = 0;

    //Timer
    private CountDownTimer countDownTimer;
    private boolean TimerIsRunning = false;
    private long TimerLimitTime;

    //function Conntect
    private TextView textview_otpShow,textview_TimeOut;

    //Intent
    private Intent intent = null;

    //Instance
    private String SecretKey = null;
    private long ServerCurrentTimeTicks = 0;
    private int CreateCycle = 0;
    private int OtpSize = 0;
    private int hashType = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        GetCurrentTimeTicks = System.currentTimeMillis();

        textview_otpShow = findViewById(R.id.textView_Otp);
        textview_TimeOut = findViewById(R.id.textview_TimeCount);


        //Intent Data
        intent = Util.getTypeDataIntent(getIntent(),"OTP");

        if(intent == null)
        {
            AlertMessageBack("OTP 실패", "데이터를 받지 못했습니다. 다시시도 해보시기 바랍니다.");
            return;
        }

        //Otp Value Setting
        try
        {
            //Get Data
            SecretKey = intent.getExtras().getString("SecretKey");
            CreateCycle = Integer.parseInt(intent.getExtras().getString("CreateCycle"));
            OtpSize = Integer.parseInt(intent.getExtras().getString("OtpSize"));
            hashType = Integer.parseInt(intent.getExtras().getString("HashType"));
            ServerCurrentTimeTicks = Long.parseLong(intent.getExtras().getString("ServerCurrentTime"));

            //Totp Instance Create And Show
            TOtp totp = new TOtp(SecretKey,CreateCycle,OtpSize,hashType);
            textview_otpShow.setText(totp.ComputeTotp(ServerCurrentTimeTicks));
        }
        catch (Exception e)
        {
            AlertMessageBack("OTP 실패", "데이터를 받지 못했습니다.\r\n" + "OTP 인증시도를 다시 시도해주세요.");
            return;
        }

        //Otp Timer
        TimerIsRunning=true;
        //Limit Calculate Time
        TimerLimitTime = CreateCycle - (TimeUnit.MILLISECONDS.toSeconds(GetCurrentTimeTicks) - TimeUnit.MILLISECONDS.toSeconds(ServerCurrentTimeTicks));

        if(DebugTest)
        {
            Log.d(LogTag.AUTH_OTP, "#OTP - Android GetCurrentTimeTicks: " + GetCurrentTimeTicks);
            Log.d(LogTag.AUTH_OTP, "#OTP - ServerTimeTicks: " + ServerCurrentTimeTicks);
            Log.d(LogTag.AUTH_OTP, "#OTP - SecretKey : " + SecretKey);
            Log.d(LogTag.AUTH_OTP, "#OTP - CreateCycle : " + CreateCycle);
            Log.d(LogTag.AUTH_OTP, "#OTP - OtpSize : " + OtpSize);
            Log.d(LogTag.AUTH_OTP, "#OTP - hashType : " + hashType);
        }

        Log.d(LogTag.AUTH_OTP, "#OTP - TimerLimitTime : " + TimerLimitTime);
        OtpTimeOutTimerStart();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        if(TimerIsRunning)
            countDownTimer.cancel();

        Util.FcmIntentDelete();
    }


    // ----------------------------------------------------------------------------

    private void OtpTimeOutTimerStart()
    {
        //OTP TimeOut Timer
        countDownTimer = new CountDownTimer(TimerLimitTime*1000,1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                if(TimerLimitTime >= 0)
                {
                    --TimerLimitTime;
                    textview_TimeOut.setText(String.format(Locale.getDefault(),"%d",TimerLimitTime));
                }
            }
            @Override
            public void onFinish() {
                TimerIsRunning=false;
                onBackPressed();
            }
        };
        countDownTimer.start(); //Timer Start
    }

    private void AlertMessageBack(final String Title, final String Message)
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
