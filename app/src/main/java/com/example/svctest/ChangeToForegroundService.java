package com.example.svctest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ChangeToForegroundService {

    static void toForeground(Service service, int iconId, String channelId, int id) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            Object mgr = ((Context)service).getSystemService(Context.NOTIFICATION_SERVICE);
            ((NotificationManager)mgr).createNotificationChannel(channel);

            Intent intent = new Intent(((Context)service), MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(((Context)service), 0, intent, 0);
            Notification notification = new NotificationCompat.Builder(((Context)service), channelId)
                    .setContentIntent(pi)
                    .setAutoCancel(false)
                    .setSmallIcon(iconId)
                    .setTicker("Foreground Service Start")
                    .setContentTitle("Socket server")
                    .setContentText("Running...")
                    .build();
            service.startForeground(id, notification);
        }
    }
}
