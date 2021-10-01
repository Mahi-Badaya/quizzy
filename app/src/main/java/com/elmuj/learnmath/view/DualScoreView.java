package com.elmuj.learnmath.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import android.util.AttributeSet;

import android.view.View;
import android.widget.FrameLayout;

import android.widget.TextView;


import com.elmuj.learnmath.R;
import com.elmuj.learnmath.model.DualScoreModel;
import com.elmuj.learnmath.utils.Constant;


public class DualScoreView extends FrameLayout {

    TextView tv_score, text_title, text_sub_title;
    DualScoreModel dualScoreModel;
    Activity activity;


    public DualScoreView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }


    public DualScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DualScoreView(Activity activity) {
        super(activity);
        this.activity = activity;

        initView();
    }

    public void setModel(DualScoreModel dualScoreModel) {
        this.dualScoreModel = dualScoreModel;
        setData();
        invalidate();
    }

    boolean isDraw = false;

    public void setDraw(boolean isDraw) {
        this.isDraw = isDraw;
        setData();
        invalidate();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        final View view = inflate(getContext(), R.layout.dual_score_part, null);

        tv_score = view.findViewById(R.id.tv_score);
        text_title = view.findViewById(R.id.text_title);
        text_sub_title = view.findViewById(R.id.text_sub_title);


        addView(view);
    }


    public String getTranslatedString(String s) {
        return Constant.getAllTranslatedDigit(s);
    }

    public void setData() {


        if (isDraw) {
            text_title.setText(getContext().getString(R.string.it_s_draw));
            text_sub_title.setVisibility(GONE);
            tv_score.setVisibility(GONE);

        } else {
            text_title.setText(dualScoreModel.title);
            text_sub_title.setText(dualScoreModel.sub_title);
            tv_score.setText(getTranslatedString(getContext().getString(R.string.score) + " " + dualScoreModel.score));
        }

    }


}
