package com.elmuj.learnmath.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.model.LearnModel;
import com.elmuj.learnmath.model.TableModel;
import com.elmuj.learnmath.model.TextModel;
import com.elmuj.learnmath.utils.CenterLineTextView;
import com.elmuj.learnmath.utils.CenteredToolbar;
import com.elmuj.learnmath.utils.Constant;
import com.elmuj.learnmath.data.LearnData;
import com.elmuj.learnmath.utils.ConstantDialog;
import com.elmuj.learnmath.utils.ExitInterface;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import static com.elmuj.learnmath.utils.Constant.DELAY_SEOCND;
import static com.elmuj.learnmath.utils.Constant.setDefaultLanguage;
import static com.thekhaeng.pushdownanim.PushDownAnim.DEFAULT_PUSH_DURATION;
import static com.thekhaeng.pushdownanim.PushDownAnim.DEFAULT_RELEASE_DURATION;
import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;


public class LearnQuizActivity extends BaseActivity implements View.OnClickListener, ExitInterface {

    Vibrator vibe;
    CardView card_1, card_2, card_3, card_4;
    CenterLineTextView textView1;
    TextView tv_right_count, tv_wrong_count,
            tv_question_count, tv_total_count, btn_op_1, btn_op_2, btn_op_3, btn_op_4;
    List<TableModel> quizModelList = new ArrayList<>();
    ProgressDialog progressDialog;
    LearnModel learnModel;
    boolean isDialogOpen = false;
    boolean isCount = false;
    ImageView imageView;
    List<TextModel> optionViewList = new ArrayList<>();
    TableModel quizModel;
    int position, wrong_answer_count, right_answer_count;
    Handler handler = new Handler();
    RelativeLayout layout_cell;
    CenteredToolbar toolbar;
    LinearLayout linear_1, linear_2, linear_3, linear_4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_quiz);
        init();
    }


    private void init() {

        vibe = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        learnModel = Constant.getLearnModel(this);

        progressDialog = new ProgressDialog(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            cancelTimer();
            ConstantDialog.showExitDialog(this, this);
        });


        imageView = findViewById(R.id.imageView);
        tv_question_count = findViewById(R.id.tv_question_count);
        tv_right_count = findViewById(R.id.tv_right_count);
        linear_1 = findViewById(R.id.linear_1);
        linear_2 = findViewById(R.id.linear_2);
        linear_3 = findViewById(R.id.linear_3);
        linear_4 = findViewById(R.id.linear_4);
        tv_wrong_count = findViewById(R.id.tv_wrong_count);

        layout_cell = findViewById(R.id.layout_cell);
        tv_total_count = findViewById(R.id.tv_total_count);
        textView1 = findViewById(R.id.textQuestion);
        card_1 = findViewById(R.id.card_1);
        card_2 = findViewById(R.id.card_2);
        card_3 = findViewById(R.id.card_3);
        card_4 = findViewById(R.id.card_4);
        btn_op_1 = findViewById(R.id.btn_op_1);
        btn_op_2 = findViewById(R.id.btn_op_2);
        btn_op_3 = findViewById(R.id.btn_op_3);
        btn_op_4 = findViewById(R.id.btn_op_4);


        getSupportActionBar().setTitle(getString(R.string.learn_table));
        quizModelList.clear();
        setClick();

        setScore();
        new GetAllData().execute();
    }


    private void setClick() {

        PushDownAnim.setPushDownAnimTo(linear_1, linear_2, linear_3, linear_4).setScale(MODE_SCALE, 0.89f).setDurationPush(DEFAULT_PUSH_DURATION).setDurationRelease(DEFAULT_RELEASE_DURATION);

        linear_1.setOnClickListener(this);
        linear_2.setOnClickListener(this);
        linear_3.setOnClickListener(this);
        linear_4.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.linear_1) {
            checkAnswer(0);
        } else if (id == R.id.linear_2) {
            checkAnswer(1);
        } else if (id == R.id.linear_3) {
            checkAnswer(2);
        } else if (id == R.id.linear_4) {
            checkAnswer(3);
        }
    }


    public void setNextData() {
        if (position < quizModelList.size() - 1) {
            position++;
            setData(position);
        } else {
            passIntent();
        }
    }

    public void onBackPressed() {
        cancelTimer();
        ConstantDialog.showExitDialog(this, this);
    }

    @Override
    public void onNo() {

    }

    public void backIntent() {
        cancelTimer();
        quizModelList.clear();
        startActivity(new Intent(this, LearnTableActivity.class));
        overridePendingTransition(0, 0);
    }

    public void cancelTimer() {
        if (handler != null) {
            handler.removeCallbacks(r);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelTimer();
    }

    public void passIntent() {
        if (!isDialogOpen) {
            showDoneDialog(this);
        }
    }

    public void showDoneDialog(final Activity activity) {
        isDialogOpen = true;
        setDefaultLanguage(activity);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_learn, null);
        builder.setView(view);
        builder.setCancelable(false);


        TextView btn_home, btn_retry, tv_wrong_count, tv_right_count;
        btn_home = view.findViewById(R.id.btn_home);
        tv_right_count = view.findViewById(R.id.tv_right_count);
        tv_wrong_count = view.findViewById(R.id.tv_wrong_count);
        btn_retry = view.findViewById(R.id.btn_retry);


        tv_right_count.setText(String.valueOf(right_answer_count));
        tv_wrong_count.setText(String.valueOf(wrong_answer_count));


        final AlertDialog setDialog = builder.create();
        setDialog.show();
        Objects.requireNonNull(setDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);


        btn_home.setOnClickListener(view1 -> {
            backIntent();
        });
        btn_retry.setOnClickListener(view1 -> {
            startActivity(new Intent(this, LearnQuizActivity.class));
        });

    }


    public void setScore() {
        tv_wrong_count.setText(getTranslatedString(String.valueOf(wrong_answer_count)));
        tv_right_count.setText(getTranslatedString(String.valueOf(right_answer_count)));
    }

    public void setFalseAction() {
        if (!isCount) {
            isCount = true;
            wrong_answer_count++;
            tv_wrong_count.setText(getTranslatedString(String.valueOf(wrong_answer_count)));
        }
        if (Constant.getVibrate(getApplicationContext())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibe.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibe.vibrate(400);
            }
        }

        imageView.setVisibility(View.GONE);
        textView1.setColor(Color.RED);
        textView1.setTextColor(ContextCompat.getColor(this, R.color.wrong_red_color));
        handler.postDelayed(r, DELAY_SEOCND);
    }


    public void setTrueAction() {
        if (!isCount) {
            isCount = true;
            right_answer_count++;
            tv_right_count.setText(getTranslatedString(String.valueOf(right_answer_count)));

        }
        textView1.setTextColor(ContextCompat.getColor(this, R.color.right_green_color));
        textView1.setColor(Color.TRANSPARENT);
        imageView.setVisibility(View.VISIBLE);
        handler.postDelayed(r, DELAY_SEOCND);
    }

    final Runnable r = this::setNextData;


    public void checkAnswer(int pos) {
        String s = optionViewList.get(pos).string;
        TextView textView = optionViewList.get(pos).textView;
        CardView cardView = optionViewList.get(pos).cardView;
        textView.setTextColor(Color.WHITE);
        cardView.setCardBackgroundColor(Constant.getThemeColor(this, R.attr.colorPrimary));


        if (quizModel != null) {
            if (s.equals((quizModel.answer))) {
                setTrueAction();
            } else {
                setFalseAction();
            }
        }
    }

    @Override
    public void onExit() {
        backIntent();
    }


    public class GetAllData extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(getString(R.string.please_wait));
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            LearnData learnData = new LearnData(LearnQuizActivity.this, learnModel);



            int t_count = 0;
            if (learnModel.table_page != 0) {
                String s = learnModel.table_page + "1";
                t_count = Integer.parseInt(s) - 1;

            }


            t_count--;


            for (int i = 0; i < Constant.DEFAULT_QUESTION_SIZE; i++) {



                t_count++;

                Log.e("t_count", "" + t_count);


                TableModel tableModel = learnData.getTableModel(t_count);
                Log.e("tableModel", "" + tableModel.optionList.size());
                quizModelList.add(tableModel);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            tv_total_count.setText(getTranslatedString(getString(R.string.slash) + quizModelList.size()));
            if (quizModelList.size() > 0) {
                setData(position);
            }
        }
    }


    public String getTranslatedString(String s) {
        return Constant.getAllTranslatedDigit(s);
    }

    public void setOptionView() {
        optionViewList.clear();
        optionViewList.add(new TextModel(btn_op_1, card_1));
        optionViewList.add(new TextModel(btn_op_2, card_2));
        optionViewList.add(new TextModel(btn_op_3, card_3));
        optionViewList.add(new TextModel(btn_op_4, card_4));


        for (int i = 0; i < optionViewList.size(); i++) {
            optionViewList.get(i).cardView.setCardBackgroundColor(Constant.getThemeColor(this, R.attr.theme_cell_color));
            optionViewList.get(i).textView.setTextColor(Constant.getThemeColor(this, R.attr.theme_text_color));
        }
    }

    public void setData(int position) {
        textView1.setColor(Color.TRANSPARENT);
        imageView.setVisibility(View.GONE);
        cancelTimer();
        isCount = false;
        tv_question_count.setText(String.valueOf((position + 1)));
        setOptionView();
        quizModel = quizModelList.get(position);
        textView1.setTextColor(Constant.getThemeColor(this, R.attr.theme_text_color));

        textView1.setText(quizModel.question);
        for (int i = 0; i < optionViewList.size(); i++) {
            TextModel textModel = optionViewList.get(i);
            textModel.textView.setText(quizModel.optionList.get(i));
            optionViewList.get(i).string = quizModel.optionList.get(i);
        }

    }


}