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


    public void refreshAcitivity() {
        Intent intent = new Intent(this, ActivitySetting.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
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
