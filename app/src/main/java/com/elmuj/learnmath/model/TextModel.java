package com.elmuj.learnmath.model;

import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class TextModel {

    public TextView textView;
    public int percentage;


    public TextView audienceView;
    public CardView cardView;
    public String string;


    public TextModel(TextView textView, CardView cardView) {
        this.textView = textView;
        this.cardView = cardView;
    }

    public TextModel(TextView textView, CardView cardView, TextView audienceView) {
        this.textView = textView;
        this.cardView = cardView;
        this.audienceView = audienceView;
    }


}
