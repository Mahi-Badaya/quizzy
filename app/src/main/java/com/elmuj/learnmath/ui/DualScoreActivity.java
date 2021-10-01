package com.elmuj.learnmath.ui;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.utils.CenteredToolbar;
import com.elmuj.learnmath.utils.Constant;
import com.elmuj.learnmath.view.DualScoreView;


public class DualScoreActivity extends BaseActivity {

    CenteredToolbar toolbar;
    CardView btn_retry, btn_home;
    LinearLayout view_2, view_1;
    ImageView img_retry, img_home;
    boolean isDraw = false;
    DualScoreView dualScoreView1, dualScoreView2;


    public String getTranslatedString(String s) {
        return Constant.getAllTranslatedDigit(s);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_score);
        init();

    }

    private void init() {
        isDraw = getIntent().getBooleanExtra(Constant.ISDraw, false);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> backIntent());
        getSupportActionBar().setTitle(getString(R.string.duel) + " " + getString(R.string.mode) + " " + Constant.getMainModel(this).title);

        btn_retry = findViewById(R.id.btn_retry);
        view_2 = findViewById(R.id.View_2);
        view_1 = findViewById(R.id.View_1);
        btn_home = findViewById(R.id.btn_home);
        img_home = findViewById(R.id.img_home);
        img_retry = findViewById(R.id.img_retry);

        int textColor = Constant.getThemeColor(this, R.attr.theme_text_color);
        img_home.getDrawable().setColorFilter(textColor, PorterDuff.Mode.SRC_IN);
        img_retry.getDrawable().setColorFilter(textColor, PorterDuff.Mode.SRC_IN);


        setView1();
        setView2();
        setClick();


        if (!isDraw) {
            dualScoreView2.setModel(Constant.getDuelModel(this, Constant.DUEL_MODEL2));
            dualScoreView1.setModel(Constant.getDuelModel(this, Constant.DUEL_MODEL1));
            if (Constant.getDuelModel(this, Constant.DUEL_MODEL1).score > Constant.getDuelModel(this, Constant.DUEL_MODEL2).score) {
                view_1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink));
            } else {
                view_2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink));
            }
        } else {
            dualScoreView1.setDraw(true);
            dualScoreView2.setDraw(true);
        }


    }

    private void setClick() {
        btn_retry.setOnClickListener(v -> startActivity(new Intent(this, DualActivity.class)));
        btn_home.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }


    public void setView1() {
        dualScoreView1 = new DualScoreView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dualScoreView1.setLayoutParams(layoutParams);
        view_1.addView(dualScoreView1);

    }

    public void setView2() {
        dualScoreView2 = new DualScoreView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dualScoreView2.setLayoutParams(layoutParams);
        view_2.addView(dualScoreView2);

    }


    @Override
    public void onBackPressed() {
        backIntent();
    }

    public void backIntent() {
        startActivity(new Intent(this, MainActivity.class));
    }


}
