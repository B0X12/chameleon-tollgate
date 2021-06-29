package com.chameleon.tollgate.pattern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.chameleon.tollgate.MainActivity;
import com.chameleon.tollgate.R;

import java.util.List;

public class AuthPatternActivity extends AppCompatActivity {
    private final String LOG_TAG = "Tollgate_Auth_Pattern";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_pattern);

        Intent intent = getIntent();
        Log.d(LOG_TAG, "Recived noti data: " + intent.getStringExtra("data"));

        Context context = this;
        PatternLockView patternView = (PatternLockView)findViewById(R.id.pattern_lock_view);
        PatternLockViewListener patternListener = new PatternLockViewListener() {
            @Override
            public void onStarted() {
                Log.d(LOG_TAG, "Pattern drawing started");
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {
                Log.d(LOG_TAG, "Pattern progress: " +
                        PatternLockUtils.patternToString(patternView, progressPattern));
            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                Log.d(LOG_TAG, "Pattern complete: " +
                        PatternLockUtils.patternToString(patternView, pattern));
                try {
                    RestTask rest = new RestTask("https://10.0.2.2:8080/auth/pattern/tester?pattern="+PatternLockUtils.patternToString(patternView, pattern), getBaseContext());
                    boolean result = rest.execute().get();
                    if(result) {
                        Intent homeIntent = new Intent(context, MainActivity.class);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(homeIntent);
                        finish();
                    }
                    Log.d(LOG_TAG, "Pattern authentication result : " + result);
                }
                catch (Exception ex){
                    Log.d(LOG_TAG, "Pattern authentication failed due to an error.");
                }
            }

            @Override
            public void onCleared() {
                Log.d(LOG_TAG, "Pattern has been cleared");
            }
        };
        patternView.addPatternLockListener(patternListener);
    }
}