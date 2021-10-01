package com.elmuj.learnmath.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


import com.elmuj.learnmath.R;
import com.elmuj.learnmath.ui.MainActivity;
import com.elmuj.learnmath.utils.Constant;


public class NotificationScheduler {
    public static void showNotification(Context context, int level) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.cancelAll();
        Intent intent1 = new Intent(context, MainActivity.class);

        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent1);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT
                | PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notifications_none_black_24dp).setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getString(R.string.level) + " " + Constant.getAllTranslatedDigit(String.valueOf(level)) + " " + context.getString(R.string.level_complete))
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);
        notificationManager.notify(123, mBuilder.build());
    }


    public static void showReminderNotification(Context context, String time) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context, MainActivity.class);

        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent1);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT
                | PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                .setContentTitle(context.getResources().getString(R.string.app_name) + " " + time)
                .setContentText(context.getResources().getString(R.string.app_name) + " " + context.getResources().getString(R.string.reminder))
                .setAutoCancel(true).setContentIntent(resultPendingIntent);
        assert notificationManager != null;
        notificationManager.notify(1, mBuilder.build());

    }


}
