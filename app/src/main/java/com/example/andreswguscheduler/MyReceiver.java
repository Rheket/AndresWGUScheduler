package com.example.andreswguscheduler;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver {

    static int notificationId;
    String channel_id = "test";

    @Override
    public void onReceive(Context context, Intent intent) {


        String get = intent.getStringExtra("key");

        Toast.makeText(context, get, Toast.LENGTH_LONG).show();

        Notification n = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(get)
                .setContentTitle("Assessment Notification").build();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId++, n);






    }



}
