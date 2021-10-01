E:\GitHub\Hacktober\learnmaths\app\src\main\java\com\elmuj\learnmath\service\NotificationService.java

package com.elmuj.learnmath.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.elmuj.learnmath.database.DatabaseAccess;
import com.elmuj.learnmath.model.ReminderModel;
import com.elmuj.learnmath.receiver.NotificationScheduler;
import com.elmuj.learnmath.utils.Constant;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.elmuj.learnmath.utils.Constant.simpleDateFormat;

public class NotificationService extends Service {
    public int counter = 0;
    DatabaseAccess manager;
    List<ReminderModel> reminderModels = new ArrayList<>();
    List<String> timeList = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "elmuj`  .permanence";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("RestartService");
        broadcastIntent.setClass(this, RestartService.class);
        this.sendBroadcast(broadcastIntent);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Timer timer;
    private TimerTask timerTask;

    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                Log.i("Count", "=========  " + (counter++));

                manager = DatabaseAccess.getInstance(getApplicationContext());
                manager.open();
                reminderModels = manager.getReminderData();
                manager.close();
                manager.open();
                timeList = manager.getReminderTimeList();
                manager.close();
                Calendar calendarTime = Calendar.getInstance();
                String currentTime = simpleDateFormat.format(calendarTime.getTime());

                Log.e("receive111", "==true");
                Gson gson = new Gson();
                String currentday = new SimpleDateFormat("EE", Locale.ENGLISH).format(calendarTime.getTime());


                if (Constant.getIsReminder(getApplicationContext())) {
                    if (timeList.contains(currentTime)) {
                        int i = timeList.indexOf(currentTime);
                        if (reminderModels.get(i).ison.equals("1")) {
                            ArrayList myList = new Gson().fromJson(reminderModels.get(i).repeat, ArrayList.class);
                            if (myList.contains(currentday)) {
                                NotificationScheduler.showReminderNotification(getApplicationContext(), currentTime);
                            }
                        }
                    }
                }

            }
        };
        timer.schedule(timerTask, 1000, 60000);

    }


    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}