package com.chameleon.tollgate.fingerprint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chameleon.tollgate.R;
import com.chameleon.tollgate.fingerprint.RestTask;

import java.util.concurrent.ExecutionException;

public class FingerPrintActivity extends AppCompatActivity implements FingerPrintManager.Callback
{
    Button btn_fingerprint;
    private FingerPrintManager manager_fingerprint;
    private final FingerprintHandler handler = new FingerprintHandler(this);

    private static class FingerprintHandler extends Handler
    {
        FingerPrintActivity activity;

        public FingerprintHandler(FingerPrintActivity activity)
        {
            super(Looper.getMainLooper());
            this.activity = activity;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print);

        btn_fingerprint = findViewById(R.id.btn_fingerprintAuth);
        manager_fingerprint = FingerPrintManager.getInstance(this);

        btn_fingerprint.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                // 생체 인식 기능을 사용할 수 있는 상태인지 확인함
                if (manager_fingerprint.checkIfBiometricFeatureAvailable())
                {
                    // boolean형. true를 반환할 때(인증을 성공했을 시)
                    // 지문 인식을 수행
                    manager_fingerprint.authenticate();
                }
            }
        });
    }

    @Override public void onBiometricAuthenticationResult(String result, CharSequence errString) throws ExecutionException, InterruptedException {
        Intent intent = getIntent();
        Context context = this;
        boolean restResult;

        switch (result)
        {
            case AUTHENTICATION_SUCCESSFUL:
                Toast.makeText(FingerPrintActivity.this,
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "AUTHENTICATION_SUCCESSFUL");
                restResult = AUTH_SUCCESSFUL;
                break;

            case AUTHENTICATION_FAILED:
                Toast.makeText(FingerPrintActivity.this, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
                Log.d("TAG", "AUTHENTICATION_FAILED");
                restResult = AUTH_FAILED;
                break;

            case AUTHENTICATION_ERROR:
                Toast.makeText(FingerPrintActivity.this,
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
                Log.d("TAG", "AUTHENTICATION_ERROR");
                restResult = AUTH_ERROR;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + result);
        }
        RestTask rest = new RestTask(Long.parseLong(intent.getStringExtra("timestamp")), restResult, context, handler);
        rest.execute().get();
    }

    /*
    Nullable 오류
    import 문제나 버전문제 같은데 해결이 안됨
    -> 지문이 등록돼 있는지 확인하는 메소드

    @Override protected void onActivityResult(int requestCode, int resultCode,
                                              @Nullable @org.jetbrains.annotations.Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FingerPrintManager.REQUEST_CODE && resultCode == RESULT_OK)
        {
            // 생체 인식 등록 여부 확인
        }
    }
    */
}