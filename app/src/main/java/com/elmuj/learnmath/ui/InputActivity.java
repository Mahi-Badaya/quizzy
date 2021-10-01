package com.elmuj.learnmath.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;


import com.elmuj.learnmath.R;
import com.elmuj.learnmath.data.RandomOptionData;
import com.elmuj.learnmath.model.HistoryModel;
import com.elmuj.learnmath.model.MainModel;
import com.elmuj.learnmath.model.QuizModel;
import com.elmuj.learnmath.model.SubModel;
import com.elmuj.learnmath.model.TextModel;
import com.elmuj.learnmath.receiver.NotificationScheduler;

import com.elmuj.learnmath.utils.AdVideoInterface;
import com.elmuj.learnmath.utils.CenterLineTextView;
import com.elmuj.learnmath.utils.CenteredToolbar;
import com.elmuj.learnmath.utils.ConnectionDetector;
import com.elmuj.learnmath.utils.Constant;
import com.elmuj.learnmath.utils.ConstantDialog;
import com.elmuj.learnmath.utils.ExitInterface;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.elmuj.learnmath.utils.Constant.DELAY_SEOCND;

import static com.elmuj.learnmath.utils.Constant.getPlusScore;
import static com.elmuj.learnmath.utils.Constant.setDefaultLanguage;


public class InputActivity extends BaseActivity implements View.OnClickListener, AdVideoInterface, ExitInterface {

