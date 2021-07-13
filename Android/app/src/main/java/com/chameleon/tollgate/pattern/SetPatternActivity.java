package com.chameleon.tollgate.pattern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.chameleon.tollgate.R;
import com.chameleon.tollgate.Util;
import com.chameleon.tollgate.define.LogTag;

import java.util.List;

public class SetPatternActivity extends AppCompatActivity {
    private String firstPattern = "";
    private int failed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pattern);

        TextView label = findViewById(R.id.text_label);
        label.setText("패턴을 그려주세요");

        Context context = this;
        Handler handler = new Handler();
        PatternLockView patternView = findViewById(R.id.pattern_set);
        PatternLockViewListener patternListener = new PatternLockViewListener() {
            @Override
            public void onStarted() {
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {
            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                if (firstPattern.isEmpty()) {
                    firstPattern = PatternLockUtils.patternToString(patternView, pattern);
                    TextView label = findViewById(R.id.text_label);
                    if(firstPattern.length() < 4) {
                        label.setText("최소 4개의 점을 연결해야 합니다.");
                        firstPattern = "";
                    }
                    else
                        label.setText("다시 패턴을 그려주세요");
                    patternView.clearPattern();
                    return;
                }

                String lastPattern = PatternLockUtils.patternToString(patternView, pattern);
                if (!firstPattern.equals(lastPattern)) {
                    failed++;
                    if(failed >= 3) {
                        Toast.makeText(getApplicationContext(), "패턴 설정에 실패했습니다.", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    TextView label = findViewById(R.id.text_label);
                    label.setText("패턴이 일치하지 않습니다.\n다시 패턴을 그려주세요");
                    patternView.clearPattern();
                    return;
                }

                try {
                    SetPatternTask rest = new SetPatternTask(Util.getHash(lastPattern), Util.getTimestamp(), context, handler);
                    boolean result = rest.execute().get();
                    Log.d(LogTag.AUTH_PATTERN, "Set pattern result : " + result);

                    if (!result)
                        Toast.makeText(getApplicationContext(), "패턴 설정에 실패했습니다.", Toast.LENGTH_LONG).show();

                    finish();
                } catch (Exception ex) {
                    Log.d(LogTag.AUTH_PATTERN, "Exception : " + ex.getMessage());
                }
            }

            @Override
            public void onCleared() {
            }
        };
        patternView.addPatternLockListener(patternListener);
    }
}