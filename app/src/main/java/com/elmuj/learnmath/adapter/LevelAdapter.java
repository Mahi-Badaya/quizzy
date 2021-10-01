package com.elmuj.learnmath.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.elmuj.learnmath.R;
import com.elmuj.learnmath.model.MainModel;
import com.elmuj.learnmath.model.ProgressModel;
import com.elmuj.learnmath.model.SubModel;
import com.elmuj.learnmath.utils.Constant;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {


    private Activity context;
    private ItemClick itemClick;
    public MainModel mainModel;
    public SubModel subModel;
    public List<ProgressModel> progressModels;


    public LevelAdapter(Activity context, List<ProgressModel> progressModels) {
        this.context = context;
        this.progressModels = progressModels;
        mainModel = Constant.getMainModel(context);
        subModel = Constant.getSubModel(context);

    }


    public void setClickListener(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public LevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_level, parent, false);
        return new LevelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelAdapter.ViewHolder holder, final int position) {


        ProgressModel pModel = progressModels.get(position);

        holder.tv_title.setText(Constant.getAllTranslatedDigit(String.valueOf(pModel.level_no)));

        if (pModel.score > 0) {
            holder.tv_score.setText(Constant.getAllTranslatedDigit(String.valueOf(pModel.score)));
        } else {
            holder.tv_score.setText(null);
        }


        holder.progressView.removeAllViews();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;

            imageView.setLayoutParams(layoutParams);


            if (pModel.progress == 0) {
                imageView.setImageResource(R.drawable.ic_star_border_black_24dp);
            } else {
                if (Constant.getStarCount(pModel.progress) >= (i + 1)) {
                    imageView.setImageResource(R.drawable.ic_star_black_24dp);
                } else {
                    imageView.setImageResource(R.drawable.ic_star_border_black_24dp);
                }

            }
            holder.progressView.addView(imageView);

        }

        holder.tv_title.setBackgroundDrawable(Constant.customPrimaryViewOval(context));


        if (Constant.getNightMode(context)) {
            holder.cell.setCardBackgroundColor(Color.parseColor("#1E2834"));
        }


        Log.e("isShow===", "" + pModel.isShow);
    }

    public interface ItemClick {
        void itemClick(int position);
    }

    @Override
    public int getItemCount() {
        return progressModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_score;
        LinearLayout progressView, view;
        CardView cell;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_score = itemView.findViewById(R.id.tv_score);
            progressView = itemView.findViewById(R.id.progressView);
            cell = itemView.findViewById(R.id.cell);
            view = itemView.findViewById(R.id.view);


            itemView.setOnClickListener(v -> {
                if (itemClick != null) {
                    itemClick.itemClick(progressModels.get(getAdapterPosition()).level_no);
                }
            });

        }
    }
}