    TextView tv_precision, tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9, tv_0, tvDecimalPoint, tvRemove, tvDone;
    TextView tv_set, tv_score, tv_plus_score, tv_right_count, tv_wrong_count, tv_timer,
            tv_coin, tv_question_count, tv_total_count, textView2;
    List<QuizModel> quizModelList = new ArrayList<>();
    ProgressDialog progressDialog;
    CenterLineTextView textView1, tv_factorial;
    Vibrator vibe;
    boolean isTimer, isCount;
    List<TextModel> optionViewList = new ArrayList<>();
    QuizModel quizModel;
    int history_id, helpLineCount, position, countTime, score,
            plusScore, wrong_answer_count, coin, right_answer_count;
    LinearLayout helpLineView;
    Intent intent;
    EditText editText;
    List<HistoryModel> historyModels = new ArrayList<>();
    Handler handler = new Handler();
    ProgressBar progress_bar;
    boolean isHint = true;
    RelativeLayout layout_cell;
    String historyQuestion, historyAnswer, historyUserAnswer;
    CenteredToolbar toolbar;
    CountDownTimer countDownTimer;
    ImageView btn_hint;
    boolean isClick = true;
    boolean isVideoComplete = false;
    RewardedVideoAd rewardedVideoAd;
    boolean interstitialCanceled;
    ConnectionDetector cd;
    InterstitialAd mInterstitialAd;
    MainModel mainModel;
    SubModel subModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_quiz);
        init();
    }

    public void startTimer(final int count) {
        countDownTimer = new CountDownTimer(count * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isTimer = true;
                countTime = (int) millisUntilFinished / 1000;
                tv_timer.setText(getTranslatedString(String.valueOf((millisUntilFinished / 1000))));
                progress_bar.setProgress(countTime);
                plusScore = getPlusScore(countTime);
                tv_plus_score.setText(getTranslatedString(getString(R.string.addition_sign) + plusScore));
            }

            @Override
            public void onFinish() {
                isTimer = false;
                helpLineCount++;
                setHelpLineView();
                if (helpLineCount > 3) {
                    if (!isVideoComplete) {
                        cancelTimer();
                        ConstantDialog.showVideoDialogs(InputActivity.this, InputActivity.this);
                    } else {
                        passIntent();
                    }

                } else {
                    handler.postDelayed(r, DELAY_SEOCND);
                }


            }
        }.start();
    }


    public void setCoins() {
        coin = Constant.getCoins(getApplicationContext());
        tv_coin.setText(getTranslatedString(String.valueOf(coin)));
    }

    public void addCoins() {
        Constant.setCoins(getApplicationContext(), (coin + 2));
    }


    private void init() {
        vibe = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        subModel = Constant.getSubModel(this);
        mainModel = Constant.getMainModel(this);

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


        btn_hint = findViewById(R.id.btn_hint);
        tv_question_count = findViewById(R.id.tvQuestionCount);
        tv_right_count = findViewById(R.id.tv_right_count);
        tv_score = findViewById(R.id.tv_score);
        tv_plus_score = findViewById(R.id.tv_plus_score);
        tv_wrong_count = findViewById(R.id.tv_wrong_count);
        tv_set = findViewById(R.id.tv_set);
        layout_cell = findViewById(R.id.layout_cell);
        tv_coin = findViewById(R.id.tv_coin);
        helpLineView = findViewById(R.id.helpLineView);
        tv_total_count = findViewById(R.id.tv_total_count);
        tv_precision = findViewById(R.id.tv_precision);
        textView1 = findViewById(R.id.textView1);
        tv_factorial = findViewById(R.id.tv_factorial);
        textView2 = findViewById(R.id.textView2);


        progress_bar = findViewById(R.id.progressBar);
        tv_timer = findViewById(R.id.tvTimer);

        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        tv_3 = findViewById(R.id.tv_3);
        tv_4 = findViewById(R.id.tv_4);
        tv_5 = findViewById(R.id.tv_5);
        tv_6 = findViewById(R.id.tv_6);
        tv_7 = findViewById(R.id.tv_7);
        tv_8 = findViewById(R.id.tv_8);
        tv_9 = findViewById(R.id.tv_9);
        tv_0 = findViewById(R.id.tv_0);
        tvDecimalPoint = findViewById(R.id.tvDecimalPoint);
        tvDone = findViewById(R.id.tvDone);
        tvRemove = findViewById(R.id.tvRemove);
        editText = findViewById(R.id.edtAns);


        progress_bar.setMax(Constant.TIMER);

        tv_set.setText(getTranslatedString(getString(R.string.level) + ": " + subModel.level_no));


        getSupportActionBar().setTitle(mainModel.title);

        setCoins();
        quizModelList.clear();
        setClick();
        setHelpLineView();
        setScore();


        new GetAllData().execute();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        startTimer(countTime);
    }

    public void setHelpLineView() {
        helpLineView.removeAllViews();

        Log.e("helpLineCount", "" + helpLineCount);
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(3, 3, 3, 3);
            imageView.setLayoutParams(layoutParams);
            if (helpLineCount > i) {
                imageView.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
            } else {
                imageView.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
            }
            helpLineView.addView(imageView);
        }
    }


    private void setClick() {
        editText.setShowSoftInputOnFocus(false);
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
        tv_5.setOnClickListener(this);
        tv_6.setOnClickListener(this);
        tv_7.setOnClickListener(this);
        tv_8.setOnClickListener(this);
        tv_9.setOnClickListener(this);
        tv_0.setOnClickListener(this);
        tvDecimalPoint.setOnClickListener(this);


        tvDone.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                checkAnswer(editText.getText().toString());
            }
        });
        btn_hint.setOnClickListener(v -> {
            if (isHint){
                isHint = false;
                showHintDialogs();

                btn_hint.setAlpha(0.5f);
            }else {
                Toast.makeText(this, getString(R.string.life_line_toast), Toast.LENGTH_SHORT).show();
            }

        });
        tv_precision.setOnClickListener(v -> {
            editText.append("-");

//            if (!TextUtils.isEmpty(editText.getText().toString())) {
//                if (editText.getText().toString().contains("-")) {
//                    editText.setText(editText.getText().toString().replaceAll("-", ""));
//                } else {
//                    editText.setText(getTranslatedString("-" + editText.getText().toString()));
//                }
//            }
        });

        tvRemove.setOnClickListener(v -> {
            int cursorPosition = editText.getSelectionStart();
            cursorPosition = cursorPosition - 1;
            String string = editText.getText().toString();

            if (cursorPosition >= 0) {

                if (!TextUtils.isEmpty(editText.getText().toString())) {

                    StringBuilder sb = new StringBuilder(string);
                    sb.deleteCharAt(cursorPosition);

                    editText.setText(sb.toString());
                    editText.setSelection(cursorPosition);
                }
            }
        });


    }


    @Override
    public void onClick(View v) {
        editText.append(((TextView) v).getText());
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
        startTimer(countTime);
    }

    public void backIntent() {
        cancelTimer();
        quizModelList.clear();
        intent = new Intent(this, LevelActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void cancelTimer() {
        if (isTimer) {
            countDownTimer.cancel();
        }
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
        NotificationScheduler.showNotification(getApplicationContext(), subModel.level_no);
        quizModelList.clear();
        subModel.right_count = right_answer_count;
        subModel.wrong_count = wrong_answer_count;
        subModel.score = score;
        Constant.saveSubModel(this, subModel);
        Constant.addModel(mainModel.title, mainModel.tableName, subModel.TYPE_CODE, getApplicationContext(), historyModels);
        intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }


    public void setScore() {
        tv_score.setText(getTranslatedString(String.valueOf(score)));
        tv_wrong_count.setText(getTranslatedString(String.valueOf(wrong_answer_count)));
        tv_right_count.setText(getTranslatedString(String.valueOf(right_answer_count)));
    }

    public void setFalseAction() {
        if (Constant.getVibrate(getApplicationContext())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibe.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibe.vibrate(400);
            }
        }
        if (!isCount) {
            isCount = true;
            wrong_answer_count++;
            tv_wrong_count.setText(getTranslatedString(String.valueOf(wrong_answer_count)));
            if ((score - 250) > 0) {
                score = score - 250;
            }
            setScore();
        }
        helpLineCount++;
        setHelpLineView();
        textView1.setColor(Color.RED);
        tv_factorial.setColor(Color.RED);

        editText.setTextColor(ContextCompat.getColor(this, R.color.red));
        textView1.setTextColor(ContextCompat.getColor(this, R.color.red));
        textView2.setTextColor(ContextCompat.getColor(this, R.color.red));
        tv_factorial.setTextColor(ContextCompat.getColor(this, R.color.red));

        if (helpLineCount > 3) {
            if (!isVideoComplete) {
                cancelTimer();
                ConstantDialog.showVideoDialogs(InputActivity.this, InputActivity.this);
            } else {
                passIntent();
            }
        } else {
            handler.postDelayed(r, DELAY_SEOCND);
        }


    }


    public void showHintDialogs() {
        cancelTimer();
        setDefaultLanguage(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_hint, null);
        builder.setView(view);
        builder.setCancelable(false);
        TextView textView = view.findViewById(R.id.textView);
        TextView btn_ok = view.findViewById(R.id.btn_ok);
        final AlertDialog dialog = builder.create();
        dialog.show();

        textView.setText(getString(R.string.answer_is) + " " + quizModel.answer);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        btn_ok.setOnClickListener(v -> {
            dialog.dismiss();

            startTimer(countTime);

        });


    }


    public void videoShow() {


        rewardedVideoAd.show();


        rewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                passIntent();
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

                rewardedVideoAd.destroy(getApplicationContext());
                ConstantDialog.showGetLivesDialogs(InputActivity.this, InputActivity.this);
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }

            @Override
            public void onRewardedVideoCompleted() {
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        cd = new ConnectionDetector(this);
        interstitialCanceled = false;
        if (getResources().getString(R.string.ADS_VISIBILITY).equals("YES")) {
            CallNewInsertial();
        }
        setAddViews();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void CallNewInsertial() {
        cd = new ConnectionDetector(this);
        if (cd.isConnectingToInternet()) {
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_id));
            requestNewInterstitial();
        }
    }


    private void loadRewardedVideoAd() {
        rewardedVideoAd.loadAd(getString(R.string.video_reward_ads),
                new AdRequest.Builder().build());
    }

    public void setAddViews() {
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        loadRewardedVideoAd();
    }


    public void setTrueAction() {
        if (!isCount) {
            isCount = true;
            right_answer_count++;
            addCoins();
            setCoins();
            score = score + plusScore;
            tv_right_count.setText(getTranslatedString(String.valueOf(right_answer_count)));
            setScore();
        }

        textView1.setColor(Color.TRANSPARENT);
        tv_factorial.setColor(Color.TRANSPARENT);

        editText.setTextColor(ContextCompat.getColor(this, R.color.greenColorPrimary));
        textView1.setTextColor(ContextCompat.getColor(this, R.color.greenColorPrimary));
        textView2.setTextColor(ContextCompat.getColor(this, R.color.greenColorPrimary));
        tv_factorial.setTextColor(ContextCompat.getColor(this, R.color.greenColorPrimary));
        handler.postDelayed(r, DELAY_SEOCND);
    }

    final Runnable r = this::setNextData;


    public void checkAnswer(String s) {

        if (isClick) {
            isClick = false;
            if (quizModel != null) {
                if (!isCount) {
                    historyUserAnswer = s;
                    history_id++;
                    historyModels.add(new HistoryModel(history_id, historyQuestion, historyAnswer, historyUserAnswer));
                }


                DecimalFormat format = new DecimalFormat();
                format.setDecimalSeparatorAlwaysShown(false);

                Log.e("quizModel13==", "" + format.format(Double.parseDouble(s)) + "===" + format.format(Double.parseDouble(quizModel.answer)) +
                        "=[=" + "===" + quizModel.answer);
                if (format.format(Double.parseDouble(s)).equals((format.format(Double.parseDouble(quizModel.answer)))) || s.equals(quizModel.answer)) {
                    setTrueAction();
                } else {
                    setFalseAction();
                }
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

            RandomOptionData learnData = new RandomOptionData(InputActivity.this, mainModel, subModel.level_no);

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
            tv_total_count.setText(getTranslatedString(getString(R.string.slash) + quizModelList.size()));
            if (quizModelList.size() > 0) {
                setData(position);
            }
        }

    }


    public String getTranslatedString(String s) {
        return Constant.getAllTranslatedDigit(s);
    }


    public void setData(int position) {
        isClick = true;
        editText.setText(null);
        textView1.setColor(Color.TRANSPARENT);
        tv_factorial.setColor(Color.TRANSPARENT);

        editText.setTextColor(Constant.getThemeColor(this, R.attr.theme_text_color));
        textView1.setTextColor(Constant.getThemeColor(this, R.attr.theme_text_color));
        textView2.setTextColor(Constant.getThemeColor(this, R.attr.theme_text_color));
        tv_factorial.setTextColor(Constant.getThemeColor(this, R.attr.theme_text_color));
        cancelTimer();
        plusScore = 500;
        countTime = Constant.TIMER;
        startTimer(countTime);
        isCount = false;

        quizModel = quizModelList.get(position);
        tv_question_count.setText(getTranslatedString(String.valueOf((position + 1))));


        if (!TextUtils.isEmpty(quizModel.question)) {

            textView2.setVisibility(View.GONE);

            historyQuestion = quizModel.question + " = ?";
            if (mainModel.tableName.equals(getString(R.string.factorial_table))) {
                tv_factorial.setVisibility(View.VISIBLE);
                tv_factorial.setText(historyQuestion);
                textView1.setVisibility(View.GONE);
            }
            textView1.setText(getTranslatedString(String.valueOf(historyQuestion)));


        } else {
            textView1.setText(getTranslatedString(String.valueOf(quizModel.firstDigit)));
            textView2.setText(getTranslatedString(mainModel.sign + getString(R.string.single_space) + quizModel.secondDigit));
            historyQuestion = textView1.getText().toString() + " " + textView2.getText().toString() + " = ?";

            textView2.setVisibility(View.GONE);
            textView1.setText(getTranslatedString(historyQuestion));


        }
        historyAnswer = quizModel.answer;

        int textSize;
        if (quizModel.answer.length() >= 5) {
            textSize = 20;
        } else {
            textSize = 30;
        }


        for (int i = 0; i < optionViewList.size(); i++) {
            TextModel textModel = optionViewList.get(i);
            String s = quizModel.optionList.get(i);
            textModel.textView.setTextSize(textSize);

            textModel.textView.setText(s);
            optionViewList.get(i).string = s;

        }

        Log.e("quizModel12==", "" + quizModel.answer);


    }

    @Override
    public void showVideoClick(Dialog dialog) {
        if (rewardedVideoAd.isLoaded()) {
            videoShow();
            dialog.dismiss();
        } else {
            if (rewardedVideoAd.isLoaded()) {
                videoShow();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "" + getString(R.string.str_video_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void getLivesClick() {
        isVideoComplete = true;
        helpLineCount = helpLineCount - 2;
        setHelpLineView();
        setNextData();
    }

    @Override
    public void cancelClick() {
        passIntent();
    }


}
