package com.elmuj.learnmath.adapter;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.elmuj.learnmath.R;
import com.elmuj.learnmath.model.SubModel;
import com.elmuj.learnmath.utils.Constant;

import java.util.List;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {


    private Activity context;
    private SubItemClick subItemClick;
    private List<SubModel> subModelList;


    SubAdapter(Activity context, List<SubModel> subModelList) {
        this.context = context;
        this.subModelList = subModelList;
    }


    void setSubClickListener(SubItemClick subClickListener) {
        this.subItemClick = subClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sub, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Drawable drawable;
        drawable = context.getResources().getDrawable(subModelList.get(position).icon);

        assert drawable != null;
        drawable.mutate();
        drawable.setColorFilter(Constant.getThemeColor(context, R.attr.colorPrimary), PorterDuff.Mode.SRC_IN);
        holder.imageView.setImageDrawable(drawable);

        holder.textView.setText(subModelList.get(position).title);

    }


    public interface SubItemClick {
        void subItemClick(int position, SubModel title);
    }

    @Override
    public int getItemCount() {
        return subModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RelativeLayout cell;
        LinearLayout view;
        ImageView imageView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            cell = itemView.findViewById(R.id.cell);
            imageView = itemView.findViewById(R.id.imageView);
            view = itemView.findViewById(R.id.view);

            itemView.setOnClickListener(v -> {
                if (subItemClick != null) {
                    subItemClick.subItemClick(getAdapterPosition(), subModelList.get(getAdapterPosition()));
                }
            });

        }
    }
}
