package com.chameleon.tollgate.pattern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.chameleon.tollgate.R;
import com.chameleon.tollgate.Util;
import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.pattern.dto.PatternPack;

import java.util.List;

public class AuthPatternActivity extends AppCompatActivity {
    private final PatternHandler handler = new PatternHandler(this);

    private static class PatternHandler extends Handler {
        AuthPatternActivity activity;

        public PatternHandler(AuthPatternActivity activity) {
            super(Looper.getMainLooper());
            this.activity = activity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_pattern);

        Intent intent = getIntent();

        Context context = this;
        TextView label = findViewById(R.id.text_label_auth);
        label.setText("패턴을 입력하세요.");
        PatternLockView patternView = findViewById(R.id.pattern_auth);
        PatternLockViewListener patternListener = new PatternLockViewListener() {
            int count = 0;
            @Override
            public void onStarted() { }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) { }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                this.count++;
                Log.d(LogTag.AUTH_PATTERN, "Pattern complete: " +
                        PatternLockUtils.patternToString(patternView, pattern));
                try {
                    String resultPattern = PatternLockUtils.patternToString(patternView, pattern);
                    PatternPack petternPack = new PatternPack(Util.getHash(resultPattern), Long.parseLong(intent.getStringExtra("timestamp")));
                    RestTask rest = new RestTask(petternPack, context, handler, count >= 3);
                    boolean result = rest.execute().get();
                    Log.d(LogTag.AUTH_PATTERN, "Pattern authentication result : " + result);

                    if(!result) {
                        if(count < 3) {
                            label.setText("패턴이 잘못되었습니다.\n다시 입력해주세요.");
                            patternView.clearPattern();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "패턴이 잘못되었습니다.", Toast.LENGTH_LONG).show();
                    }
                    finish();
                } catch (Exception ex) {
                    Log.d(LogTag.AUTH_PATTERN, "Exception : " + ex.getMessage());
                }
            }

            @Override
            public void onCleared() { }
        };
        patternView.addPatternLockListener(patternListener);
    }
}