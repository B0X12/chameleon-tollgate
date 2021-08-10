package com.chameleon.tollgate.qr.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.chameleon.tollgate.R;
import com.chameleon.tollgate.Util;
import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.login.MainActivity;
import com.chameleon.tollgate.otp.Activity.RestServer.ReturnMessage;
import com.chameleon.tollgate.qr.RestServer.RestQrPack;
import com.chameleon.tollgate.qr.RestServer.RestQrTask;
import com.chameleon.tollgate.rest.define.Path;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class QrActivity extends AppCompatActivity { //AppCompatActivity
    //Debug
    public static final boolean DEBUGQR = true;

    //RestSecretKey
    Context context;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init
        context = getApplicationContext();
        handler = new Handler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);


        IntentIntegrator Qr = new IntentIntegrator(this);
        Qr.setBeepEnabled(false);
        Qr.setOrientationLocked(true);
        Qr.setPrompt("QR을 인증해주세요.");
        Qr.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null)
        {
            if(intentResult.getContents() == null)
            {
                Toast.makeText(this, "취소하였습니다.", Toast.LENGTH_LONG).show();
            } else {
                try
                {
                    JSONObject obj = new JSONObject(intentResult.getContents());

                    if(DEBUGQR)
                        Log.d(LogTag.AUTH_QR, "#QR onActivityResult - 0  Scanned = userId : " + obj.getString("userId") + " data : " + obj.getString("data"));

                    if(!MainActivity.USER_ID.equals(obj.getString("userId")))
                    {
                        Toast.makeText(this, "맞지 않는 QR 입니다. 다시시도 해주세요.", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }

                    final String result = SendData(obj.getString("data"));

                    if(DEBUGQR)
                        Log.d(LogTag.AUTH_QR, "#QR onActivityResult - 1  SendData Result : " + result);

                    if(result.equals(ReturnMessage.SUCCESS))
                    {
                        Toast.makeText(this, "QR 인증에 성공하였습니다.", Toast.LENGTH_LONG).show();
                        onBackPressed();
                        return;
                    }
                    else if(result.equals(ReturnMessage.UNKNOWN))
                    {
                        Toast.makeText(this, "유효하지 않은 QR 입니다.", Toast.LENGTH_LONG).show();
                        onBackPressed();
                        return;
                    }
                    else {
                        Toast.makeText(this, "QR 인증에 실패하였습니다.", Toast.LENGTH_LONG).show();
                        onBackPressed();
                        return;
                    }
                } catch(JSONException e) {
                    Toast.makeText(this, "맞지 않는 QR 입니다. 다시시도 해주세요.", Toast.LENGTH_LONG).show();
                    onBackPressed();
                };
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);


    }

    private String SendData(final String decryptData)
    {
        String returnValue = null;
        try
        {
            RestQrPack ROP = new RestQrPack(MainActivity.USER_ID,System.currentTimeMillis(),decryptData);
            RestQrTask ROT = new RestQrTask(ROP, context, handler, Path.AUTH_QR);
            returnValue = ROT.execute().get();

        } catch(Exception e) { return "SendData Exception"; };

        return returnValue;
    }
}