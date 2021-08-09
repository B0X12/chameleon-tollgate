package com.chameleon.tollgate.fingerprint;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.chameleon.tollgate.define.LogTag;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import lombok.SneakyThrows;

import static android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

public class FingerPrintManager {

    private static FingerPrintManager instance = null;

    // https://developer.android.com/training/sign-in/biometric-auth 참조
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo;

    private Context context;
    private Callback callback;
    private FragmentActivity fragmentActivity;

    public static final int REQUEST_CODE = 100;

    public static FingerPrintManager getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new FingerPrintManager();
        }
        instance.init(context);
        return instance;
    }

    private void init(Context context)
    {
        this.context = context;
        this.fragmentActivity = (FragmentActivity) context;
        this.callback = (Callback) context;
    }

    // 생체 인식 기능을 사용할 수 있는 상태인지 확인함
    boolean checkIfBiometricFeatureAvailable()
    {
        // BiometricManager : https://developer.android.com/reference/androidx/biometric/BiometricManager
        BiometricManager biometricManager = BiometricManager.from(context);

        switch (biometricManager.canAuthenticate())
        {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d(LogTag.AUTH_FINGERPRINT, "App can authenticate using biometrics.");
                //Toast.makeText(context, "App can authenticate using biometrics.",Toast.LENGTH_SHORT).show();
                return true;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                // 적합한 하드웨어가 없음
                Log.e(LogTag.AUTH_FINGERPRINT, "No biometric features available on this device.");
                Toast.makeText(context, "No biometric features available on this device.", Toast.LENGTH_SHORT).show();
                return false;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                // 하드웨어를 사용할 수 없음
                Log.e(LogTag.AUTH_FINGERPRINT, "Biometric features are currently unavailable.");
                Toast.makeText(context, "Biometric features are currently unavailable.", Toast.LENGTH_SHORT).show();
                return false;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // 등록 된 생체 인식 또는 장치 자격 증명이 없음
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);

                fragmentActivity.startActivity(enrollIntent); // 액티비티를 열어주고 결과값 전달

                Toast.makeText(context, "지문을 등록해주세요.", Toast.LENGTH_SHORT).show();
                Log.e(LogTag.AUTH_FINGERPRINT, "There are no registered fingerprints.");

                return false;
        }
        return false;
    }

    void authenticate()
    {
        setBiometric();
        biometricPrompt.authenticate(promptInfo);
        // authenticate(BiometricPrompt.PromptInfo info) - 원형
        // 사용자에게 생체 인식 프롬프트를 표시함
    }

    private void setBiometric()
    {
        // https://developer.android.com/training/sign-in/biometric-auth
        // 공식문서에서 제공해주는 내용, 대화상자 표시와 관련된 내용임

        executor = ContextCompat.getMainExecutor(context);
        biometricPrompt = new BiometricPrompt(fragmentActivity
                , executor, new BiometricPrompt.AuthenticationCallback()
        {
            @SneakyThrows
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString)
            {
                super.onAuthenticationError(errorCode, errString);
                callback.onBiometricAuthenticationResult(Callback.AUTHENTICATION_ERROR, errString);
            }

            @SneakyThrows
            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result)
            {
                super.onAuthenticationSucceeded(result);
                callback.onBiometricAuthenticationResult(Callback.AUTHENTICATION_SUCCESSFUL,
                        "");
            }

            @SneakyThrows
            @Override
            public void onAuthenticationFailed()
            {
                super.onAuthenticationFailed();
                callback.onBiometricAuthenticationResult(Callback.AUTHENTICATION_FAILED, "");
            }
        });

        showBiometricPrompt();
    }

    private void showBiometricPrompt()
    {
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Tollgate 지문인증")
                .setSubtitle("로그인을 원하실 경우에만 인증을 수행해주세요.")
                .setNegativeButtonText("Chameleon")
                .build();
    }

    interface Callback
    {
        void onBiometricAuthenticationResult(String result, CharSequence errString) throws ExecutionException, InterruptedException;

        String AUTHENTICATION_SUCCESSFUL = "인증 성공";
        String AUTHENTICATION_FAILED = "인증 실패";
        String AUTHENTICATION_ERROR = "에러 발생";

        int AUTH_SUCCESSFUL = 121;
        int AUTH_FAILED = 122;
        int AUTH_ERROR = 123;
        int AUTH_FINGER_ENROLLED = 131;
    }

}