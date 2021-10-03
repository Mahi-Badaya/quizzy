package com.elmuj.learnmath.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.elmuj.learnmath.R;


public class CenteredToolbar extends Toolbar {

    private TextView titleView;

    public CenteredToolbar(Context context) {
        this(context, null);
    }

    public CenteredToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.toolbarStyle);
    }

    public CenteredToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        titleView = new TextView(getContext());

        int textAppearanceStyleResId;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                new int[]{R.attr.titleTextAppearance}, defStyleAttr, 0);
        try {
            textAppearanceStyleResId = a.getResourceId(0, 0);
        } finally {
            ((TypedArray) a).recycle();
        }
        if (textAppearanceStyleResId > 0) {
            titleView.setTextAppearance(context, textAppearanceStyleResId);
        }
        Typeface typeface = ResourcesCompat.getFont(context, R.font.myriadpro_semibold);
        titleView.setTypeface(typeface);

        addView(titleView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        titleView.setX((getWidth() - titleView.getWidth()) / 2);
    }

    @Override
    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }
}