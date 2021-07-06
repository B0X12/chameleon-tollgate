package com.chameleon.tollgate.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.chameleon.tollgate.R;
import com.chameleon.tollgate.define.LogTag;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class FCMService extends FirebaseMessagingService {
    private final String NOTI_ID = "tollgate_fcm";
    private final String NOTI_NAME = "tollgate_fcm";

    @Override
    public void onNewToken(String token) {
        Log.d(LogTag.FCM, "Refreshed token: " + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMsg) //앱이 포그라운드일 때만 작동
    {
        Log.d(LogTag.FCM, "Message received");
        Log.d(LogTag.FCM, "Data : " + remoteMsg.getData());
        sendNotification(remoteMsg);
    }

    private void sendNotification(RemoteMessage remoteMsg)
    {
        Intent intent = new Intent(remoteMsg.getNotification().getClickAction());
        for(Map.Entry<String, String> entry : remoteMsg.getData().entrySet())
            intent.putExtra(entry.getKey(), entry.getValue());

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notiMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notiMgr.createNotificationChannel(new NotificationChannel(NOTI_ID, NOTI_NAME, NotificationManager.IMPORTANCE_HIGH));
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTI_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(remoteMsg.getNotification().getTitle())
                .setContentText(remoteMsg.getNotification().getBody())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        notiMgr.notify(0, builder.build());
    }
}
