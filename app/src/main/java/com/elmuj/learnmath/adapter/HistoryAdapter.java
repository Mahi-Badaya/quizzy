package com.elmuj.learnmath.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.elmuj.learnmath.R;
import com.elmuj.learnmath.model.HistoryModel;
import com.elmuj.learnmath.utils.Constant;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private Activity context;
    private List<HistoryModel> historyNotesList;


    public HistoryAdapter(Activity context, List<HistoryModel> historyNotes) {
        this.context = context;
        this.historyNotesList = historyNotes;
    }


    @NonNull
    @Override
    public HistoryAdapter.HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryHolder holder, int position) {
        HistoryModel historyNotes = historyNotesList.get(position);

        Constant.setDefaultLanguage(context);
        if (position == 0) {
            holder.txt_id.setText(context.getString(R.string.no));
            setBg(holder.txt_id);
            setBg(holder.text_question);
            setBg(holder.text_answer);
            setBg(holder.text_userAnswer);

        } else {
            holder.txt_id.setText(Constant.getAllTranslatedDigit(String.valueOf(position)));

            setBg1(holder.txt_id);
            setBg1(holder.text_question);
            setBg1(holder.text_answer);
            setBg1(holder.text_userAnswer);

        }
        if (historyNotes != null) {
            holder.text_question.setText(Constant.getAllTranslatedDigit(historyNotes.question));
            holder.text_answer.setText(Constant.getAllTranslatedDigit(historyNotes.answer));
            holder.text_userAnswer.setText(Constant.getAllTranslatedDigit(historyNotes.userAnswer));
        }


    }

    private void setBg1(TextView textView) {
        textView.setBackgroundResource(getDrawableBg());
        textView.setTextColor(Constant.getThemeColor(context, R.attr.theme_text_color));
    }

    private void setBg(TextView textView) {
        textView.setBackground(getDrawable());
        textView.setTextColor(Color.WHITE);
    }

    public Drawable getDrawable() {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Constant.getThemeColor(context, R.attr.colorPrimary));
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(1, ContextCompat.getColor(context, R.color.white));
        return gd;
    }

    private int getDrawableBg() {


        return R.drawable.table_content_cell;
    }


    @Override
    public int getItemCount() {
        return historyNotesList.size();
    }

    class HistoryHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView text_answer, text_question, text_userAnswer, txt_id;

        HistoryHolder(@NonNull View itemView) {
            super(itemView);

            txt_id = itemView.findViewById(R.id.txt_id);
            imageView = itemView.findViewById(R.id.imageView);
            text_question = itemView.findViewById(R.id.text_question);
            text_answer = itemView.findViewById(R.id.txt_answer);
            text_userAnswer = itemView.findViewById(R.id.txt_userAnswer);

        }
    }
}
