package com.elmuj.learnmath.data;

import android.app.Activity;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.model.LearnModel;
import com.elmuj.learnmath.model.TableModel;
import com.elmuj.learnmath.utils.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LearnData {

    public Activity activity;
    public String space;
    public String sign;
    public LearnModel learnModel;

    public LearnData(Activity activity, LearnModel learnModel) {
        this.activity = activity;
        this.learnModel = learnModel;
        space = activity.getString(R.string.space);
        sign = learnModel.sign;
    }


    public TableModel getTableModel(int i) {

        TableModel tableModel;
        int table_no = learnModel.table_no;

        tableModel = new TableModel();

        tableModel.question = table_no + space + sign + space + (i + 1) + " = ?";


        if (sign.equals(activity.getString(R.string.division_sign))) {

            double ans = (double) table_no / (double) (i + 1);

            tableModel.answer = Constant.returnFormatNumber((ans));
            tableModel.op_1 = Constant.returnFormatNumber((ans + 5));
            tableModel.op_2 = Constant.returnFormatNumber((ans + 2));
            tableModel.op_3 = Constant.returnFormatNumber((ans + 3));
        } else {
            int ans;
            if (sign.equals(activity.getString(R.string.addition_sign))) {
                ans = table_no + (i + 1);
            } else if (sign.equals(activity.getString(R.string.subtraction_sign))) {
                ans = table_no - (i + 1);
            } else {
                ans = table_no * (i + 1);
            }

            tableModel.answer = String.valueOf(ans);
            tableModel.op_1 = String.valueOf((ans + 5));
            tableModel.op_2 = String.valueOf((ans + 2));
            tableModel.op_3 = String.valueOf((ans + 3));
        }


        List<String> stringList = new ArrayList<>();
        stringList.add(tableModel.op_1);
        stringList.add(tableModel.op_2);
        stringList.add(tableModel.op_3);
        stringList.add(tableModel.answer);

        Collections.shuffle(stringList);
        tableModel.setOptionList(stringList);

        return tableModel;
    }



}
