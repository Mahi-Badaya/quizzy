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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.model.MainModel;
import com.elmuj.learnmath.model.SubModel;
import com.elmuj.learnmath.utils.Constant;

import java.util.List;

import static com.elmuj.learnmath.utils.Constant.getTextString;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {


    private Activity context;
    private MainItemClick mainItemClick;
    private List<MainModel> mainModels;


    public MainAdapter(Activity context, List<MainModel> mainModels) {
        this.context = context;
        this.mainModels = mainModels;
    }

    public interface MainItemClick {
        void mainItemClick(int main_id, int position, SubModel subModel, MainModel mainModel);

        void expandClick(int position);
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false);
        return new ViewHolder(view);
    }

    public void setMainClickListener(MainItemClick mainClickListener) {
        this.mainItemClick = mainClickListener;
    }


    public void setExapndArray(List<MainModel> mainModels) {
        this.mainModels = mainModels;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, final int pos) {

        Constant.setDefaultLanguage(context);
        holder.textView.setText(mainModels.get(pos).title);

        final MainModel mainModel = mainModels.get(pos);

        if (pos == 0 || pos == 1) {
            holder.text_total_question.setText(getTextString((mainModels.get(pos).totalQuestion) + context.getString(R.string.str_space) + context.getString(R.string.quiz)));
        } else {
            holder.text_total_question.setText(getTextString(mainModel.totalQuestion + context.getString(R.string.str_space) + context.getString(R.string.quiz)));
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3);
        holder.recyclerView.setLayoutManager(layoutManager);

        if (mainModel.subModelList != null) {

            SubAdapter subAdapter = new SubAdapter(context, mainModel.subModelList);
            holder.recyclerView.setAdapter(subAdapter);
            subAdapter.setSubClickListener((position, subModel) -> {
                if (mainItemClick != null) {
                    mainItemClick.mainItemClick(pos, position, subModel, mainModel);
                    notifyDataSetChanged();
                }
            });
        }


        String test = mainModels.get(pos).title;
        char first = test.charAt(0);

        holder.text_no.setText(String.valueOf(first));


        if (mainModel.isExpand) {
            holder.view.setBackgroundResource(R.drawable.bg_group);
            holder.expandView.setVisibility(View.VISIBLE);
            holder.btn_next.setImageDrawable(getThemeDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
        } else {
            holder.view.setBackgroundResource(R.drawable.bg_group_radius);
            holder.btn_next.setImageDrawable(getThemeDrawable(R.drawable.ic_navigate_next_black_24dp));
            holder.expandView.setVisibility(View.GONE);
        }


    }

    public Drawable getThemeDrawable(int drawableID) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableID);
        assert drawable != null;
        drawable.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        LinearLayout expandView;
        RelativeLayout view;
        RelativeLayout cell;
        TextView textView, text_no, text_total_question;
        ImageView btn_next;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            textView = itemView.findViewById(R.id.textView);
            text_total_question = itemView.findViewById(R.id.text_total_question);
            btn_next = itemView.findViewById(R.id.btn_next);
            cell = itemView.findViewById(R.id.cell);
            view = itemView.findViewById(R.id.view);
            expandView = itemView.findViewById(R.id.expandView);
            text_no = itemView.findViewById(R.id.text_no);


            itemView.setOnClickListener(v -> {
                if (mainItemClick != null) {
                    mainItemClick.expandClick(getAdapterPosition());
                }
            });


        }
    }
}
