package com.chameleon.tollgate.fingerprint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.chameleon.tollgate.R;
import com.chameleon.tollgate.define.LogTag;

import java.util.concurrent.ExecutionException;

import static android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

public class FingerPrintActivity extends AppCompatActivity implements FingerPrintManager.Callback
{
    private int cnt;
    private Button btn_fingerprint;
    private Button btn_fingerprintEnroll;

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
        cnt = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_fingerprint);

        btn_fingerprint = findViewById(R.id.btn_fingerprintAuth);
        btn_fingerprintEnroll = findViewById(R.id.btn_fingerprintEnroll);

        manager_fingerprint = FingerPrintManager.getInstance(this);

        Intent intent = getIntent();
        Context context = this;

        btn_fingerprint.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                // 생체 인식 기능을 사용할 수 있는 상태인지 확인함
                if (manager_fingerprint.checkIfBiometricFeatureAvailable())
                {
                    // 인증이 가능한 상태면 인증 수행
                    manager_fingerprint.authenticate();

                    // 서버로 인증 가능한 상태임을 전송
                    RestTask rest = new RestTask(Long.parseLong(intent.getStringExtra("timestamp"))
                            , AUTH_FINGER_ENROLLED, context, handler);
                    rest.execute();
                }
            }
        });

        btn_fingerprintEnroll.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                Intent finger_enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                startActivity(finger_enrollIntent);
            }
        });
    }

    @Override public void onBiometricAuthenticationResult(String result, CharSequence errString) throws ExecutionException, InterruptedException {
        Intent intent = getIntent();
        Context context = this;
        int restResult = 0;

        switch (result)
        {
            case AUTHENTICATION_SUCCESSFUL:
                Toast.makeText(FingerPrintActivity.this,
                        "인증되었습니다."
                        , Toast.LENGTH_SHORT).show();
                Log.d(LogTag.AUTH_FINGERPRINT, "AUTHENTICATION_SUCCESSFUL");
                restResult = AUTH_SUCCESSFUL;
                break;

            case AUTHENTICATION_FAILED:
                Toast.makeText(FingerPrintActivity.this
                        , "인증에 실패했습니다.",
                        Toast.LENGTH_SHORT).show();
                Log.d(LogTag.AUTH_FINGERPRINT, "AUTHENTICATION_FAILED");
                restResult = AUTH_FAILED;

                if(++cnt < 5) { return; }
                break;

            case AUTHENTICATION_ERROR:
                Toast.makeText(FingerPrintActivity.this,
                        "Authentication error: " + errString
                        , Toast.LENGTH_SHORT).show();
                Log.d(LogTag.AUTH_FINGERPRINT, "AUTHENTICATION_ERROR");
                restResult = AUTH_ERROR;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + result);
        }
        RestTask rest = new RestTask(Long.parseLong(intent.getStringExtra("timestamp")), restResult, context, handler);
        rest.execute().get();
    }
}