package com.elmuj.learnmath.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.elmuj.learnmath.R;
import com.elmuj.learnmath.utils.Constant;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {


    private List<String> languageList;
    private Activity context;
    private OnLanguageClick onLanguageClick;

    public LanguageAdapter(Activity context, List<String> languageList, OnLanguageClick onLanguageClick) {
        this.context = context;
        this.languageList = languageList;
        this.onLanguageClick = onLanguageClick;
    }

    @NonNull
    @Override
    public LanguageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_language, parent, false);
        return new ViewHolder(view);
    }


    public interface OnLanguageClick {

        void onClick(String s);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageAdapter.ViewHolder holder, int position) {
        holder.txt_language.setText(languageList.get(position));
        String path = "flag/" + "flag_" + Constant.getLanguageCodeFromLanguage(context, languageList.get(position)) + ".png";
        holder.img_flag.setImageBitmap(Constant.getBitmapFromAsset(context, path));

        if (Constant.getLanguageCode(context).equalsIgnoreCase(Constant.getLanguageCodeFromLanguage(context, languageList.get(position)))) {
            holder.cell.setBackgroundResource(R.drawable.language_select);
            holder.txt_language.setTextColor(Color.WHITE);
        } else {
            holder.txt_language.setTextColor(Constant.getThemeColor(context, R.attr.theme_text_color));
            holder.cell.setBackgroundResource(R.drawable.language_unselect);
        }
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_flag;
        LinearLayout cell;
        TextView txt_language;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_language = itemView.findViewById(R.id.txt_language);
            img_flag = itemView.findViewById(R.id.img_flag);
            cell = itemView.findViewById(R.id.cell);

            itemView.setOnClickListener(view -> {

                if (onLanguageClick != null) {
                    onLanguageClick.onClick(languageList.get(getAdapterPosition()));
                }
            });
        }
    }
}
