package com.elmuj.learnmath.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.model.ReviewTestModel;
import com.elmuj.learnmath.utils.Constant;

import java.util.List;


public class ReivewTestListAdapter extends RecyclerView.Adapter<ReivewTestListAdapter.ViewHolder> {


    private Activity context;
    private ItemClick itemClick;
    private List<ReviewTestModel> strings;


    public ReivewTestListAdapter(Activity context, List<ReviewTestModel> strings) {
        this.context = context;
        this.strings = strings;
    }


    public void setListener(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ReivewTestListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pdf, parent, false);
        return new ReivewTestListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReivewTestListAdapter.ViewHolder holder, final int position) {

        holder.tv_no.setText(Constant.getAllTranslatedDigit(String.valueOf((position + 1))));
        holder.tv_title.setText(strings.get(position).title + (position + 1));

        holder.checkBox.setVisibility(View.GONE);
        holder.tv_no.setBackgroundDrawable(Constant.customPrimaryViewOval(context));

    }

    public interface ItemClick {
        void itemClick(int position);
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_no, tv_title;
        ImageView checkBox;
        CardView cell;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_no = itemView.findViewById(R.id.tv_no);
            checkBox = itemView.findViewById(R.id.check_box);
            tv_title = itemView.findViewById(R.id.tv_title);
            cell = itemView.findViewById(R.id.cell);
            itemView.setOnClickListener(v -> {
                if (itemClick != null) {
                    itemClick.itemClick(getAdapterPosition());
                }
            });

        }
    }
}
