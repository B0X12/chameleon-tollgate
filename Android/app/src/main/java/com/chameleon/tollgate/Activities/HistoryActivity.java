package com.chameleon.tollgate.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chameleon.tollgate.R;
import com.chameleon.tollgate.faceauth.FaceMsg;
import com.chameleon.tollgate.faceauth.FacePack;
import com.chameleon.tollgate.faceauth.FaceRestTask;
import com.chameleon.tollgate.util.others.HistoryPack;
import com.chameleon.tollgate.util.others.HistoryRecord;
import com.chameleon.tollgate.util.others.HistoryRestTask;
import com.chameleon.tollgate.viewitem.HistoryTitle;
import com.chameleon.tollgate.viewitem.HistoryIcon;
import com.chameleon.tollgate.viewitem.HistoryItem;
import com.chameleon.tollgate.viewitem.HistoryResultIcon;
import com.chameleon.tollgate.viewitem.authItem;
import com.chameleon.tollgate.viewitem.tollgateAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class HistoryActivity extends AppCompatActivity {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case FaceMsg.TOAST_MSG:
                    Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case FaceMsg.TOAST_ERROR:
                    Toast.makeText(getApplicationContext(), "Error : " + (String)msg.obj, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    private RecyclerView historyRecyclerView;
    private ArrayList<HistoryItem> historyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent parentIntent = getIntent();
        String UserID = parentIntent.getStringExtra("userID");
        ((TextView)findViewById(R.id.history_subtitle)).setText(UserID + "님의 최근 인증 내역이에요 ☺");

        HistoryRestTask historyRest
                = new HistoryRestTask(new HistoryPack(true, System.currentTimeMillis()), this, handler);
        ArrayList<HistoryRecord> result = null;
        try {
            if(historyRest != null)
                result = historyRest.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        historyRecyclerView = findViewById(R.id.recyclerView_history);
        historyList = new ArrayList<HistoryItem>();
        if(result != null) {
            for (HistoryRecord record : result) {
                switch (record.getFactor()){
                    case "face":
                        historyList.add(new HistoryItem(HistoryIcon.FACE, HistoryResultIcon.get(record.getResult()), HistoryTitle.FACE, record.getPc(), record.getTimestamp(),
                                new authItem.OnItemClickListener() {
                                    @Override
                                    public void onClick() {}
                                })
                        );
                        break;
                    case "otp":
                        historyList.add(new HistoryItem(HistoryIcon.OTP, HistoryResultIcon.get(record.getResult()), HistoryTitle.OTP, record.getPc(), record.getTimestamp(),
                                new authItem.OnItemClickListener() {
                                    @Override
                                    public void onClick() {}
                                })
                        );
                        break;
                    case "pattern":
                        historyList.add(new HistoryItem(HistoryIcon.PATTERN, HistoryResultIcon.get(record.getResult()), HistoryTitle.PATTERN, record.getPc(), record.getTimestamp(),
                                new authItem.OnItemClickListener() {
                                    @Override
                                    public void onClick() {}
                                })
                        );
                        break;
                    case "qr":
                        historyList.add(new HistoryItem(HistoryIcon.QR, HistoryResultIcon.get(record.getResult()), HistoryTitle.QR, record.getPc(), record.getTimestamp(),
                                new authItem.OnItemClickListener() {
                                    @Override
                                    public void onClick() {}
                                })
                        );
                        break;
                    case "fingerprint":
                        historyList.add(new HistoryItem(HistoryIcon.FINGERPRINT, HistoryResultIcon.get(record.getResult()), HistoryTitle.FINGERPRINT, record.getPc(), record.getTimestamp(),
                                new authItem.OnItemClickListener() {
                                    @Override
                                    public void onClick() {}
                                })
                        );
                        break;
                    case "usb":
                        historyList.add(new HistoryItem(HistoryIcon.USB, HistoryResultIcon.get(record.getResult()), HistoryTitle.USB, record.getPc(), record.getTimestamp(),
                                new authItem.OnItemClickListener() {
                                    @Override
                                    public void onClick() {}
                                })
                        );
                        break;
                }
            }
        }
        historyRecyclerView.setAdapter(new tollgateAdapter(historyList, R.layout.item_log));
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        historyRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);


        ImageButton history_button = (ImageButton) findViewById(R.id.history_back_button);
        history_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}