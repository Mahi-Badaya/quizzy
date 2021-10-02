package com.elmuj.learnmath.model;

import java.util.ArrayList;
import java.util.List;

public class QuizModel {

    public String sign;


    public String getRem() {
        return rem;
    }

    public void setRem(String rem) {
        this.rem = rem;
    }

    public String rem;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QuizModel() {
    }

    public int id;
    public String firstDigit;
    public String secondDigit, question;
    public String answer;
    public String op_1;
    public String op_2;
    public String op_3;


    public void setOptionList(List<String> optionList) {
        this.optionList = optionList;
    }

    public List<String> optionList = new ArrayList<>();


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public QuizModel(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public QuizModel(String firstDigit, String secondDigit, String answer, String op_1, String op_2, String op_3) {
        this.firstDigit = firstDigit;
        this.secondDigit = secondDigit;
        this.answer = answer;
        this.op_1 = op_1;
        this.op_2 = op_2;
        this.op_3 = op_3;
    }

    public QuizModel(String question, String answer, String op_1, String op_2, String op_3) {
        this.question = question;
        this.answer = answer;
        this.op_1 = op_1;
        this.op_2 = op_2;
        this.op_3 = op_3;
    }

}
