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
import com.chameleon.tollgate.define.LogTag;

import java.util.List;

public class AuthPatternActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_pattern);

        Context context = this;
        PatternLockView patternView = (PatternLockView)findViewById(R.id.pattern_lock_view);
        PatternLockViewListener patternListener = new PatternLockViewListener() {
            @Override
            public void onStarted() { }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) { }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                Log.d(LogTag.AUTH_PATTERN, "Pattern complete: " +
                        PatternLockUtils.patternToString(patternView, pattern));
                try {
                    RestTask rest = new RestTask(PatternLockUtils.patternToString(patternView, pattern), getBaseContext());
                    boolean result = rest.execute().get();
                    Log.d(LogTag.AUTH_PATTERN, "Pattern authentication result : " + result);
                    if(result) {
                        Intent homeIntent = new Intent(context, MainActivity.class);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(homeIntent);
                        finish();
                    }
                }
                catch (Exception ex){
                    Log.d(LogTag.AUTH_PATTERN, "Pattern authentication failed due to an error.");
                }
            }

            @Override
            public void onCleared() {
                Log.d(LogTag.AUTH_PATTERN, "Pattern has been cleared");
            }
        };
        patternView.addPatternLockListener(patternListener);
    }
}