package com.elmuj.learnmath.data;

import android.app.Activity;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.model.MainModel;
import com.elmuj.learnmath.model.SubModel;

import java.util.ArrayList;
import java.util.List;

public class MainData {


    public static final int LEARN_QUIZ = 1;
    public static final int INPUT = 3;
    public static final int TRUE_FALSE = 4;
    public static final int FIND_MISSING = 5;
    public static final int DUEL = 6;

    public static List<MainModel> getMainModel(Activity context) {
        List<MainModel> mainModelList = new ArrayList<>();

        List<String> operationList = new ArrayList<>();

        operationList.add(context.getString(R.string.learn_table));
        operationList.add(context.getString(R.string.option));
        operationList.add(context.getString(R.string.input));
        operationList.add(context.getString(R.string.true_false));
        operationList.add(context.getString(R.string.find_missing));
        operationList.add(context.getString(R.string.duel));

        List<Integer> iconList = new ArrayList<>();

        iconList.add(R.drawable.ic_homepage);
        iconList.add(R.drawable.ic_homepage);
        iconList.add(R.drawable.ic_homepage);
        iconList.add(R.drawable.ic_homepage);
        iconList.add(R.drawable.ic_homepage);
        iconList.add(R.drawable.ic_homepage);


        List<String> operationList1 = new ArrayList<>();


        operationList1.add(context.getString(R.string.option));
        operationList1.add(context.getString(R.string.input));
        operationList1.add(context.getString(R.string.true_false));
        operationList1.add(context.getString(R.string.find_missing));
        operationList1.add(context.getString(R.string.duel));


        List<Integer> iconList1 = new ArrayList<>();

        iconList1.add(R.drawable.ic_homepage);
        iconList1.add(R.drawable.ic_homepage);
        iconList1.add(R.drawable.ic_homepage);
        iconList1.add(R.drawable.ic_homepage);
        iconList1.add(R.drawable.ic_homepage);


        MainModel mainModel = new MainModel(context.getString(R.string.addition), true);
        mainModel.subModelList = getSubModelWithoutLearnQuiz(context, true);
        mainModel.iconList = iconList;
        mainModel.tableName = context.getString(R.string.addition_table);
        mainModel.sign = context.getString(R.string.addition_sign);
        mainModelList.add(mainModel);

        mainModel = new MainModel(context.getString(R.string.subtraction), false, true);
        mainModel.subModelList = getSubModelWithoutLearnQuiz(context, true);
        mainModel.iconList = iconList;
        mainModel.sign = context.getString(R.string.subtraction_sign);
        mainModel.tableName = context.getString(R.string.subtraction_table);
        mainModelList.add(mainModel);

        mainModel = new MainModel(context.getString(R.string.multiplication), false, true);
        mainModel.subModelList = getSubModelWithoutLearnQuiz(context, true);
        mainModel.iconList = iconList;
        mainModel.sign = context.getString(R.string.multiplication_sign);
        mainModel.tableName = context.getString(R.string.multiplication_table);
        mainModelList.add(mainModel);

        mainModel = new MainModel(context.getString(R.string.division), false, true);
        mainModel.subModelList = getSubModelWithoutLearnQuiz(context, true);
        mainModel.iconList = iconList;
        mainModel.sign = context.getString(R.string.division_sign);
        mainModel.tableName = context.getString(R.string.division_table);
        mainModelList.add(mainModel);

        mainModel = new MainModel(context.getString(R.string.square), false, false);
        mainModel.subModelList = getSubModelWithoutLearnQuiz(context, false);
        mainModel.iconList = iconList1;
        mainModel.sign = context.getString(R.string.square_sign);
        mainModel.tableName = context.getString(R.string.square_table);

        mainModelList.add(mainModel);

        mainModel = new MainModel(context.getString(R.string.square_root), false, false);
        mainModel.subModelList = getSubModelWithoutLearnQuiz(context, false);
        mainModel.iconList = iconList1;
        mainModel.tableName = context.getString(R.string.square_root_table);
        mainModel.sign = context.getString(R.string.root_sign);
        mainModelList.add(mainModel);

        mainModel = new MainModel(context.getString(R.string.cube), false, false);
        mainModel.subModelList = getSubModelWithoutLearnQuiz(context, false);
        mainModel.iconList = iconList1;
        mainModel.tableName = context.getString(R.string.cube_table);
        mainModel.sign = context.getString(R.string.cube_sign);
        mainModelList.add(mainModel);

        mainModel = new MainModel(context.getString(R.string.cube_root), false, false);
        mainModel.subModelList = getSubModelWithoutLearnQuiz(context, false);
        mainModel.iconList = iconList1;
        mainModel.tableName = context.getString(R.string.cube_root_table);
        mainModel.sign = context.getString(R.string.root_sign);
        mainModelList.add(mainModel);

        mainModel = new MainModel(context.getString(R.string.factorial), false, false);
        mainModel.subModelList = getSubModelWithoutLearnQuiz(context, false);
        mainModel.iconList = iconList1;
        mainModel.tableName = context.getString(R.string.factorial_table);
        mainModel.sign = context.getString(R.string.factorial_sign);
        mainModelList.add(mainModel);

        mainModel = new MainModel(context.getString(R.string.mixed), false, false);
        mainModel.subModelList = getSubModelWithoutLearnQuiz(context, false);
        mainModel.iconList = iconList1;
        mainModel.tableName = context.getString(R.string.mixed_table);
        mainModelList.add(mainModel);

        mainModel = new MainModel(context.getString(R.string.addition_subtraction), false, false);
        mainModel.subModelList = getSubModelWithoutLearnQuiz(context, false);
        mainModel.iconList = iconList1;
        mainModel.tableName = context.getString(R.string.addition_subtraction_table);
        mainModelList.add(mainModel);

        mainModel = new MainModel(context.getString(R.string.complicated_multiplication), false, false);
        mainModel.subModelList = getSubModelWithoutLearnQuiz(context, false);
        mainModel.iconList = iconList1;
        mainModel.tableName = context.getString(R.string.complicated_multiplication_table);
        mainModelList.add(mainModel);


        mainModel = new MainModel(context.getString(R.string.complicated_division), false, false);
        mainModel.subModelList = getSubModelWithoutLearnQuiz(context, false);
        mainModel.iconList = iconList1;
        mainModel.tableName = context.getString(R.string.complicated_division_table);
        mainModelList.add(mainModel);


        return mainModelList;
    }


