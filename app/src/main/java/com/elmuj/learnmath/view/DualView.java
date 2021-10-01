package com.elmuj.learnmath.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.AttributeSet;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.model.QuizModel;
import com.elmuj.learnmath.model.TextModel;
import com.elmuj.learnmath.utils.Constant;
import com.elmuj.learnmath.utils.DualInterface;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.DEFAULT_PUSH_DURATION;
import static com.thekhaeng.pushdownanim.PushDownAnim.DEFAULT_RELEASE_DURATION;
import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;


public class DualView extends FrameLayout implements View.OnClickListener {

    CardView card_1, card_2, card_3, card_4;
    TextView tv_score, tv_timer, tv_right_count, tv_wrong_count, text_question, btn_op_1, btn_op_2, btn_op_3, btn_op_4;
    ProgressBar progress_bar;
    QuizModel quizModel;
    boolean isCount = false;
    Activity activity;
    int wrong_answer_count, score, right_answer_count;
    List<TextModel> optionViewList = new ArrayList<>();
    Vibrator vibe;
    boolean isClick = true;
    DualInterface dualInterface;
    LinearLayout linear_1,linear_2,linear_3,linear_4;


    public DualView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public void setIsClick(boolean isClick){
        this.isClick = isClick;
    }

    public DualView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DualView(Activity activity, DualInterface dualInterface) {
        super(activity);
        this.activity = activity;
        this.dualInterface = dualInterface;
        initView();
    }

    public void setQuizModel(QuizModel quizModel) {
        this.quizModel = quizModel;
        vibe = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        setData();
        invalidate();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        final View view = inflate(getContext(), R.layout.dual_part, null);

        tv_score = view.findViewById(R.id.tv_score);
        progress_bar = view.findViewById(R.id.progress_bar);

        tv_timer = view.findViewById(R.id.tv_timer);
        tv_right_count = view.findViewById(R.id.tv_right_count);
        text_question = view.findViewById(R.id.text_question);
        tv_wrong_count = view.findViewById(R.id.tv_wrong_count);

        card_1 = view.findViewById(R.id.card_1);
        card_2 = view.findViewById(R.id.card_2);
        card_3 = view.findViewById(R.id.card_3);
        card_4 = view.findViewById(R.id.card_4);
        btn_op_1 = view.findViewById(R.id.btn_op_1);
        btn_op_2 = view.findViewById(R.id.btn_op_2);
        btn_op_3 = view.findViewById(R.id.btn_op_3);
        btn_op_4 = view.findViewById(R.id.btn_op_4);
        linear_1 = view.findViewById(R.id.linear_1);
        linear_2 = view.findViewById(R.id.linear_2);
        linear_3 = view.findViewById(R.id.linear_3);
        linear_4 = view.findViewById(R.id.linear_4);

        PushDownAnim.setPushDownAnimTo(linear_1,linear_2,linear_3,linear_4).setScale(MODE_SCALE, 0.89f).setDurationPush(DEFAULT_PUSH_DURATION).setDurationRelease(DEFAULT_RELEASE_DURATION);


        linear_1.setOnClickListener(this);
        linear_2.setOnClickListener(this);
        linear_3.setOnClickListener(this);
        linear_4.setOnClickListener(this);
        
        
        setScore();
        addView(view);
    }


    public void setOptionView() {
        optionViewList.clear();
        optionViewList.add(new TextModel(btn_op_1, card_1));
        optionViewList.add(new TextModel(btn_op_2, card_2));
        optionViewList.add(new TextModel(btn_op_3, card_3));
        optionViewList.add(new TextModel(btn_op_4, card_4));


        for (int i = 0; i < optionViewList.size(); i++) {
            optionViewList.get(i).cardView.setVisibility(View.VISIBLE);
            optionViewList.get(i).cardView.setCardBackgroundColor(Constant.getThemeColor(activity, R.attr.theme_cell_color));
            optionViewList.get(i).textView.setTextColor(Constant.getThemeColor(activity, R.attr.theme_text_color));
        }
    }


    public int getScore(){
        return score;
    }


    public String getTranslatedString(String s) {
        return Constant.getAllTranslatedDigit(s);
    }

    public void setData() {
        isCount = false;
        setOptionView();

        if (!TextUtils.isEmpty(quizModel.question)) {
            text_question.setText(quizModel.question);

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

    public void setScore() {
        tv_score.setText(getTranslatedString(String.valueOf(score)));

        tv_wrong_count.setText(getTranslatedString(String.valueOf(wrong_answer_count)));
        tv_right_count.setText(getTranslatedString(String.valueOf(score)));

    }

    public void checkAnswer(int pos) {
        Log.e("checkAnswer==", "" + isClick);
        if (isClick) {

            CardView cardView = optionViewList.get(pos).cardView;
            TextView textView = optionViewList.get(pos).textView;
            String s = optionViewList.get(pos).string;


            if (quizModel != null) {

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

    public void setTrueAction(CardView textView) {
        if (!isCount) {
            isCount = true;
            right_answer_count++;
            score = score + 1;
            tv_right_count.setText(getTranslatedString(String.valueOf(right_answer_count)));
            setScore();
        }

        textView.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.right_green_color));
        if (dualInterface != null) {
            dualInterface.onNextData(score,true);
        }

    }

    public void setFalseAction(CardView textView) {
         if (Constant.getVibrate(getContext())) {
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


        textView.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.wrong_red_color));


        if (dualInterface != null) {
            dualInterface.onNextData(score,false);
        }


    }


}
