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


    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}