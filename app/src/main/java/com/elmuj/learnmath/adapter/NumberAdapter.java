package com.elmuj.learnmath.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.utils.Constant;


public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.ViewHolder> {

    private Activity context;
    private int selected_pos;
    private int table_position;
    private setClick setClick;


    public NumberAdapter(Activity context, int table_position) {
        this.table_position = table_position;
        this.context = context;
    }

    public void setClickListener(setClick setClick) {
        this.setClick = setClick;
    }

    public interface setClick {

        void onTableNoClick(int pos);
    }

    public void setSelectedPos(int selected_pos) {
        this.selected_pos = selected_pos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NumberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        layout = R.layout.item_number;
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberAdapter.ViewHolder holder, int position) {


        if (selected_pos == position) {
            holder.cardView.setCardBackgroundColor(Constant.getThemeColor(context, R.attr.colorPrimary));
            holder.textView.setTextColor(Color.WHITE);
        } else {
            holder.cardView.setCardBackgroundColor(Constant.getThemeColor(context, R.attr.theme_cell_color));
            holder.textView.setTextColor(Constant.getThemeColor(context, R.attr.theme_text_color));
        }


        holder.textView.setText(String.valueOf((position + table_position)));

        holder.textView.setOnClickListener(view -> {
            if (setClick != null) {
                setClick.onTableNoClick(Integer.parseInt(holder.textView.getText().toString()));
                selected_pos = position;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return Constant.TABLE_SIZE;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }
}
