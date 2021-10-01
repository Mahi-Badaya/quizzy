package com.elmuj.learnmath.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CenterLineTextView extends AppCompatTextView {

    private final Paint mPaint = new Paint();
    private int mStroke;

    public CenterLineTextView(Context context) {
        super(context);
        init();
    }

    public CenterLineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CenterLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }
        setGravity(Gravity.CENTER);
        mStroke = 10;
    }

    int color = Color.RED;

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStrokeWidth(mStroke);
        mPaint.setColor(color);

        int top = getHeight() / 2;
        int left = 0; //start at the left margin
        int right = getWidth(); //we draw all the way to the right
        int bottom = top + 2; //we want the line to be 2 pixel thick
        canvas.drawRect(left, top, right, bottom, mPaint);


    }
}