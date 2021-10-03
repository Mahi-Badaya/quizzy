package com.elmuj.learnmath.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.elmuj.learnmath.R;
import com.elmuj.learnmath.adapter.ColorAdapter;
import com.elmuj.learnmath.utils.CenteredToolbar;
import com.elmuj.learnmath.utils.Constant;

import java.util.Objects;

import static com.elmuj.learnmath.ui.MainActivity.showFeedbackDialog;
import static com.elmuj.learnmath.ui.MainActivity.showRatingDialog;
import static com.elmuj.learnmath.utils.Constant.setDefaultLanguage;


public class ActivitySetting extends AppCompatActivity {


    ImageView image_vibrate, image_sound, image_color;
    CardView btn_color, btn_privacy_policy, btn_share, btn_rate, btn_feedback;
    ImageView image_mode;
    ImageView image_reminder;
    Intent mServiceIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultLanguage(this);
        setContentView(R.layout.activity_setting);
        init();
    }


    private void init() {

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        toolbar.setNavigationOnClickListener(view -> backIntent());

        getSupportActionBar().setTitle(getString(R.string.action_settings));


        image_mode = findViewById(R.id.image_mode);
        image_sound = findViewById(R.id.image_sound);
        image_vibrate = findViewById(R.id.image_vibrate);
        image_reminder = findViewById(R.id.image_reminder);
        btn_privacy_policy = findViewById(R.id.btn_privacy_policy);
        btn_share = findViewById(R.id.btn_share);
        btn_rate = findViewById(R.id.btn_rate);
        image_color = findViewById(R.id.image_color);
        btn_feedback = findViewById(R.id.btn_feedback);
        btn_color = findViewById(R.id.btn_color);


        setClick();
        image_color.setBackground(Constant.customViewOval(ContextCompat.getColor(this, Constant.getThemeList().get(Constant.getThemePosition(ActivitySetting.this)))));

        setOnOffVibrate();
        setOnOffSound();
        setThemeMode();
        setReminder();
    }

    public void refreshAcitivity() {
        Intent intent = new Intent(this, ActivitySetting.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void setClick() {
        image_mode.setOnClickListener(v -> {

            Constant.setNightMode(getApplicationContext(), !Constant.getNightMode(getApplicationContext()));
            setThemeMode();
            refreshAcitivity();
        });

        image_sound.setOnClickListener(v -> {
            Constant.setSound(getApplicationContext(), !Constant.getSound(getApplicationContext()));
            setOnOffSound();
        });

        image_vibrate.setOnClickListener(v -> {
            Constant.setVibrate(getApplicationContext(), !Constant.getVibrate(getApplicationContext()));
            setOnOffVibrate();
        });

        image_reminder.setOnClickListener(v -> {
            Constant.setIsReminder(getApplicationContext(), !Constant.getIsReminder(getApplicationContext()));
            setReminder();
        });

        btn_feedback.setOnClickListener(v -> {
            showFeedbackDialog(this);
        });
        btn_rate.setOnClickListener(v -> {
            showRatingDialog(this);
        });
        btn_share.setOnClickListener(v -> {
            Constant.share(this);
        });

        btn_privacy_policy.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.privacy_policy_link)));
            startActivity(browserIntent);
        });

        btn_color.setOnClickListener(v -> showColorThemeDialog());

    }


    public void setOnOffSound() {
        if (Constant.getSound(getApplicationContext())) {
            image_sound.setBackgroundResource(R.drawable.ic_toggle_on);
        } else {
            image_sound.setBackgroundResource(R.drawable.ic_toggle_off);
        }

    }

    public void setOnOffVibrate() {
        if (Constant.getVibrate(getApplicationContext())) {
            image_vibrate.setBackgroundResource(R.drawable.ic_toggle_on);
        } else {
            image_vibrate.setBackgroundResource(R.drawable.ic_toggle_off);
        }

    }

    public void setThemeMode() {
        if (Constant.getNightMode(getApplicationContext())) {
            image_mode.setBackgroundResource(R.drawable.ic_toggle_on);
        } else {
            image_mode.setBackgroundResource(R.drawable.ic_toggle_off);
        }

    }

    public void setReminder() {
        if (Constant.getIsReminder(getApplicationContext())) {
            image_reminder.setBackgroundResource(R.drawable.ic_toggle_on);
        } else {
            image_reminder.setBackgroundResource(R.drawable.ic_toggle_off);
        }

    }


    public void backIntent() {
        startActivity(new Intent(this, MainActivity.class));
    }


    public void showColorThemeDialog() {
        setDefaultLanguage(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_color, null);
        builder.setView(view);

        TextView btn_ok, btn_cancel;

        btn_ok = view.findViewById(R.id.btn_ok);
        btn_cancel = view.findViewById(R.id.btn_cancel);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

        final AlertDialog dialog = builder.create();
        dialog.show();
        ColorAdapter colorAdapter = new ColorAdapter(this, Constant.getThemeList(), s -> {

            Constant.setThemeColor(ActivitySetting.this, s);
            dialog.dismiss();
            refreshAcitivity();

        });
        recyclerView.setAdapter(colorAdapter);


        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        btn_cancel.setOnClickListener(view1 -> dialog.dismiss());
        btn_ok.setOnClickListener(view12 -> {
            dialog.dismiss();
        });

    }


    @Override
    public void onBackPressed() {
        backIntent();
    }


}
