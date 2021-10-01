package com.elmuj.learnmath.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.database.DatabaseAccess;
import com.elmuj.learnmath.model.ReminderModel;

import java.util.List;


public class AdapterReminder extends RecyclerView.Adapter<AdapterReminder.MyViewHolder> {

    private List<ReminderModel> reminderModels;
    private Activity context;
    private deleteInter deleteInterObj;
    private DatabaseAccess manager;

    public AdapterReminder(List<ReminderModel> reminderModels, Activity context) {
        this.reminderModels = reminderModels;
        this.context = context;
        manager = DatabaseAccess.getInstance(context);
    }

    public void setInterface(deleteInter reminder) {
        deleteInterObj = reminder;
    }

    @NonNull
    @Override
    public AdapterReminder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reminder, viewGroup, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterReminder.MyViewHolder holder, @SuppressLint("RecyclerView") final int i) {
        String days;
        days = reminderModels.get(i).repeat.replaceAll("[]\\[\"|\"$]", "");
        String val = days.replace("Sun", context.getResources().getString(R.string.sun)).
                replace("Mon", context.getResources().getString(R.string.mon)).
                replace("Tue", context.getResources().getString(R.string.tue)).
                replace("Wed", context.getResources().getString(R.string.wed))
                .replace("Thu", context.getResources().getString(R.string.thu))
                .replace("Fri", context.getResources().getString(R.string.fri))
                .replace("Sat", context.getResources().getString(R.string.sat));

        holder.tvDays.setText(val);
        Log.e("Json==", "" + reminderModels.get(i).ison);
        holder.tvTime.setText("" + reminderModels.get(i).time);
        if (reminderModels.get(i).ison.equals("1")) {
            holder.switchJson.setChecked(true);
        } else {
            holder.switchJson.setChecked(false);
        }


        holder.switchJson.setOnCheckedChangeListener((buttonView, isChecked) -> {
            manager.open();
            if (isChecked) {
                manager.updateIsON(reminderModels.get(i).id, "1");
            } else {
                manager.updateIsON(reminderModels.get(i).id, "0");
            }
            manager.close();
            if (deleteInterObj != null) {
                deleteInterObj.onDoUpdate(reminderModels.get(i).id);
            }
        });


        holder.imgDelete.setOnClickListener(v -> {
            if (deleteInterObj != null) {
                deleteInterObj.onDoDelete(reminderModels.get(i).id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminderModels.size();
    }

    public interface deleteInter {
        void onDoDelete(int id);

        void onDoUpdate(int id);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime, tvDays;
        Switch switchJson;
        ImageView imgDelete;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDays = itemView.findViewById(R.id.tv_days);
            tvTime = itemView.findViewById(R.id.tv_time);
            switchJson = itemView.findViewById(R.id.switch_ison);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }
    }
}
