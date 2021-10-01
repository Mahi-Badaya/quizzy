package com.elmuj.learnmath.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.elmuj.learnmath.R;
import com.elmuj.learnmath.utils.Constant;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {


    private List<Integer> colorList;
    private Activity context;
    private OnColorClick onColorClick;

    public ColorAdapter(Activity context, List<Integer> colorList, OnColorClick onColorClick) {
        this.context = context;
        this.colorList = colorList;
        this.onColorClick = onColorClick;
    }

    @NonNull
    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_color, parent, false);
        return new ViewHolder(view);
    }


    public interface OnColorClick {

        void onClick(int s);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.ViewHolder holder, int position) {
        holder.cell.setBackground(Constant.customViewOval(ContextCompat.getColor(context, colorList.get(position))));


        if (position == Constant.getThemePosition(context)) {
            holder.img_check.setVisibility(View.VISIBLE);
        } else {
            holder.img_check.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout cell;
        ImageView img_check;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cell = itemView.findViewById(R.id.cell);
            img_check = itemView.findViewById(R.id.img_check);

            itemView.setOnClickListener(view -> {

                if (onColorClick != null) {
                    onColorClick.onClick(getAdapterPosition());
                }
            });
        }
    }
}
