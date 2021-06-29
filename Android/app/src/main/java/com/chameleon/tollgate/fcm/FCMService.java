package com.chameleon.tollgate.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.chameleon.tollgate.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMService extends FirebaseMessagingService {
    private final String LOG_TAG = "Tollgate_FCM";
    private final String NOTI_ID = "tollgate_fcm";
    private final String NOTI_NAME = "tollgate_fcm";

    @Override
    public void onNewToken(String token) {
        Log.d(LOG_TAG, "Refreshed token: " + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMsg) //앱이 포그라운드일 때만 작동
    {
        Log.d(LOG_TAG, "Message received");
        Log.d(LOG_TAG, "Data : " + remoteMsg.getData());
        sendNotification(remoteMsg);
    }

    private void sendNotification(RemoteMessage remoteMsg)
    {
        Intent intent = new Intent(remoteMsg.getNotification().getClickAction());
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
