package com.elmuj.learnmath.model;

public class ProgressModel {

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String tableName, type;
    public int id;

    public int level_no, isShow;
    public int progress;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int score;
    public int highScore;


    public ProgressModel() {

    }


    public ProgressModel(String tableName, String type, int level_no, int progress) {
        this.tableName = tableName;
        this.level_no = level_no;
        this.progress = progress;
        this.type = type;
    }

}
