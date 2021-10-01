package com.elmuj.learnmath.model;

import java.util.ArrayList;
import java.util.List;

public class TableModel {

    public String question;
    public String op_1, op_2, op_3, answer;


    public void setOptionList(List<String> optionList) {
        this.optionList = optionList;
    }

    public List<String> optionList = new ArrayList<>();


    public TableModel() {

    }
}
