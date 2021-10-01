package com.elmuj.learnmath.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.elmuj.learnmath.R;

public class TopPagerAdapter extends PagerAdapter {
    RecyclerView number_recycler;
    private Activity mContext;
    int count;
    OnItemClick onItemClick;


    public TopPagerAdapter(Activity context, int count, OnItemClick onItemClick) {
        mContext = context;
        this.count = count;
        this.onItemClick = onItemClick;
    }


    public interface OnItemClick {
        void onTableItemClick(int position);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_top, collection, false);
        number_recycler = layout.findViewById(R.id.number_recycler);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 5);
        number_recycler.setLayoutManager(layoutManager);
        NumberAdapter numberAdapter;
        if (position == 0) {
            numberAdapter = new NumberAdapter(mContext, 1);
        } else {
            numberAdapter = new NumberAdapter(mContext, 11);
        }


        number_recycler.setAdapter(numberAdapter);
        numberAdapter.setClickListener(pos -> {
            if (onItemClick != null) {
                onItemClick.onTableItemClick(pos);
            }
        });
        numberAdapter.setSelectedPos(0);


        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}