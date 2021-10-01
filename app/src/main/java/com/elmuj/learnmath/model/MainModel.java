package com.elmuj.learnmath.model;

import java.util.List;

public class MainModel {
    public String title, sign, tableName;
    public int id, totalQuestion;
    public List<SubModel> subModelList;
    public List<Integer> iconList;
    public boolean isExpand;
    public boolean isLearnQuiz;


    public MainModel(String title, boolean isExpand, boolean isLearnQuiz) {
        this.title = title;
        this.isExpand = isExpand;
        this.isLearnQuiz = isLearnQuiz;
    }

    public MainModel(String title, boolean isExpand) {
        this.title = title;
        this.isExpand = isExpand;
    }
}


