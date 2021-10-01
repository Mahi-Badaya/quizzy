package com.elmuj.learnmath.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.elmuj.learnmath.model.ProgressModel;
import com.elmuj.learnmath.model.ReminderModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private static DatabaseAccess instance;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private String ID = "id";
    private String PROGRESS = "progress";
    private String TABLENAME = "tableName";
    private String LEVEL_NO = "level_no";
    private String IsShow = "isShow";
    private String TYPE = "type";
    private String SCORE = "score";
    private String HIGH_SCORE = "high_score";
    private String PROGRESS_TABLE = "progress_table";

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        if (db != null) {
            this.db.close();
        }
    }

    public long insertProgressData(ProgressModel progressModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROGRESS, progressModel.progress);
        contentValues.put(TABLENAME, progressModel.tableName);
        contentValues.put(LEVEL_NO, progressModel.level_no);


        Log.e("level_no===", "" + progressModel.level_no);
        if (progressModel.level_no == 1) {
            contentValues.put(IsShow, 1);
        } else {
            contentValues.put(IsShow, 0);
        }

        contentValues.put(TYPE, progressModel.type);
        return db.insert(PROGRESS_TABLE, null, contentValues);
    }


    private int checkReminder(String s) {
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM tbl_reminder WHERE time='" + s + "'", null);
        int count = cursor.getCount();
        cursor.close();
        return count;

    }


    public List<String> getReminderTimeList() {
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT time FROM tbl_reminder", null);
        List<String> reminderModelList = new ArrayList<>();
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    reminderModelList.add(cursor.getString(cursor.getColumnIndex("time")));
                }
                while (cursor.moveToNext());
            }
        }
        cursor.close();
        return reminderModelList;
    }

    public void deleteReminder(int id) {

        try {
            String EXECSql = "DELETE FROM tbl_reminder WHERE id=" + id;
            db.execSQL(EXECSql);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("db_err==", "" + e.getMessage());
        }
    }


    public void updateProgress(int level_no, String type, String tableName, int progress) {
        try {
            String ExecSql = "UPDATE " + PROGRESS_TABLE + " SET " + PROGRESS + "=" + progress + " WHERE " + TABLENAME + " = '" +
                    tableName + "' and " + LEVEL_NO + "= " + level_no + " and " + TYPE + " = '" + type + "'";
            db.execSQL(ExecSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateScore(String type, int level_no, String tableName, int score, int high_score) {
        try {
            String ExecSql = "UPDATE " + PROGRESS_TABLE + " SET " + SCORE + " = " + score + "," + HIGH_SCORE + " = " + high_score + " WHERE " + TABLENAME + " = '" + tableName + "' and " + LEVEL_NO +
                    "= " + level_no + " and " + TYPE + " = '" + type + "'";
            db.execSQL(ExecSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLevel(String type, int level_no, String tableName, int isShow) {
        try {
            String ExecSql = "UPDATE " + PROGRESS_TABLE + " SET " + IsShow + " = " + isShow + " WHERE " + TABLENAME + " = '" + tableName + "' and " + LEVEL_NO +
                    "= " + level_no + " and " + TYPE + " = '" + type + "'";
            db.execSQL(ExecSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<ProgressModel> getProgressLevels(String tableName, String type) {
        Cursor cur = db.rawQuery("select * from " + PROGRESS_TABLE + " where " +
                TYPE + " = '" + type + "' and " + TABLENAME + " ='" + tableName + "';", null);
        List<ProgressModel> item_data = new ArrayList<>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    ProgressModel obj = new ProgressModel();
                    obj.id = cur.getInt(cur.getColumnIndex(ID));
                    obj.progress = cur.getInt(cur.getColumnIndex(PROGRESS));
                    obj.tableName = cur.getString(cur.getColumnIndex(TABLENAME));
                    obj.type = cur.getString(cur.getColumnIndex(TYPE));
                    obj.level_no = cur.getInt(cur.getColumnIndex(LEVEL_NO));
                    obj.score = cur.getInt(cur.getColumnIndex(SCORE));
                    obj.highScore = cur.getInt(cur.getColumnIndex(HIGH_SCORE));
                    obj.isShow = cur.getInt(cur.getColumnIndex(IsShow));
                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        cur.close();
        return item_data;
    }


    public List<ProgressModel> getProgressShowLevel(String tableName, String type) {
        Cursor cur = db.rawQuery("select * from " + PROGRESS_TABLE + " where " +
                TYPE + " = '" + type + "' and " + TABLENAME + " ='" + tableName + "';", null);
        List<ProgressModel> item_data = new ArrayList<>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    ProgressModel obj = new ProgressModel();
                    obj.id = cur.getInt(cur.getColumnIndex(ID));
                    obj.progress = cur.getInt(cur.getColumnIndex(PROGRESS));
                    obj.tableName = cur.getString(cur.getColumnIndex(TABLENAME));
                    obj.type = cur.getString(cur.getColumnIndex(TYPE));
                    obj.level_no = cur.getInt(cur.getColumnIndex(LEVEL_NO));
                    obj.score = cur.getInt(cur.getColumnIndex(SCORE));
                    obj.highScore = cur.getInt(cur.getColumnIndex(HIGH_SCORE));
                    obj.isShow = cur.getInt(cur.getColumnIndex(IsShow));
                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        cur.close();
        return item_data;
    }


    public List<ProgressModel> getScore(String tableName, String type, int level) {
        Cursor cur = db.rawQuery("select * from " + PROGRESS_TABLE + " where " +
                TYPE + " ='" + type + "' and " + TABLENAME + " ='" + tableName + "' and " + LEVEL_NO + " = " + level, null);
        List<ProgressModel> item_data = new ArrayList<>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    ProgressModel obj = new ProgressModel();
                    obj.id = cur.getInt(cur.getColumnIndex(ID));
                    obj.progress = cur.getInt(cur.getColumnIndex(PROGRESS));
                    obj.tableName = cur.getString(cur.getColumnIndex(TABLENAME));
                    obj.type = cur.getString(cur.getColumnIndex(TABLENAME));
                    obj.level_no = cur.getInt(cur.getColumnIndex(LEVEL_NO));
                    obj.score = cur.getInt(cur.getColumnIndex(SCORE));
                    obj.highScore = cur.getInt(cur.getColumnIndex(HIGH_SCORE));
                    obj.isShow = cur.getInt(cur.getColumnIndex(IsShow));
                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        cur.close();
        return item_data;
    }


    public List<ReminderModel> getReminderData() {

        Cursor cursor = db.rawQuery("SELECT * FROM tbl_reminder", null);
        List<ReminderModel> reminderModelList = new ArrayList<>();
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    ReminderModel reminderModel = new ReminderModel();
                    reminderModel.id = cursor.getInt(cursor.getColumnIndex("id"));
                    reminderModel.time = cursor.getString(cursor.getColumnIndex("time"));
                    reminderModel.repeat = cursor.getString(cursor.getColumnIndex("repeat"));
                    reminderModel.ison = cursor.getString(cursor.getColumnIndex("ison"));
                    reminderModelList.add(reminderModel);
                }
                while (cursor.moveToNext());
            }
        }
        cursor.close();
        return reminderModelList;
    }

    public void insertReminder(String time, String repeat, boolean isOn) {

        int i = checkReminder(time);
        if (i == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("time", time);
            contentValues.put("repeat", repeat);
            contentValues.put("ison", isOn);
            db.insert("tbl_reminder", null, contentValues);
        }
    }


    public void updateIsON(int id, String value) {
        try {

            String ExecSql = "UPDATE tbl_reminder SET ison=" + value + " WHERE id=" + id;
            db.execSQL(ExecSql);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("db_err==", "" + e.getMessage());
        }
    }

}
