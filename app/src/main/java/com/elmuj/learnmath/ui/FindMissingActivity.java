package com.elmuj.learnmath.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.data.RandomFindMissingData;
import com.elmuj.learnmath.model.HistoryModel;
import com.elmuj.learnmath.model.MainModel;
import com.elmuj.learnmath.model.QuizModel;
import com.elmuj.learnmath.model.SubModel;
import com.elmuj.learnmath.model.TextModel;
import com.elmuj.learnmath.receiver.NotificationScheduler;
import com.elmuj.learnmath.utils.AdVideoInterface;
import com.elmuj.learnmath.utils.AnimatorUtils;
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
import com.ogaclejapan.arclayout.ArcLayout;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.elmuj.learnmath.utils.Constant.DELAY_SEOCND;
import static com.elmuj.learnmath.utils.Constant.getPlusScore;
import static com.thekhaeng.pushdownanim.PushDownAnim.DEFAULT_PUSH_DURATION;
import static com.thekhaeng.pushdownanim.PushDownAnim.DEFAULT_RELEASE_DURATION;
import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;


public class FindMissingActivity extends BaseActivity implements View.OnClickListener, AdVideoInterface, ExitInterface {

    CardView card_1, card_2, card_3, card_4;
    ImageView btn_help_line;
    CenterLineTextView textView1;
    TextView audience_op_1, audience_op_2, audience_op_3, audience_op_4, tv_set, tv_score, tv_plus_score, tv_right_count, tv_wrong_count, tv_timer,
            tv_coin, tv_question_count, tv_total_count, btn_op_1, btn_op_2, btn_op_3, btn_op_4;
    List<QuizModel> quizModelList = new ArrayList<>();
    ProgressDialog progressDialog;
    ImageView btn_fifty, btn_timer, btn_audiance;
    boolean isTimer, isCount;
    Vibrator vibe;
    boolean isHelpLine;
    List<TextModel> optionViewList = new ArrayList<>();
    List<Integer> integerArrayList = new ArrayList<>();
    QuizModel quizModel;
    View menuLayout;
    int history_id, helpLineCount, position, countTime, score,
            plusScore, wrong_answer_count, coin, right_answer_count;
    LinearLayout helpLineView;
    Intent intent;
    List<HistoryModel> historyModels = new ArrayList<>();
    ArcLayout arcLayout;
    Handler handler = new Handler();
    ProgressBar progress_bar;
    RelativeLayout layout_cell;
    String historyQuestion, historyAnswer, historyUserAnswer;
    CenteredToolbar toolbar;
    CountDownTimer countDownTimer;
    MediaPlayer answerPlayer;
    boolean isVideoComplete = false;
    RewardedVideoAd rewardedVideoAd;
    boolean interstitialCanceled;
    ConnectionDetector cd;
    boolean isClick = true;
    InterstitialAd mInterstitialAd;
    MainModel mainModel;
    SubModel subModel;
    LinearLayout linear_1, linear_2, linear_3, linear_4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_missing);
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
                        ConstantDialog.showVideoDialogs(FindMissingActivity.this, FindMissingActivity.this);
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

        linear_1 = findViewById(R.id.linear_1);
        linear_2 = findViewById(R.id.linear_2);
        linear_3 = findViewById(R.id.linear_3);
        linear_4 = findViewById(R.id.linear_4);
        tv_question_count = findViewById(R.id.tv_question_count);
        tv_right_count = findViewById(R.id.tv_right_count);
        tv_score = findViewById(R.id.tv_score);
        tv_plus_score = findViewById(R.id.tv_plus_score);
        tv_wrong_count = findViewById(R.id.tv_wrong_count);
        tv_set = findViewById(R.id.tv_set);
        layout_cell = findViewById(R.id.layout_cell);
        tv_coin = findViewById(R.id.tv_coin);
        helpLineView = findViewById(R.id.helpLineView);
        tv_total_count = findViewById(R.id.tv_total_count);
        textView1 = findViewById(R.id.textView1);

        card_1 = findViewById(R.id.card_1);
        card_2 = findViewById(R.id.card_2);
        btn_help_line = findViewById(R.id.btn_help_line);
        card_3 = findViewById(R.id.card_3);
        card_4 = findViewById(R.id.card_4);
        btn_op_1 = findViewById(R.id.btn_op_1);
        arcLayout = findViewById(R.id.arc_layout);
        btn_op_2 = findViewById(R.id.btn_op_2);
        btn_op_3 = findViewById(R.id.btn_op_3);
        btn_op_4 = findViewById(R.id.btn_op_4);
        progress_bar = findViewById(R.id.progress_bar);
        tv_timer = findViewById(R.id.tv_timer);
        menuLayout = findViewById(R.id.menu_layout);
        btn_fifty = findViewById(R.id.btn_fifty);
        btn_timer = findViewById(R.id.btn_timer);
        btn_audiance = findViewById(R.id.btn_audiance);
        audience_op_1 = findViewById(R.id.audience_op_1);
        audience_op_2 = findViewById(R.id.audience_op_2);
        audience_op_3 = findViewById(R.id.audience_op_3);
        audience_op_4 = findViewById(R.id.audience_op_4);


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
        PushDownAnim.setPushDownAnimTo(linear_1, linear_2, linear_3, linear_4).setScale(MODE_SCALE, 0.89f).setDurationPush(DEFAULT_PUSH_DURATION).setDurationRelease(DEFAULT_RELEASE_DURATION);

        linear_1.setOnClickListener(this);
        linear_2.setOnClickListener(this);
        linear_3.setOnClickListener(this);
        linear_4.setOnClickListener(this);

        btn_help_line.setOnClickListener(v -> {
            if (isHelpLine) {
                onFabClick(v);
            }
        });


        btn_fifty.setOnClickListener(v -> {
            if (isHelpLine && btn_fifty.getAlpha() != 0.5) {
                isHelpLine = false;
                btn_fifty.setAlpha(0.5f);
                helpLineMethod();
                hideMenu();
                setHelpLineButton();

            }
        });

        btn_audiance.setOnClickListener(v -> {
            if (isHelpLine && btn_audiance.getAlpha() != 0.5) {
                isHelpLine = false;
                btn_audiance.setAlpha(0.5f);
                setPercentage();
                hideMenu();
                setHelpLineButton();
            }
        });

        btn_timer.setOnClickListener(v -> {
            if (isHelpLine && btn_timer.getAlpha() != 0.5) {
                isHelpLine = false;
                btn_timer.setAlpha(0.5f);
                setTimerAnswer();
                setHelpLineButton();
                hideMenu();
            }
        });
    }


    public void setTimerAnswer() {
        String checkAnswer;
        checkAnswer = quizModel.answer;
        for (int i = 0; i < optionViewList.size(); i++) {
            optionViewList.get(i).cardView.setVisibility(View.GONE);
        }

        int answerNumber = 0;
        for (int i = 0; i < optionViewList.size(); i++) {
            if (optionViewList.get(i).textView.getText().toString().equals(checkAnswer)) {
                answerNumber = i;
            }
        }

        optionViewList.get(answerNumber).cardView.setVisibility(View.VISIBLE);

    }


    public void setPercentage() {
        String checkAnswer;


        final int random1 = new Random().nextInt((100 - 70) + 1) + 70;
        final int random2 = new Random().nextInt((70 - 45) + 1) + 45;
        final int random3 = new Random().nextInt((45 - 30) + 1) + 30;
        final int random4 = new Random().nextInt((30 - 10) + 1) + 10;


        checkAnswer = quizModel.answer;
        int answerPosition = 0;

        for (int i = 0; i < optionViewList.size(); i++) {
            optionViewList.get(i).audienceView.setVisibility(View.VISIBLE);

            if (checkAnswer.equals(optionViewList.get(i).textView.getText().toString())) {
                answerPosition = i;
                break;
            }
        }
        integerArrayList.clear();
        optionViewList.get(answerPosition).audienceView.setText(getTranslatedString(random1 + " %"));

        integerArrayList.add(random2);
        integerArrayList.add(random3);
        integerArrayList.add(random4);

        Collections.shuffle(integerArrayList);
        int count = -1;


        for (int i = 0; i < optionViewList.size(); i++) {
            optionViewList.get(i).audienceView.setVisibility(View.VISIBLE);
            if (i != answerPosition) {
                count++;
                Log.e("answerPosition==", "" + answerPosition + "==" + i);
                optionViewList.get(i).audienceView.setText(getTranslatedString(integerArrayList.get(count) + " %"));
            }
        }

    }


    public void helpLineMethod() {

        Random random_number = new Random();
        String checkAnswer;

        checkAnswer = String.valueOf(quizModel.answer);

        int answerNumber = 0;
        for (int i = 0; i < optionViewList.size(); i++) {
            if (optionViewList.get(i).textView.getText().toString().equals(checkAnswer)) {
                answerNumber = i;
            }
        }
        int helpTag = random_number.nextInt(3) + 1;

        if (helpTag == answerNumber) {
            helpTag = random_number.nextInt(3) + 1;
        }

        if (helpTag == answerNumber) {
            helpTag = random_number.nextInt(3) + 1;
        }

        for (int i = 0; i < optionViewList.size(); i++) {
            optionViewList.get(i).cardView.setVisibility(View.GONE);

        }
        optionViewList.get(helpTag).cardView.setVisibility(View.VISIBLE);
        optionViewList.get(answerNumber).cardView.setVisibility(View.VISIBLE);


    }


    private void onFabClick(View v) {
        if (menuLayout.getVisibility() == View.INVISIBLE) {
            showMenu();
        } else {
            hideMenu();
        }
    }

    private void hideMenu() {

        List<Animator> animList = new ArrayList<>();

        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menuLayout.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();
    }


    private Animator createHideItemAnimator(final View item) {
        float dx = btn_help_line.getX() - item.getX();
        float dy = btn_help_line.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }

    private void showMenu() {
        menuLayout.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    private Animator createShowItemAnimator(View item) {

        float dx = btn_help_line.getX() - item.getX();
        float dy = btn_help_line.getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        return ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );
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

        if (answerPlayer != null) {
            answerPlayer.release();
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

    public void setFalseAction(CardView textView) {
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
        textView.setCardBackgroundColor(Constant.getThemeColor(this, R.attr.colorPrimary));
//        textView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.wrong_red_color));
        textView1.setTextColor(ContextCompat.getColor(this, R.color.wrong_red_color));
        if (helpLineCount > 3) {
            if (!isVideoComplete) {
                cancelTimer();
                ConstantDialog.showVideoDialogs(FindMissingActivity.this, FindMissingActivity.this);
            } else {
                passIntent();
            }
        } else {
            handler.postDelayed(r, DELAY_SEOCND);
        }


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
                ConstantDialog.showGetLivesDialogs(FindMissingActivity.this, FindMissingActivity.this);
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


    public void setTrueAction(CardView textView) {
        if (!isCount) {
            isCount = true;
            right_answer_count++;
            addCoins();
            setCoins();
            score = score + plusScore;
            tv_right_count.setText(getTranslatedString(String.valueOf(right_answer_count)));
            setScore();
        }
//        textView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.right_green_color));
        textView.setCardBackgroundColor(Constant.getThemeColor(this, R.attr.colorPrimary));
        textView1.setColor(Color.TRANSPARENT);
        textView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.right_green_color));
        handler.postDelayed(r, DELAY_SEOCND);
    }

    final Runnable r = this::setNextData;


    public void checkAnswer(int pos) {
        if (isClick) {
            isClick = false;

            CardView cardView = optionViewList.get(pos).cardView;
            TextView textView = optionViewList.get(pos).textView;
            String s = optionViewList.get(pos).string;


            if (quizModel != null) {


                if (!isCount) {
                    historyUserAnswer = s;
                    history_id++;
                    historyModels.add(new HistoryModel(history_id, historyQuestion, historyAnswer, historyUserAnswer));
                }


                Log.e("checkAnswer==", "" + s + "==" + quizModel.answer);

                textView.setTextColor(Color.WHITE);
                if (s.equals((quizModel.answer))) {
                    setTrueAction(cardView);
                } else {
                    setFalseAction(cardView);
                }
            }
        }
    }

    @Override
    public void onExit() {
        backIntent();
    }

    @Override
    public void onNo() {
        startTimer(countTime);
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

            RandomFindMissingData learnData = new RandomFindMissingData(FindMissingActivity.this, mainModel, subModel.level_no);

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

    public void setOptionView() {
        optionViewList.clear();
        optionViewList.add(new TextModel(btn_op_1, card_1, audience_op_1));
        optionViewList.add(new TextModel(btn_op_2, card_2, audience_op_2));
        optionViewList.add(new TextModel(btn_op_3, card_3, audience_op_3));
        optionViewList.add(new TextModel(btn_op_4, card_4, audience_op_4));


        for (int i = 0; i < optionViewList.size(); i++) {
            optionViewList.get(i).cardView.setVisibility(View.VISIBLE);
            optionViewList.get(i).cardView.setCardBackgroundColor(Constant.getThemeColor(this, R.attr.theme_cell_color));
            optionViewList.get(i).textView.setTextColor(Constant.getThemeColor(this, R.attr.theme_text_color));
            optionViewList.get(i).audienceView.setTextColor(Constant.getThemeColor(this, R.attr.theme_text_color));
            optionViewList.get(i).audienceView.setVisibility(View.GONE);
        }
    }


    public void setHelpLineButton() {

//        if (btn_fifty.getAlpha() == 0.5 && btn_timer.getAlpha() == 0.5 && btn_audiance.getAlpha() == 0.5) {
//            btn_help_line.setAlpha(0.5f);
//        } else {
//            Toast.makeText(this, "cghfghj", Toast.LENGTH_SHORT).show();
//            btn_help_line.setAlpha(255);
//        }
    }

    public void setData(int position) {
        isClick = true;
        cancelTimer();
        plusScore = 500;
        countTime = Constant.TIMER;
        startTimer(countTime);
        isCount = false;
        isHelpLine = true;

        setHelpLineButton();

        setOptionView();
        quizModel = quizModelList.get(position);
        textView1.setColor(Color.TRANSPARENT);
        tv_question_count.setText(getTranslatedString(String.valueOf((position + 1))));

        textView1.setTextColor(Constant.getThemeColor(this, R.attr.theme_text_color));
        if (!TextUtils.isEmpty(quizModel.question)) {
            textView1.setText(getTranslatedString(String.valueOf(quizModel.question)));
            historyQuestion = textView1.getText().toString();
        }
        historyAnswer = quizModel.answer;


        Log.e("quizModel", "" + quizModel.answer);
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
            }        }
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
