package com.elmuj.learnmath.ui;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.adapter.AdapterReminder;
import com.elmuj.learnmath.database.DatabaseAccess;
import com.elmuj.learnmath.model.ReminderModel;
import com.elmuj.learnmath.utils.CenteredToolbar;
import com.elmuj.learnmath.utils.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.elmuj.learnmath.utils.Constant.FROM_SETTINGS;
import static com.elmuj.learnmath.utils.Constant.setDefaultLanguage;
import static com.elmuj.learnmath.utils.Constant.simpleDateFormat;


public class ActivityReminder extends AppCompatActivity implements AdapterReminder.deleteInter {

    RecyclerView rec_remind;
    FloatingActionButton btn_add;
    AdapterReminder Adapter;
    DatabaseAccess manager;
    List<ReminderModel> reminderModelList = new ArrayList<>();
    String time = null;
    CenteredToolbar toolbar;
    boolean fromSetting = false;


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }


    public void setAdapter() {
        manager = DatabaseAccess.getInstance(getApplicationContext());
        manager.open();
        reminderModelList = manager.getReminderData();
        manager.close();
        Adapter = new AdapterReminder(reminderModelList, this);
        rec_remind.setAdapter(Adapter);
        Adapter.setInterface(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultLanguage(this);
        setContentView(R.layout.activity_reminder);
        fromSetting = getIntent().getBooleanExtra(FROM_SETTINGS, false);

        init();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.reminder));
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        rec_remind.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));

        setAdapter();

        btn_add.setOnClickListener(v -> {
            Calendar CurrentTime = Calendar.getInstance();
            int hour = CurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = CurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;


            mTimePicker = new TimePickerDialog(ActivityReminder.this, R.style.TimePickerDialogStyle, (view, hourOfDay, minute1) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute1);
                time = simpleDateFormat.format(calendar.getTime());
                showDialog();
            }, hour, minute, false);
            mTimePicker.show();
        });


    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        rec_remind = findViewById(R.id.rec_remind);
        btn_add = findViewById(R.id.btn_add);
    }


    public void showDialog() {
        setDefaultLanguage(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        setDefaultLanguage(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_repeate_days, null);
        builder.setView(view);

        TextView btn_ok, btn_cancel;


        CheckBox cb_sun = view.findViewById(R.id.cb_sun);
        CheckBox cb_mon = view.findViewById(R.id.cb_mon);
        CheckBox cb_tue = view.findViewById(R.id.cb_tue);
        CheckBox cb_wed = view.findViewById(R.id.cb_wed);
        CheckBox cb_thu = view.findViewById(R.id.cb_thu);
        CheckBox cb_fri = view.findViewById(R.id.cb_fri);
        CheckBox cb_sat = view.findViewById(R.id.cb_sat);
        btn_ok = view.findViewById(R.id.btn_ok);
        btn_cancel = view.findViewById(R.id.btn_cancel);

        final List<CheckBox> checkBoxes = new ArrayList<>();
        checkBoxes.add(cb_mon);
        checkBoxes.add(cb_tue);
        checkBoxes.add(cb_wed);
        checkBoxes.add(cb_thu);
        checkBoxes.add(cb_fri);
        checkBoxes.add(cb_sat);
        checkBoxes.add(cb_sun);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        builder.setTitle(getResources().getString(R.string.repeat));
        final List<String> list = new ArrayList<>();


        final AlertDialog dialog = builder.create();
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        btn_ok.setOnClickListener(view12 -> {
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isChecked()) {
                    list.add(Constant.getReminderDaysList().get(i));
                }
            }
            String Level = new Gson().toJson(list);
            manager.open();
            manager.insertReminder(time, Level, true);
            manager.close();
            Intent intent = new Intent(this, ActivityReminder.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            dialog.dismiss();
        });

        btn_cancel.setOnClickListener(view12 -> {
            dialog.dismiss();
        });


    }

    @Override
    public void onDoDelete(int id) {
        manager.open();
        manager.deleteReminder(id);
        manager.close();
        Intent intent = new Intent(this, ActivityReminder.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onDoUpdate(int id) {
        setAdapter();
    }


}
