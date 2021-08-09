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
    private TextView textview_OtpText,textview_CycleTimeText;

    //SecretKey
    String secretKey = null;

    //RestSecretKey
    Context context;
    Handler handler;
    //---------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //init
        context = getApplicationContext();
        handler = new Handler();

        //Otp Timer
        secretKey = GetSecretKey();
        if(secretKey == ReturnMessage.VERIFY_INFORMATION)
        {
            ToastMessageShow("정보를 가져오지 못했습니다. 다시시도 해주세요.");
            onBackPressed();
        }
        Log.d(LogTag.AUTH_OTP, "#OTP - SecretKey : " + secretKey);

        timerIsRunning=true;
        OtpTimeOutTimerStart();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        textview_OtpText = findViewById(R.id.textView_Otp);
        textview_CycleTimeText = findViewById(R.id.textview_TimeCount);

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
        countDownTimer = new CountDownTimer(2147000000,500)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                    GetCurrentTimeTicks = System.currentTimeMillis();
                    int currentSeconds = Integer.parseInt(new SimpleDateFormat("ss").format(GetCurrentTimeTicks));
                    timerOtpCycleTime = (OtpConfig.createCycle) - ((currentSeconds < OtpConfig.createCycle) ? currentSeconds+1 : currentSeconds-(OtpConfig.createCycle-1));
                    textview_CycleTimeText.setText(String.format(Locale.getDefault(),"%d",timerOtpCycleTime));

                    if(DEBUGOTP)
                    {
                        Log.d(LogTag.AUTH_OTP, "#OTP - CurrentTime : " + Long.parseLong(new SimpleDateFormat("ss").format(GetCurrentTimeTicks)));
                        Log.d(LogTag.AUTH_OTP, "#OTP - timerOtpCycleTime : " + timerOtpCycleTime);
                        Log.d(LogTag.AUTH_OTP, "#OTP - currentSeconds-60 : " + (currentSeconds - OtpConfig.createCycle));
                        Log.d(LogTag.AUTH_OTP, "#OTP - SecretKey : " + secretKey);
                        Log.d(LogTag.AUTH_OTP, "#OTP - timestamp : " + GetCurrentTimeTicks);
                    }

                    TOtp totp = new TOtp(secretKey, OtpConfig.createCycle, OtpConfig.otpSize, OtpConfig.hashType);
                    textview_OtpText.setText(String.format(Locale.getDefault(), "%s", totp.ComputeTotp(GetCurrentTimeTicks)));
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
