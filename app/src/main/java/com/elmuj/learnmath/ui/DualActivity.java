package com.elmuj.learnmath.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;


import androidx.annotation.Nullable;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.data.RandomDualData;
import com.elmuj.learnmath.model.DualScoreModel;
import com.elmuj.learnmath.model.MainModel;
import com.elmuj.learnmath.model.QuizModel;


import com.elmuj.learnmath.utils.Constant;

import com.elmuj.learnmath.utils.ConstantDialog;
import com.elmuj.learnmath.utils.ExitInterface;
import com.elmuj.learnmath.view.DualView;

import java.util.ArrayList;
import java.util.List;

import static com.elmuj.learnmath.utils.Constant.DELAY_SEOCND;
import static com.elmuj.learnmath.utils.Constant.getSubModel;

public class DualActivity extends BaseActivity implements ExitInterface {

    DualScoreModel dualScoreModel1, dualScoreModel2;
    LinearLayout view_1, view_2;
    ProgressDialog progressDialog;
    TextView tv_question_count, tv_total_count;
    Handler handler = new Handler();
    MainModel mainModel;
    boolean isNext1 = false;
    boolean isNext2 = false;
    List<QuizModel> quizModelList = new ArrayList<>();
    DualView dualView1, dualView2;
    int position;
    QuizModel quizModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual);
        init();
    }

    private void init() {
        dualScoreModel1 = new DualScoreModel();
        dualScoreModel2 = new DualScoreModel();
        mainModel = Constant.getMainModel(this);
        progressDialog = new ProgressDialog(this);
        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);
        tv_total_count = findViewById(R.id.tv_total_count);
        tv_question_count = findViewById(R.id.tv_question_count);

        setView1();
        setView2();

        new GetAllData().execute();
    }

    @Override
    public void onExit() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onNo() {

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

            RandomDualData learnData = new RandomDualData(DualActivity.this, mainModel, getSubModel(DualActivity.this).mode_type);

            for (int i = 0; i < Constant.DEFAULT_QUESTION_SIZE; i++) {
                QuizModel tableModel = learnData.getMethods();
                Log.e("quizModel===", "" + tableModel.optionList.size());
                quizModelList.add(tableModel);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("quizModelList", "" + quizModelList.size());
            progressDialog.dismiss();
            if (quizModelList.size() > 0) {
                setData(position);
            }
        }

    }

    public String getTranslatedString(String s) {
        return Constant.getAllTranslatedDigit(s);
    }


    public void setData(int position) {
        quizModel = quizModelList.get(position);
        tv_total_count.setText(getTranslatedString(getString(R.string.slash) + Constant.DEFAULT_QUESTION_SIZE));
        tv_question_count.setText(getTranslatedString(String.valueOf((position + 1))));


        Log.e("quizModel===", "" + quizModel.answer);
        dualView1.setQuizModel(quizModel);
        dualView2.setQuizModel(quizModel);

    }

    @Override
    public void onBackPressed() {
        ConstantDialog.showExitDialog(this, this);

    }

    public void setView1() {
        dualView1 = new DualView(this, (score, isTrue) -> {
            if (!isNext1) {
                isNext1 = true;
            }


            if (score >= 10) {
                setScoreModel(true);
                passScoreIntent();


            } else {
                if (isNext2 || isTrue) {
                    handler.postDelayed(r, DELAY_SEOCND);
                } else {
                    dualView1.setIsClick(false);
                }
            }


        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dualView1.setLayoutParams(layoutParams);
        view_1.addView(dualView1);

    }


    public void setScoreModel(boolean isD1) {

        DualScoreModel d1 = new DualScoreModel();
        d1.title = getString(R.string.congratulations);
        d1.sub_title = getString(R.string.you_won);

        DualScoreModel d2 = new DualScoreModel();
        d2.title = getString(R.string.try_next_time);
        d2.sub_title = getString(R.string.you_lose);


        if (isD1) {

            d1.score = dualView1.getScore();
            d2.score = dualView2.getScore();

            dualScoreModel1 = d1;
            dualScoreModel2 = d2;


        } else {
            d1.score = dualView2.getScore();
            d2.score = dualView1.getScore();

            dualScoreModel1 = d2;
            dualScoreModel2 = d1;
        }


        Constant.saveDuelModel(this, dualScoreModel1, Constant.DUEL_MODEL1);
        Constant.saveDuelModel(this, dualScoreModel2, Constant.DUEL_MODEL2);
    }

    public void passScoreIntent() {
        Intent intent = new Intent(this, DualScoreActivity.class);
        intent.putExtra(Constant.ISDraw, isDraw);
        startActivity(intent);
    }

    public void setView2() {
        dualView2 = new DualView(this, (score, isTrue) -> {
            if (!isNext2) {
                isNext2 = true;
            }
            if (score >= 10) {
                setScoreModel(false);
                passScoreIntent();
            } else {
                if (isNext1 || isTrue) {
                    handler.postDelayed(r, DELAY_SEOCND);
                } else {
                    dualView2.setIsClick(false);
                }
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dualView2.setLayoutParams(layoutParams);
        view_2.addView(dualView2);

    }

    final Runnable r = this::setNextData;


    boolean isDraw = false;

    public void setNextData() {

        isDraw = false;
        isNext1 = false;
        isNext2 = false;
        dualView1.setIsClick(true);
        dualView2.setIsClick(true);
        if (position < quizModelList.size() - 1) {
            position++;
            setData(position);

        } else {
            if (dualView1.getScore() > dualView2.getScore()) {
                setScoreModel(true);
            } else if (dualView1.getScore() < dualView2.getScore()) {
                setScoreModel(false);
            } else {
                isDraw = true;
            }
            passScoreIntent();
        }
    }


}
