package com.elmuj.learnmath.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.model.LearnModel;
import com.elmuj.learnmath.utils.Constant;

public class BottomPagerAdapter extends PagerAdapter {
    LinearLayout number_layout;
    private Activity mContext;
    int width;
    private int table_no = 1;


    public BottomPagerAdapter(Activity context, int width) {
        mContext = context;
        this.width = width;
    }

    public void setTableNo(int table_no) {
        this.table_no = table_no;
        notifyDataSetChanged();
    }


    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_bottom, collection, false);
        number_layout = layout.findViewById(R.id.layout);


        setTableView(position);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public void setTableView(int position) {

        int t_count = 0;
        if (position != 0) {
            String s = position + "1";
            t_count = Integer.parseInt(s) - 1;

            Log.e("t_count", "" + t_count + "==" + position);
        }
        number_layout.removeAllViews();
        for (int i = 0; i < 5; i++) {

            LinearLayout linearLayout = new LinearLayout(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            layoutParams.gravity = Gravity.CENTER;
            int margin = (int) mContext.getResources().getDimension(R.dimen.margin_top_10);
            if (Constant.DEVICE_1080 == width) {
                layoutParams.setMargins(margin, 5, margin, 5);
            } else {
                layoutParams.setMargins(margin, margin, margin, margin);
            }
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            t_count++;
            linearLayout.addView(getView(t_count));
            t_count++;
            linearLayout.addView(getView(t_count));

            number_layout.addView(linearLayout);
        }
    }

    public View getView(int count) {
        String space, sign, equal;
        space = mContext.getString(R.string.space);
        sign = mContext.getString(R.string.str_multi_sign);
        LearnModel learnModel = Constant.getLearnModel(mContext);

        if (learnModel != null) {
            sign = learnModel.sign;
        }

        equal = mContext.getString(R.string.sign_equal1);

        TextView subLinear = new TextView(mContext);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams1.weight = 1;
        layoutParams1.gravity = Gravity.CENTER;
        int margin = (int) mContext.getResources().getDimension(R.dimen.table_margin);


        layoutParams1.setMargins(margin, 15, margin, 15);
        subLinear.setLayoutParams(layoutParams1);
        subLinear.setGravity(Gravity.CENTER);
        subLinear.setTypeface(getTypeface());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            subLinear.setTextAppearance(mContext, android.R.style.TextAppearance_Large);
        } else {
            subLinear.setTextAppearance(android.R.style.TextAppearance_Large);
        }


        if (sign.equals(mContext.getString(R.string.division_sign))) {

            double ans = (double) table_no / (double) (count);
            subLinear.setText(Constant.getAllTranslatedDigit(table_no + space + sign + space + count + equal + (Constant.returnFormatNumber(ans))));

        } else {
            int ans;
            if (sign.equals(mContext.getString(R.string.addition_sign))) {
                ans = table_no + (count);
            } else if (sign.equals(mContext.getString(R.string.subtraction_sign))) {
                ans = table_no - (count);
            } else {
                ans = table_no * (count);
            }
            subLinear.setText(Constant.getAllTranslatedDigit(table_no + space + sign + space + count + equal + (ans)));
        }
        subLinear.setTextColor(Color.WHITE);
        subLinear.setBackgroundResource(R.drawable.cell_learn_table);
        return subLinear;
    }

    public Typeface getTypeface() {
        return ResourcesCompat.getFont(mContext, R.font.myriadpro_semibold);
    }


}