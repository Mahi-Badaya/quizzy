package com.elmuj.learnmath.data;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.model.MainModel;
import com.elmuj.learnmath.model.QuizModel;
import com.elmuj.learnmath.utils.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class RandomDualData {

    private int firstDigit;
    private int secondDigit;
    private int answer;
    private double doubleAnswer;
    private Context context;
    private String question, tableName;
    MainModel mainModel;
    String multiplication_sign, equal, space, addition_sign, subtraction_sign, division_sign;
    public int dataTypeNumber;

    public RandomDualData(Context context, MainModel mainModel, int dataTypeNumber) {
        this.context = context;
        this.mainModel = mainModel;
        this.tableName = mainModel.tableName;
        this.dataTypeNumber = dataTypeNumber;


        space = context.getString(R.string.space);
        equal = context.getString(R.string.sign_equal1);
        space = context.getString(R.string.space);
        addition_sign = context.getString(R.string.addition_sign);
        addition_sign = context.getString(R.string.addition_sign);
        multiplication_sign = context.getString(R.string.multiplication_sign);
        division_sign = context.getString(R.string.division_sign);
        subtraction_sign = context.getString(R.string.subtraction_sign);
    }

    public boolean isTrueFalseQuiz = false;


    public QuizModel getMethods() {

        if (tableName.equals(context.getString(R.string.addition_table))) {
            return getAddition();
        } else if (tableName.equals(context.getString(R.string.subtraction_table))) {
            return getSubtraction();
        } else if (tableName.equals(context.getString(R.string.multiplication_table))) {
            return getMultiplication();
        } else if (tableName.equals(context.getString(R.string.division_table))) {
            return getDivision();
        } else if (tableName.equals(context.getString(R.string.square_table))) {
            return getSquareData();
        } else if (tableName.equals(context.getString(R.string.square_root_table))) {
            return getSquareRootData();
        } else if (tableName.equals(context.getString(R.string.cube_table))) {
            return getCubeData();
        } else if (tableName.equals(context.getString(R.string.cube_root_table))) {
            return getCubeRootData();
        } else if (tableName.equals(context.getString(R.string.factorial_table))) {
            return getFactorialData();
        } else if (tableName.equals(context.getString(R.string.mixed_table))) {
            return getMixedData();
        } else if (tableName.equals(context.getString(R.string.addition_subtraction_table))) {
            return getAdditionSubtraction();
        } else if (tableName.equals(context.getString(R.string.complicated_multiplication_table))) {
            return getComplicatedMultiplication();
        } else if (tableName.equals(context.getString(R.string.complicated_division_table))) {
            return getComplicatedDivision();
        } else {
            return getAddition();
        }

    }


    public int getFirstMinNumber() {
        int number = 30;
        if (dataTypeNumber == 2) {
            number = 15;
        } else if (dataTypeNumber == 3) {
            number = 150;
        }
        return number;
    }

    public int getSecondMinNumber() {
        int number = 15;

        if (dataTypeNumber == 2) {
            number = 150;
        } else if (dataTypeNumber == 3) {
            number = 555;
        }
        return number;
    }

    public int getFirstMaxNumber() {
        int number = 85;

        if (dataTypeNumber == 2) {
            number = 95;
        } else if (dataTypeNumber == 3) {
            number = 555;
        }
        return number;
    }

    public int getSecondMaxNumber() {
        int number = 95;

        if (dataTypeNumber == 2) {
            number = 555;
        } else if (dataTypeNumber == 3) {
            number = 898;
        }
        return number;
    }

    public QuizModel getAddition() {

        int firstMin = getFirstMinNumber();
        int secondMin = getSecondMinNumber();
        int firstMax = getFirstMaxNumber();
        int secondMax = getSecondMaxNumber();


        firstDigit = new Random().nextInt((firstMax - firstMin) + 1) + firstMin;
        secondDigit = new Random().nextInt((secondMax - secondMin) + 1) + secondMin;


        answer = firstDigit + secondDigit;


        question = firstDigit + space + mainModel.sign + space + secondDigit + space + equal + space + "?";

        return addModel();
    }

    public QuizModel getSubtraction() {
        if (dataTypeNumber == 1) {
            firstDigit = new Random().nextInt(100) + 50;
            secondDigit = new Random().nextInt(50) + 10;
        } else if (dataTypeNumber == 2) {
            firstDigit = new Random().nextInt((1000 - 500) + 1) + 500;
            secondDigit = new Random().nextInt((500 - 100) + 1) + 100;
        } else {
            firstDigit = new Random().nextInt((2000 - 1000) + 1) + 1000;
            secondDigit = new Random().nextInt((500 - 100) + 1) + 100;
        }

        answer = firstDigit - secondDigit;

        question = firstDigit + space + mainModel.sign + space + secondDigit + space + equal + space + "?";

        return addModel();
    }

    public QuizModel getMultiplication() {
        if (dataTypeNumber == 1) {
            firstDigit = new Random().nextInt(10) + 1;
            secondDigit = new Random().nextInt(10) + 1;
        } else if (dataTypeNumber == 2) {
            firstDigit = new Random().nextInt(10) + 21;
            secondDigit = new Random().nextInt(10) + 21;
        } else {
            firstDigit = new Random().nextInt(1000) + 10;
            secondDigit = new Random().nextInt(50) + 10;
        }

        answer = firstDigit * secondDigit;
        question = firstDigit + space + mainModel.sign + space + secondDigit + space + equal + space + "?";
        return addModel();
    }

    public QuizModel getDivision() {
        double n1, n2;

        if (dataTypeNumber == 1) {
            n1 = new Random().nextInt((99 - 10) + 1) + 10;
            n2 = new Random().nextInt((10 - 5) + 1) + 5;

        } else if (dataTypeNumber == 2) {

            n1 = new Random().nextInt((999 - 100) + 1) + 100;
            n2 = new Random().nextInt((10 - 5) + 1) + 5;

        } else {
            n1 = new Random().nextInt((9999 - 500) + 1) + 500;
            n2 = new Random().nextInt((100 - 50) + 1) + 50;
        }

        doubleAnswer = n1 / n2;

        firstDigit = (int) n1;
        secondDigit = (int) n2;

        question = firstDigit + space + mainModel.sign + space + secondDigit + space + equal + space + "?";
        return addDoubleModel();
    }


    public QuizModel getSquareData() {
        Random random = new Random();

        if (dataTypeNumber == 1) {
            firstDigit = random.nextInt(100) + 1;
        } else if (dataTypeNumber == 2) {
            firstDigit = random.nextInt(500) + 100;
        } else {
            firstDigit = random.nextInt(1000) + 900;
        }

        answer = firstDigit * firstDigit;
        question = firstDigit + context.getString(R.string.space) + mainModel.sign + space + equal + space + "?";
        return addModel();


    }


    public QuizModel getSquareRootData() {
        Random random = new Random();

        double firstDigit;

        if (dataTypeNumber == 1) {
            firstDigit = random.nextInt(100) + 1;
        } else if (dataTypeNumber == 2) {
            firstDigit = random.nextInt(2000) + 500;
        } else {
            firstDigit = random.nextInt(4000) + 2000;
        }


        doubleAnswer = Math.sqrt(firstDigit);

        question = mainModel.sign + space + (int) firstDigit + space + equal + space + "?";

        return addDoubleModel();
    }


    public QuizModel getCubeData() {
        Random random = new Random();
        double firstDigit;

        if (dataTypeNumber == 1) {
            firstDigit = random.nextInt(100) + 1;
        } else if (dataTypeNumber == 2) {
            firstDigit = random.nextInt(500) + 100;
        } else {
            firstDigit = random.nextInt(700) + 500;
        }

        doubleAnswer = Math.pow(firstDigit, 3);

        question = (int) firstDigit + context.getString(R.string.space) + mainModel.sign + space + equal + space + "?";

        return addDoubleModel();

    }

    public QuizModel getCubeRootData() {
        Random random = new Random();
        double firstDigit;

        if (dataTypeNumber == 1) {
            firstDigit = random.nextInt(100) + 2;
        } else if (dataTypeNumber == 2) {
            firstDigit = random.nextInt(2000) + 500;
        } else {
            firstDigit = random.nextInt(4000) + 2000;
        }

        doubleAnswer = Math.cbrt(firstDigit);
        question = mainModel.sign + space + (int) firstDigit + space + equal + space + "?";

        return addDoubleModel();
    }

    public QuizModel getFactorialData() {

        if (dataTypeNumber == 1) {
            firstDigit = new Random().nextInt((10 - 2) + 1) + 2;
        } else if (dataTypeNumber == 2) {
            firstDigit = new Random().nextInt((20 - 5) + 1) + 5;
        } else {
            firstDigit = new Random().nextInt((100 - 40) + 1) + 40;
        }


        int fact = 1;
        for (int i = 1; i <= firstDigit; i++) {
            fact = fact * i;
        }


        answer = fact;


        question = firstDigit + space + mainModel.sign + space + equal + space + "?";
        Log.e("doubleAnswer", "" + answer + "===" + mainModel.sign + "====" + question);
        if (answer < 0) {
            return getFactorialData();
        } else {
            return addModel();
        }


    }

    public QuizModel getMixedData() {


        if (dataTypeNumber == 1) {
            return getEasyMixedData();
        } else if (dataTypeNumber == 2) {
            return getMediumMixedData();
        } else {
            return getHardMixedData();
        }

    }


    private QuizModel getEasyMixedData() {

        Random random = new Random();
        Random answerRandom = new Random();
        int digit_1, digit_2, digit_3, digit_4;
        firstDigit = random.nextInt(8) + 1;

        digit_1 = firstDigit;

        firstDigit = random.nextInt(8) + 1;

        digit_2 = firstDigit;

        firstDigit = random.nextInt(8) + 1;

        digit_3 = firstDigit;

        firstDigit = random.nextInt(8) + 1;

        digit_4 = firstDigit;

        int randomSign = answerRandom.nextInt(3);

        if (randomSign == 1) {
            answer = digit_1 + (digit_2 * digit_3) - digit_4;
            question = digit_1 + space + addition_sign + space + digit_2 + space + multiplication_sign + space + digit_3 + space + subtraction_sign + space + digit_4;

        } else if (randomSign == 2) {
            answer = (digit_1 * digit_2) - digit_3 + digit_4;
            question = digit_1 + space + multiplication_sign + space + digit_2 + space + subtraction_sign + space + digit_3 + space + addition_sign + space + digit_4;
        } else {
            answer = digit_1 - digit_2 + (digit_3 * digit_4);
            question = digit_1 + space + subtraction_sign + space + digit_2 + space + addition_sign + space + digit_3 + space + multiplication_sign + space + digit_4;
        }


        return addModel();

    }


    private QuizModel getMediumMixedData() {
        Random random = new Random();
        Random answerRandom = new Random();
        double digit_1, digit_2, digit_3, digit_4;
        firstDigit = random.nextInt(85) + 10;
        digit_1 = firstDigit;

        firstDigit = random.nextInt(85) + 10;

        digit_2 = firstDigit;

        firstDigit = random.nextInt(85) + 10;

        digit_3 = firstDigit;

        firstDigit = random.nextInt(85) + 10;

        digit_4 = firstDigit;

        int randomSign = answerRandom.nextInt(3);

        if (randomSign == 1) {
            doubleAnswer = digit_1 + digit_2 * (digit_3 / digit_4);
            question = (int) digit_1 + space + addition_sign + space + (int) digit_2 + space + multiplication_sign + space + (int) digit_3 + space + division_sign + space + (int) digit_4;
        } else if (randomSign == 2) {
            doubleAnswer = (digit_1) * (digit_2) - digit_3 + digit_4;
            question = (int) digit_1 + space + multiplication_sign + space + (int) digit_2 + space + subtraction_sign + (int) digit_3 + space +
                    addition_sign + space + (int) digit_4;
        } else {

            doubleAnswer = digit_1 - digit_2 + (digit_3) * (digit_4);
            question = (int) digit_1 + space + subtraction_sign + space + (int) digit_2 + space + addition_sign + space + (int) digit_3 + space +
                    multiplication_sign + space + (int) digit_4;
        }

        return addDoubleModel();

    }


    private QuizModel getHardMixedData() {
        Random random = new Random();
        Random answerRandom = new Random();
        double digit_1, digit_2, digit_3, digit_4;
        firstDigit = random.nextInt(445) + 100;


        digit_1 = firstDigit;

        firstDigit = random.nextInt(885) + 100;

        digit_2 = firstDigit;

        firstDigit = random.nextInt(885) + 100;

        digit_3 = firstDigit;

        firstDigit = random.nextInt(885) + 100;

        digit_4 = firstDigit;

        int randomSign = answerRandom.nextInt(3);

        if (randomSign == 1) {

            doubleAnswer = digit_1 + digit_2 * (digit_3) / (digit_4);
            question = (int) digit_1 + space + addition_sign + space + (int) digit_2 + space + multiplication_sign + space + (int) digit_3 + space +
                    division_sign + space + (int) digit_4;
        } else if (randomSign == 2) {

            doubleAnswer = (digit_1) * (digit_2) - (digit_3) + (digit_4);
            question = (int) digit_1 + space + multiplication_sign + space + (int) digit_2 + space + subtraction_sign + space + (int) digit_3 + space +
                    addition_sign + space + (int) digit_4;
        } else {
            doubleAnswer = (digit_1) - (digit_2) / (digit_3) * (digit_4);
            question = (int) digit_1 + space + subtraction_sign + space + (int) digit_2 + space + division_sign + space + (int) digit_3 + space +
                    multiplication_sign + space + (int) digit_4;
        }


        return addDoubleModel();


    }


    public QuizModel getAdditionSubtraction() {
        Random random = new Random();
        int n1, n2, n3;
        int operator = (int) (Math.random() * 2) + 1;
        int operator1 = (int) (Math.random() * 2) + 1;

        boolean isMinus = operator == 1;
        boolean isMinus1 = operator1 == 1;


        if (dataTypeNumber == 1) {
            n1 = random.nextInt(100) + 1;
            n2 = random.nextInt(100) + 1;
            n3 = random.nextInt(20) + 1;
        } else if (dataTypeNumber == 2) {
            n1 = random.nextInt(1000) + 1;
            n2 = random.nextInt(1000) + 1;
            n3 = random.nextInt(500) + 1;
        } else {
            n1 = random.nextInt(9999) + 1;
            n2 = random.nextInt(9999) + 1;
            n3 = random.nextInt(500) + 1;
        }


        if (isMinus) {

            if (isMinus1) {
                answer = n1 + n2 + (-n3);
                question = n1 + space + addition_sign + space + n2 + space + addition_sign + space + "(" + subtraction_sign + n3 + ")";
            } else {
                answer = n1 + (-n2) + (-n3);
                question = n1 + space + addition_sign + space + "(" + subtraction_sign + n2 + ")" + space + addition_sign + space + "(" + subtraction_sign + n3 + ")";
            }


        } else {

            if (isMinus1) {
                answer = n1 + (-n2) + n3;
                question = n1 + space + addition_sign + space + "(" + subtraction_sign + n2 + ")" + space + addition_sign + space + n3;
            } else {
                answer = n1 + n2 + n3;
                question = n1 + space + addition_sign + space + n2 + space + addition_sign + space + n3;
            }

        }

        return addModel();

    }

    public QuizModel getComplicatedMultiplication() {
        Random random = new Random();
        int n1, n2, n3;
        int operator = (int) (Math.random() * 2) + 1;

        boolean isMinus = operator == 1;


        if (dataTypeNumber == 1) {
            n1 = random.nextInt(100) + 1;
            n2 = random.nextInt(50) + 1;
            n3 = random.nextInt(20) + 1;
        } else if (dataTypeNumber == 2) {
            n1 = random.nextInt(1000) + 1;
            n2 = random.nextInt(100) + 1;
            n3 = random.nextInt(50) + 1;
        } else {
            n1 = random.nextInt(9999) + 1;
            n2 = random.nextInt(1000) + 1;
            n3 = random.nextInt(500) + 1;
        }


        if (isMinus) {


            answer = n1 + ((-n2) * n3);
            question = n1 + space + addition_sign + space + "(" + subtraction_sign + n2 + ")" + space + multiplication_sign + space + n3;
        } else {
            answer = n1 + (n2 * n3);

            question = n1 + space + addition_sign + space + n2 + space + multiplication_sign + space + n3;
        }


        return addModel();

    }

    public QuizModel getComplicatedDivision() {
        Random random = new Random();
        double n1, n2, n3;
        int operator = (int) (Math.random() * 2) + 1;

        boolean isMinus = operator == 1;


        if (dataTypeNumber == 1) {
            n1 = random.nextInt(100) + 1;
            n2 = random.nextInt(50) + 1;
            n3 = random.nextInt(20) + 1;
        } else if (dataTypeNumber == 2) {
            n1 = random.nextInt(1000) + 1;
            n2 = random.nextInt(100) + 1;
            n3 = random.nextInt(50) + 1;
        } else {
            n1 = random.nextInt(9999) + 1;
            n2 = random.nextInt(1000) + 1;
            n3 = random.nextInt(500) + 1;
        }


        if (isMinus) {


            doubleAnswer = n1 + ((-n2) / n3);
            question = (int) n1 + addition_sign + "(" + subtraction_sign + (int) n2 + ")" + division_sign + (int) n3;
        } else {
            doubleAnswer = n1 + (n2 / n3);

            question = (int) n1 + addition_sign + (int) n2 + division_sign + (int) n3;
        }

        return addDoubleModel();

    }

    public QuizModel addModel() {
        int op_1 = answer + 10;
        int op_2 = answer - 10;
        if (op_2 < 0) {
            op_2 = answer + 15;
        }

        int op_3 = answer + 20;
        List<String> stringList = new ArrayList<>();
        stringList.add(String.valueOf(op_1));
        stringList.add(String.valueOf(op_2));
        stringList.add(String.valueOf(op_3));
        stringList.add(String.valueOf(answer));


        QuizModel quizModel;

        if (!isTrueFalseQuiz) {
            if (!TextUtils.isEmpty(question)) {
                quizModel = new QuizModel(question, String.valueOf(answer), String.valueOf(op_1), String.valueOf(op_2), String.valueOf(op_3));
            } else {
                quizModel = new QuizModel(String.valueOf(firstDigit), String.valueOf(secondDigit), String.valueOf(answer), String.valueOf(op_1), String.valueOf(op_2), String.valueOf(op_3));
            }
        } else {
            int helpTag = new Random().nextInt(4) + 1;
            int ans;
            String s, stringAnswer;
            stringAnswer = context.getString(R.string.str_false);
            if (helpTag == 1) {
                ans = op_1;
            } else if (helpTag == 2) {
                ans = op_2;
            } else if (helpTag == 3) {
                ans = op_3;
            } else {
                stringAnswer = context.getString(R.string.str_true);
                ans = answer;
            }


            if (!TextUtils.isEmpty(question)) {
                s = question + " = " + ans;
            } else {
                s = firstDigit + " " + mainModel.sign + " " + secondDigit + " = " + ans;
            }


            quizModel = new QuizModel(s, stringAnswer);
        }
        Collections.shuffle(stringList);
        quizModel.setOptionList(stringList);
        return quizModel;
    }


    public QuizModel addDoubleModel() {
        double opDouble1 = doubleAnswer + 10;
        double opDouble2 = doubleAnswer - 10;

        if (opDouble2 < 0) {
            opDouble2 = doubleAnswer + 15;
        }
        double opDouble3 = doubleAnswer + 20;
        List<String> stringList = new ArrayList<>();
        stringList.add(Constant.returnFormatNumber(opDouble1));
        stringList.add(Constant.returnFormatNumber(opDouble2));
        stringList.add(Constant.returnFormatNumber(opDouble3));
        stringList.add(Constant.returnFormatNumber(doubleAnswer));


        QuizModel quizModel;

        if (!isTrueFalseQuiz) {
            if (!TextUtils.isEmpty(question)) {
                quizModel = new QuizModel(question, Constant.returnFormatNumber(doubleAnswer), String.valueOf(opDouble1),
                        String.valueOf(opDouble2), String.valueOf(opDouble3));

            } else {
                quizModel = new QuizModel(String.valueOf(firstDigit), String.valueOf(secondDigit), Constant.returnFormatNumber(doubleAnswer), String.valueOf(opDouble1),
                        String.valueOf(opDouble2), String.valueOf(opDouble3));
            }
        } else {
            int helpTag = new Random().nextInt(4) + 1;
            double ans;
            String s, stringAnswer;
            stringAnswer = context.getString(R.string.str_false);
            if (helpTag == 1) {
                ans = opDouble1;
            } else if (helpTag == 2) {
                ans = opDouble2;
            } else if (helpTag == 3) {
                ans = opDouble3;
            } else {
                ans = doubleAnswer;
                stringAnswer = context.getString(R.string.str_true);
            }


            if (!TextUtils.isEmpty(question)) {
                s = question + " = " + Constant.returnFormatNumber(ans);
            } else {
                s = firstDigit + " " + mainModel.sign + " " + secondDigit + " = " + Constant.returnFormatNumber(ans);
            }


            quizModel = new QuizModel(s, stringAnswer);
        }
        Collections.shuffle(stringList);
        quizModel.setOptionList(stringList);
        return quizModel;
    }


}