    public static List<SubModel> getSubModelWithoutLearnQuiz(Activity activity, boolean isLearnQuiz) {

        List<SubModel> subModelList = new ArrayList<>();
        SubModel subModel = new SubModel();

        if (isLearnQuiz) {

            subModel.title = activity.getString(R.string.learn_table);
            subModel.type = activity.getString(R.string.learn_table_type);
            subModel.icon = R.drawable.ic_learn_table;
            subModel.TYPE_CODE = 1;
            subModelList.add(subModel);
        }

        subModel = new SubModel();
        subModel.title = activity.getString(R.string.option);
        subModel.type = activity.getString(R.string.option_type);
        subModel.icon = R.drawable.ic_option;
        subModel.TYPE_CODE = 2;
        subModelList.add(subModel);

        subModel = new SubModel();
        subModel.title = activity.getString(R.string.input);
        subModel.type = activity.getString(R.string.input_type);
        subModel.icon = R.drawable.ic_input;
        subModel.TYPE_CODE = 3;
        subModelList.add(subModel);

        subModel = new SubModel();
        subModel.title = activity.getString(R.string.true_false);
        subModel.type = activity.getString(R.string.true_false_type);
        subModel.icon = R.drawable.ic_true_false;
        subModel.TYPE_CODE = 4;
        subModelList.add(subModel);

        subModel = new SubModel();
        subModel.title = activity.getString(R.string.find_missing);
        subModel.type = activity.getString(R.string.find_missing_type);
        subModel.icon = R.drawable.ic_find_missing;
        subModel.TYPE_CODE = 5;
        subModelList.add(subModel);

        subModel = new SubModel();
        subModel.title = activity.getString(R.string.duel);
        subModel.type = activity.getString(R.string.duel_type);
        subModel.icon = R.drawable.ic_duel;
        subModel.TYPE_CODE = 6;
        subModelList.add(subModel);


        return subModelList;
    }
}
