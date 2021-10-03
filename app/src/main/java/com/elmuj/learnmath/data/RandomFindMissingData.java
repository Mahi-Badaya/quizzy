package com.elmuj.learnmath.data;

import android.content.Context;
import android.util.Log;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.model.MainModel;
import com.elmuj.learnmath.model.QuizModel;
import com.elmuj.learnmath.utils.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class RandomFindMissingData {

    private int level_no;
    private int firstDigit;
    private double firstDoubleDigit;
    private int secondDigit;
    private double secondDoubleDigit;
    private int answer;
    private double doubleAnswer;
    private Context context;
    private String question, tableName;
    MainModel mainModel;
    int helpTag;
    double digit_1, digit_2, digit_3, digit_4;

    String multiplication_sign, addition_sign, subtraction_sign, division_sign;
    public int dataTypeNumber = 1;

    public RandomFindMissingData(Context context, MainModel mainModel, int level_no) {
        this.context = context;
        this.mainModel = mainModel;
        this.tableName = mainModel.tableName;
        this.level_no = level_no;
        if (level_no > 10 && level_no <= 20) {
            dataTypeNumber = 2;
        } else if (level_no > 20 && level_no <= 30) {
            dataTypeNumber = 3;
        }

        addition_sign = context.getString(R.string.addition_sign);
        multiplication_sign = context.getString(R.string.multiplication_sign);
        division_sign = context.getString(R.string.division_sign);
        subtraction_sign = context.getString(R.string.subtraction_sign);
    }


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
        if (level_no > 10 && level_no <= 20) {
            number = 15;
        } else if (level_no > 20 && level_no <= 30) {
            number = 150;
        }
        return number;
    }

    public int getSecondMinNumber() {
        int number = 15;

        if (level_no > 10 && level_no <= 20) {
            number = 150;
        } else if (level_no > 20 && level_no <= 30) {
            number = 555;
        }
        return number;
    }

    public int getFirstMaxNumber() {
        int number = 85;

        if (level_no > 10 && level_no <= 20) {
            number = 95;
        } else if (level_no > 20 && level_no <= 30) {
            number = 555;
        }
        return number;
    }

    public int getSecondMaxNumber() {
        int number = 95;

        if (level_no > 10 && level_no <= 20) {
            number = 555;
        } else if (level_no > 20 && level_no <= 30) {
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
        addModel();


        if (helpTag == 1) {
            question = "? " + mainModel.sign + " " + secondDigit + " = " + (firstDigit + secondDigit);
        } else if (helpTag == 2) {
            question = firstDigit + " " + mainModel.sign + " ? = " + (firstDigit + secondDigit);
        } else {
            question = firstDigit + " " + mainModel.sign + " " + secondDigit + " = ?";
        }

        quizModel.question = question;
        return quizModel;
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


        addModel();


        if (helpTag == 1) {
            question = "? " + mainModel.sign + " " + secondDigit + " = " + (firstDigit - secondDigit);
        } else if (helpTag == 2) {
            question = firstDigit + " " + mainModel.sign + " ? = " + (firstDigit - secondDigit);
        } else {
            question = firstDigit + " " + mainModel.sign + " " + secondDigit + " = ?";
        }
        quizModel.question = question;
        return quizModel;


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


        addModel();


        if (helpTag == 1) {
            question = "? " + mainModel.sign + " " + secondDigit + " = " + (firstDigit * secondDigit);
        } else if (helpTag == 2) {
            question = firstDigit + " " + mainModel.sign + " ? = " + (firstDigit * secondDigit);
        } else {
            question = firstDigit + " " + mainModel.sign + " " + secondDigit + " = ?";
        }
        quizModel.question = question;
        return quizModel;


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

        firstDoubleDigit = n1;
        secondDoubleDigit = n2;


        addDoubleModel();


        if (helpTag == 1) {
            question = "? " + mainModel.sign + " " + (int) secondDoubleDigit + " = " + Constant.returnFormatNumber((firstDoubleDigit / secondDoubleDigit));
        } else if (helpTag == 2) {
            question = (int) firstDoubleDigit + " " + mainModel.sign + " ? = " + Constant.returnFormatNumber((firstDoubleDigit / secondDoubleDigit));
        } else {
            question = (int) firstDoubleDigit + " " + mainModel.sign + " " + (int) secondDoubleDigit + " = ?";
        }
        quizModel.question = question;
        return quizModel;

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
        question = firstDigit + context.getString(R.string.space) + mainModel.sign;


        addSingleModel();


        if (helpTag == 1) {
            question = "? " + mainModel.sign + " = " + (firstDigit * firstDigit);
        } else {
            question = firstDigit + " " + mainModel.sign + " = ?";
        }
        quizModel.question = question;
        return quizModel;


    }


    public QuizModel getSquareRootData() {
        Random random = new Random();


        if (dataTypeNumber == 1) {
            firstDoubleDigit = random.nextInt(100) + 1;
        } else if (dataTypeNumber == 2) {
            firstDoubleDigit = random.nextInt(2000) + 500;
        } else {
            firstDoubleDigit = random.nextInt(4000) + 2000;
        }


        doubleAnswer = Math.sqrt(firstDoubleDigit);

        question = mainModel.sign + (int) firstDoubleDigit;


        addDoubleSingleModel();


        if (helpTag == 1) {
            question = mainModel.sign + " ? = " + Constant.returnFormatNumber(Math.sqrt(firstDoubleDigit));
        } else {
            question = mainModel.sign + (int) firstDoubleDigit + " = ?";
        }
        quizModel.question = question;
        return quizModel;

    }


    public QuizModel getCubeData() {
        Random random = new Random();

        if (dataTypeNumber == 1) {
            firstDoubleDigit = random.nextInt(100) + 1;
        } else if (dataTypeNumber == 2) {
            firstDoubleDigit = random.nextInt(500) + 100;
        } else {
            firstDoubleDigit = random.nextInt(700) + 500;
        }

        doubleAnswer = Math.pow(firstDoubleDigit, 3);

        question = (int) firstDoubleDigit + context.getString(R.string.space) + mainModel.sign;


        addDoubleSingleModel();


        if (helpTag == 1) {
            question = "? " + mainModel.sign + " = " + Constant.returnFormatNumber(Math.pow(firstDoubleDigit, 3));
        } else {
            question = (int) firstDoubleDigit + mainModel.sign + " = ?";
        }
        quizModel.question = question;
        return quizModel;

    }

    public QuizModel getCubeRootData() {
        Random random = new Random();

        if (dataTypeNumber == 1) {
            firstDoubleDigit = random.nextInt(100) + 2;
        } else if (dataTypeNumber == 2) {
            firstDoubleDigit = random.nextInt(2000) + 500;
        } else {
            firstDoubleDigit = random.nextInt(4000) + 2000;
        }

        doubleAnswer = Math.cbrt(firstDoubleDigit);
        question = mainModel.sign + (int) firstDoubleDigit;

        addDoubleSingleModel();


        if (helpTag == 1) {
            question = mainModel.sign + " ? = " + Constant.returnFormatNumber(Math.cbrt(firstDoubleDigit));
        } else {
            question = mainModel.sign + (int) firstDoubleDigit + " = ?";
        }
        quizModel.question = question;
        return quizModel;

    }

    public QuizModel getFactorialData() {

        if (dataTypeNumber == 1) {
            firstDigit = new Random().nextInt((20 - 5) + 1) + 5;
        } else if (dataTypeNumber == 2) {
            firstDigit = new Random().nextInt((40 - 20) + 1) + 20;
        } else {
            firstDigit = new Random().nextInt((100 - 40) + 1) + 40;
        }


        int fact = 1;
        for (int i = 1; i <= firstDigit; i++) {
            fact = fact * i;
        }


        answer = fact;


        question = firstDigit + mainModel.sign;
        Log.e("doubleAnswer", "" + answer + "===" + mainModel.sign + "====" + question);
        if (answer < 0) {
            addSingleModel();


            if (helpTag == 1) {
                question = "? " + mainModel.sign + " = " + (fact);
            } else {
                question = firstDigit + " " + mainModel.sign + " = ?";
            }
            quizModel.question = question;
            return quizModel;
        } else {

            addSingleModel();


            if (helpTag == 1) {
                question = "? " + mainModel.sign + " = " + (fact);
            } else {
                question = firstDigit + " " + mainModel.sign + " = ?";
            }
            quizModel.question = question;
            return quizModel;
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
        helpTag = new Random().nextInt(5) + 1;


        if (randomSign == 1) {
            answer = digit_1 + (digit_2 * digit_3) - digit_4;


            if (helpTag == 1) {
                question = " ? " + addition_sign + digit_2 + multiplication_sign + digit_3 + subtraction_sign + digit_4 + " = " + answer;
            } else if (helpTag == 2) {
                question = digit_1 + addition_sign + " ? " + multiplication_sign + digit_3 + subtraction_sign + digit_4 + " = " + answer;
            } else if (helpTag == 3) {
                question = digit_1 + addition_sign + digit_2 + multiplication_sign + " ? " + subtraction_sign + digit_4 + " = " + answer;
            } else if (helpTag == 4) {
                question = digit_1 + addition_sign + digit_2 + multiplication_sign + digit_3 + subtraction_sign + " ? = " + answer;
            } else {
                question = digit_1 + addition_sign + digit_2 + multiplication_sign + digit_3 + subtraction_sign + digit_4 + " = ?";
            }


        } else if (randomSign == 2) {
            answer = (digit_1 * digit_2) - digit_3 + digit_4;
//            question = digit_1 + multiplication_sign + digit_2 + subtraction_sign + digit_3 + addition_sign + digit_4;


            if (helpTag == 1) {
                question = " ? " + multiplication_sign + digit_2 + subtraction_sign + digit_3 + addition_sign + digit_4 + " = " + answer;
            } else if (helpTag == 2) {
                question = digit_1 + multiplication_sign + " ? " + subtraction_sign + digit_3 + addition_sign + digit_4 + " = " + answer;
            } else if (helpTag == 3) {
                question = digit_1 + multiplication_sign + digit_2 + subtraction_sign + " ? " + addition_sign + digit_4 + " = " + answer;
            } else if (helpTag == 4) {
                question = digit_1 + multiplication_sign + digit_2 + subtraction_sign + digit_3 + addition_sign + " ? = " + answer;
            } else {
                question = digit_1 + multiplication_sign + digit_2 + subtraction_sign + digit_3 + addition_sign + digit_4 + " = ?";
            }

        } else {

            answer = digit_1 - digit_2 + (digit_3 * digit_4);

            if (helpTag == 1) {
                question = " ? " + subtraction_sign + digit_2 + addition_sign + digit_3 + multiplication_sign + digit_4 + " = " + answer;
            } else if (helpTag == 2) {
                question = digit_1 + subtraction_sign + " ? " + addition_sign + digit_3 + multiplication_sign + digit_4 + " = " + answer;
            } else if (helpTag == 3) {
                question = digit_1 + subtraction_sign + digit_2 + addition_sign + " ? " + multiplication_sign + digit_4 + " = " + answer;
            } else if (helpTag == 4) {
                question = digit_1 + subtraction_sign + digit_2 + addition_sign + digit_3 + multiplication_sign + " ? = " + answer;
            } else {
                question = digit_1 + subtraction_sign + digit_2 + addition_sign + digit_3 + multiplication_sign + digit_4 + " = ?";
            }

        }


        if (helpTag == 1) {
            answer = digit_1;
        } else if (helpTag == 2) {
            answer = digit_2;
        } else if (helpTag == 3) {
            answer = digit_3;
        } else if (helpTag == 4) {
            answer = digit_4;
        }


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


        quizModel = new QuizModel();
        quizModel.answer = String.valueOf(answer);

        Collections.shuffle(stringList);
        quizModel.setOptionList(stringList);
        quizModel.question = question;
        return quizModel;

    }


    private QuizModel getMediumMixedData() {
        Random random = new Random();
        Random answerRandom = new Random();
        firstDigit = random.nextInt(85) + 10;
        digit_1 = firstDigit;

        firstDigit = random.nextInt(85) + 10;

        digit_2 = firstDigit;

        firstDigit = random.nextInt(85) + 10;

        digit_3 = firstDigit;

        firstDigit = random.nextInt(85) + 10;

        digit_4 = firstDigit;

        int randomSign = answerRandom.nextInt(3);
        helpTag = new Random().nextInt(5) + 1;


        if (randomSign == 1) {
            doubleAnswer = digit_1 + digit_2 * (digit_3 / digit_4);

            if (helpTag == 1) {
                question = "? " + addition_sign + (int) digit_2 + multiplication_sign + (int) digit_3 + division_sign + (int) digit_4 + " = " + doubleAnswer;
            } else if (helpTag == 2) {
                question = (int) digit_1 + addition_sign + " ? " + multiplication_sign + (int) digit_3 + division_sign + (int) digit_4 + " = " + doubleAnswer;
            } else if (helpTag == 3) {
                question = (int) digit_1 + addition_sign + (int) digit_2 + multiplication_sign + " ? " + division_sign + (int) digit_4 + " = " + doubleAnswer;
            } else if (helpTag == 4) {
                question = (int) digit_1 + addition_sign + (int) digit_2 + multiplication_sign + (int) digit_3 + division_sign + " ? = " + doubleAnswer;
            } else {
                question = (int) digit_1 + addition_sign + (int) digit_2 + multiplication_sign + (int) digit_3 + division_sign + (int) digit_4 + " = ?";
            }
        } else if (randomSign == 2) {
            doubleAnswer = (digit_1) * (digit_2) - digit_3 + digit_4;
            if (helpTag == 1) {
                question = "? " + multiplication_sign + (int) digit_2 + subtraction_sign + (int) digit_3 + addition_sign + (int) digit_4 + doubleAnswer;
            } else if (helpTag == 2) {
                question = (int) digit_1 + multiplication_sign + " ? " + subtraction_sign + (int) digit_3 + addition_sign + (int) digit_4 + doubleAnswer;
            } else if (helpTag == 3) {
                question = (int) digit_1 + multiplication_sign + (int) digit_2 + subtraction_sign + " ? " + addition_sign + (int) digit_4 + doubleAnswer;
            } else if (helpTag == 4) {
                question = (int) digit_1 + multiplication_sign + (int) digit_2 + subtraction_sign + (int) digit_3 + addition_sign + " ? = " + doubleAnswer;
            } else {
                question = (int) digit_1 + multiplication_sign + (int) digit_2 + subtraction_sign + (int) digit_3 + addition_sign + (int) digit_4 + " = ?";
            }

        } else {

            doubleAnswer = digit_1 - digit_2 + (digit_3) * (digit_4);


            if (helpTag == 1) {
                question = "? " + subtraction_sign + (int) digit_2 + addition_sign + (int) digit_3 + multiplication_sign + (int) digit_4 + doubleAnswer;
            } else if (helpTag == 2) {
                question = (int) digit_1 + subtraction_sign + " ? " + addition_sign + (int) digit_3 + multiplication_sign + (int) digit_4 + doubleAnswer;
            } else if (helpTag == 3) {
                question = (int) digit_1 + subtraction_sign + (int) digit_2 + addition_sign + " ? " + multiplication_sign + (int) digit_4 + doubleAnswer;
            } else if (helpTag == 4) {
                question = (int) digit_1 + subtraction_sign + (int) digit_2 + addition_sign + (int) digit_3 + multiplication_sign + " ? = " + doubleAnswer;
            } else {
                question = (int) digit_1 + subtraction_sign + (int) digit_2 + addition_sign + (int) digit_3 + multiplication_sign + (int) digit_4 + " = ?";
            }


        }

        return getMixedMissingModel();

    }


    private QuizModel getHardMixedData() {
        Random random = new Random();
        Random answerRandom = new Random();
        firstDigit = random.nextInt(445) + 100;


        digit_1 = firstDigit;

        firstDigit = random.nextInt(885) + 100;

        digit_2 = firstDigit;

        firstDigit = random.nextInt(885) + 100;

        digit_3 = firstDigit;

        firstDigit = random.nextInt(885) + 100;

        digit_4 = firstDigit;

        int randomSign = answerRandom.nextInt(3);


        helpTag = new Random().nextInt(5) + 1;


        if (randomSign == 1) {

            doubleAnswer = digit_1 + digit_2 * (digit_3) / (digit_4);

            if (helpTag == 1) {
                question = "? " + addition_sign + (int) digit_2 + multiplication_sign + (int) digit_3 + division_sign + (int) digit_4 + " = " + doubleAnswer;
            } else if (helpTag == 2) {
                question = (int) digit_1 + addition_sign + " ? " + multiplication_sign + (int) digit_3 + division_sign + (int) digit_4 + " = " + doubleAnswer;
            } else if (helpTag == 3) {
                question = (int) digit_1 + addition_sign + (int) digit_2 + multiplication_sign + " ? " + division_sign + (int) digit_4 + " = " + doubleAnswer;
            } else if (helpTag == 4) {
                question = (int) digit_1 + addition_sign + (int) digit_2 + multiplication_sign + (int) digit_3 + division_sign + " ? = " + doubleAnswer;
            } else {
                question = (int) digit_1 + addition_sign + (int) digit_2 + multiplication_sign + (int) digit_3 + division_sign + (int) digit_4 + " = ?";
            }


        } else if (randomSign == 2) {

            doubleAnswer = (digit_1) * (digit_2) - (digit_3) + (digit_4);


            if (helpTag == 1) {
                question = "? " + multiplication_sign + (int) digit_2 + subtraction_sign + (int) digit_3 + addition_sign + (int) digit_4 + doubleAnswer;
            } else if (helpTag == 2) {
                question = (int) digit_1 + multiplication_sign + " ? " + subtraction_sign + (int) digit_3 + addition_sign + (int) digit_4 + doubleAnswer;
            } else if (helpTag == 3) {
                question = (int) digit_1 + multiplication_sign + (int) digit_2 + subtraction_sign + " ? " + addition_sign + (int) digit_4 + doubleAnswer;
            } else if (helpTag == 4) {
                question = (int) digit_1 + multiplication_sign + (int) digit_2 + subtraction_sign + (int) digit_3 + addition_sign + " ? = " + doubleAnswer;
            } else {
                question = (int) digit_1 + multiplication_sign + (int) digit_2 + subtraction_sign + (int) digit_3 + addition_sign + (int) digit_4 + " = ?";
            }


        } else {
            doubleAnswer = (digit_1) - (digit_2) / (digit_3) * (digit_4);

            if (helpTag == 1) {
                question = "? " + subtraction_sign + (int) digit_2 + division_sign + (int) digit_3 + multiplication_sign + (int) digit_4 + doubleAnswer;
            } else if (helpTag == 2) {
                question = (int) digit_1 + subtraction_sign + " ? " + division_sign + (int) digit_3 + multiplication_sign + (int) digit_4 + doubleAnswer;
            } else if (helpTag == 3) {
                question = (int) digit_1 + subtraction_sign + (int) digit_2 + division_sign + " ? " + multiplication_sign + (int) digit_4 + doubleAnswer;
            } else if (helpTag == 4) {
                question = (int) digit_1 + subtraction_sign + (int) digit_2 + division_sign + (int) digit_3 + multiplication_sign + " ? = " + doubleAnswer;
            } else {
                question = (int) digit_1 + subtraction_sign + (int) digit_2 + division_sign + (int) digit_3 + multiplication_sign + (int) digit_4 + " = ?";
            }


        }


        return getMixedMissingModel();


    }

    public QuizModel getMixedMissingModel() {
        if (helpTag == 1) {
            doubleAnswer = digit_1;
        } else if (helpTag == 2) {
            doubleAnswer = digit_2;
        } else if (helpTag == 3) {
            doubleAnswer = digit_3;
        } else if (helpTag == 4) {
            doubleAnswer = digit_4;
        }


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


        quizModel = new QuizModel();
        quizModel.answer = Constant.returnFormatNumber(doubleAnswer);

        Collections.shuffle(stringList);
        quizModel.setOptionList(stringList);
        quizModel.question = question;
        return quizModel;
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

        helpTag = new Random().nextInt(4) + 1;


        if (isMinus) {

            if (isMinus1) {
                answer = n1 + n2 + (-n3);
                if (helpTag == 1) {
                    question = "? " + addition_sign + n2 + addition_sign + "(" + subtraction_sign + n3 + ") = " + answer;
                } else if (helpTag == 2) {
                    question = n1 + addition_sign + " ? " + addition_sign + "(" + subtraction_sign + n3 + ") = " + answer;
                } else if (helpTag == 3) {
                    question = n1 + addition_sign + n2 + addition_sign + "(" + subtraction_sign + " ? " + ") = " + answer;
                } else {
                    question = n1 + addition_sign + n2 + addition_sign + "(" + subtraction_sign + n3 + ") = ?";
                }
            } else {
                answer = n1 + (-n2) + (-n3);

                if (helpTag == 1) {
                    question = "? " + addition_sign + "(" + subtraction_sign + n2 + ")" + addition_sign + "(" + subtraction_sign + n3 + ") = " + answer;
                } else if (helpTag == 2) {
                    question = n1 + addition_sign + "(" + subtraction_sign + " ? " + ")" + addition_sign + "(" + subtraction_sign + n3 + ") = " + answer;
                } else if (helpTag == 3) {
                    question = n1 + addition_sign + "(" + subtraction_sign + n2 + ")" + addition_sign + "(" + subtraction_sign + " ? " + ") = " + answer;
                } else {
                    question = n1 + addition_sign + "(" + subtraction_sign + n2 + ")" + addition_sign + "(" + subtraction_sign + n3 + ") = ?";
                }

            }

        } else {

            if (isMinus1) {
                answer = n1 + (-n2) + n3;
                question = n1 + addition_sign + "(" + subtraction_sign + n2 + ")" + addition_sign + n3;


                if (helpTag == 1) {
                    question = "? " + addition_sign + "(" + subtraction_sign + n2 + ")" + addition_sign + n3 + " = " + answer;
                } else if (helpTag == 2) {
                    question = n1 + addition_sign + "(" + subtraction_sign + " ? " + ")" + addition_sign + n3 + " = " + answer;
                } else if (helpTag == 3) {
                    question = n1 + addition_sign + "(" + subtraction_sign + n2 + ")" + addition_sign + " ? " + " = " + answer;
                } else {
                    question = n1 + addition_sign + "(" + subtraction_sign + n2 + ")" + addition_sign + n3 + " = ?";
                }

            } else {
                answer = n1 + n2 + n3;
                question = n1 + addition_sign + n2 + addition_sign + n3;


                if (helpTag == 1) {
                    question = "? " + addition_sign + n2 + addition_sign + n3 + " = " + answer;
                } else if (helpTag == 2) {
                    question = n1 + addition_sign + " ? " + addition_sign + n3 + " = " + answer;
                } else if (helpTag == 3) {
                    question = n1 + addition_sign + n2 + addition_sign + " ? " + " = " + answer;
                } else {
                    question = n1 + addition_sign + n2 + addition_sign + n3 + " = ?";
                }
            }

        }

        if (helpTag == 1) {
            answer = n1;
        } else if (helpTag == 2) {
            answer = n2;
        } else if (helpTag == 3) {
            answer = n3;
        }


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


        quizModel = new QuizModel();
        quizModel.answer = String.valueOf(answer);

        Collections.shuffle(stringList);
        quizModel.setOptionList(stringList);
        quizModel.question = question;

        return quizModel;
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
        helpTag = new Random().nextInt(4) + 1;


        if (isMinus) {


            answer = n1 + ((-n2) * n3);
//            question = n1 + addition_sign + "(" + subtraction_sign + n2 + ")" + multiplication_sign + n3;


            if (helpTag == 1) {
                question = "? " + addition_sign + "(" + subtraction_sign + n2 + ")" + multiplication_sign + n3 + " = " + answer;
            } else if (helpTag == 2) {
                question = n1 + addition_sign + "(" + subtraction_sign + " ? " + ")" + multiplication_sign + n3 + " = " + answer;
            } else if (helpTag == 3) {
                question = n1 + addition_sign + "(" + subtraction_sign + n2 + ")" + multiplication_sign + " ? = " + answer;
            } else {
                question = n1 + addition_sign + "(" + subtraction_sign + n2 + ")" + multiplication_sign + n3 + " = ?";
            }
        } else {
            answer = n1 + (n2 * n3);

            question = n1 + addition_sign + n2 + multiplication_sign + n3;


            if (helpTag == 1) {
                question = "? " + addition_sign + n2 + multiplication_sign + n3 + " = " + answer;
            } else if (helpTag == 2) {
                question = n1 + addition_sign + " ? " + multiplication_sign + n3 + " = " + answer;
            } else if (helpTag == 3) {
                question = n1 + addition_sign + n2 + multiplication_sign + " ? = " + answer;
            } else {
                question = n1 + addition_sign + n2 + multiplication_sign + n3 + " = ?";
            }

        }


        if (helpTag == 1) {
            answer = n1;
        } else if (helpTag == 2) {
            answer = n2;
        } else if (helpTag == 3) {
            answer = n3;
        }


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


        quizModel = new QuizModel();
        quizModel.answer = String.valueOf(answer);

        Collections.shuffle(stringList);
        quizModel.setOptionList(stringList);
        quizModel.question = question;

        return quizModel;


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

        helpTag = new Random().nextInt(4) + 1;
        if (isMinus) {


            doubleAnswer = n1 + ((-n2) / n3);

            if (helpTag == 1) {
                question = "? " + addition_sign + "(" + subtraction_sign + (int) n2 + ")" + division_sign + (int) n3 + " = " + doubleAnswer;
            } else if (helpTag == 2) {
                question = (int) n1 + addition_sign + "(" + subtraction_sign + " ? " + ")" + division_sign + (int) n3 + " = " + doubleAnswer;
            } else if (helpTag == 3) {
                question = (int) n1 + addition_sign + "(" + subtraction_sign + (int) n2 + ")" + division_sign + " ? = " + doubleAnswer;
            } else {
                question = (int) n1 + addition_sign + "(" + subtraction_sign + (int) n2 + ")" + division_sign + (int) n3 + " = ?";
            }

        } else {
            doubleAnswer = n1 + (n2 / n3);

            question = (int) n1 + addition_sign + (int) n2 + division_sign + (int) n3;
        }


        if (helpTag == 1) {
            doubleAnswer = n1;
        } else if (helpTag == 2) {
            doubleAnswer = n2;
        } else if (helpTag == 3) {
            doubleAnswer = n3;
        }


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


        quizModel = new QuizModel();
        quizModel.answer = Constant.returnFormatNumber(doubleAnswer);

        Collections.shuffle(stringList);
        quizModel.setOptionList(stringList);


        quizModel.question = question;
        return quizModel;


    }


    public void addModel() {

        helpTag = new Random().nextInt(3) + 1;
        if (helpTag == 1) {
            answer = firstDigit;
        } else if (helpTag == 2) {
            answer = secondDigit;
        }


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


        quizModel = new QuizModel();
        quizModel.answer = String.valueOf(answer);

        Collections.shuffle(stringList);
        quizModel.setOptionList(stringList);
    }

    public void addSingleModel() {

        helpTag = new Random().nextInt(2) + 1;
        if (helpTag == 1) {
            answer = firstDigit;
        }


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


        quizModel = new QuizModel();
        quizModel.answer = String.valueOf(answer);

        Collections.shuffle(stringList);
        quizModel.setOptionList(stringList);
    }

    QuizModel quizModel;

    public void addDoubleModel() {

        helpTag = new Random().nextInt(3) + 1;
        if (helpTag == 1) {
            doubleAnswer = firstDoubleDigit;
        } else if (helpTag == 2) {
            doubleAnswer = secondDoubleDigit;
        }


        double opDouble1 = doubleAnswer + 10;
        double opDouble2 = doubleAnswer - 10;
        double opDouble3 = doubleAnswer + 20;


        if (opDouble2 < 0) {
            opDouble2 = doubleAnswer + 15;
        }

        List<String> stringList = new ArrayList<>();
        stringList.add(Constant.returnFormatNumber(opDouble1));
        stringList.add(Constant.returnFormatNumber(opDouble2));
        stringList.add(Constant.returnFormatNumber(opDouble3));
        stringList.add(Constant.returnFormatNumber(doubleAnswer));


        quizModel = new QuizModel();
        quizModel.answer = Constant.returnFormatNumber(doubleAnswer);

        Collections.shuffle(stringList);
        quizModel.setOptionList(stringList);

    }


    public void addDoubleSingleModel() {

        helpTag = new Random().nextInt(2) + 1;
        if (helpTag == 1) {
            doubleAnswer = firstDoubleDigit;
        }


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


        quizModel = new QuizModel();
        quizModel.answer = Constant.returnFormatNumber(doubleAnswer);

        Collections.shuffle(stringList);
        quizModel.setOptionList(stringList);
    }


}
