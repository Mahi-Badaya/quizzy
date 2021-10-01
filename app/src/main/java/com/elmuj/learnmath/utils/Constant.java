package com.elmuj.learnmath.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.elmuj.learnmath.R;
import com.elmuj.learnmath.data.MainData;
import com.elmuj.learnmath.model.DualScoreModel;
import com.elmuj.learnmath.model.HistoryModel;
import com.elmuj.learnmath.model.LearnModel;
import com.elmuj.learnmath.model.MainModel;
import com.elmuj.learnmath.model.ReviewTestListModel;
import com.elmuj.learnmath.model.ReviewTestModel;
import com.elmuj.learnmath.model.SubModel;
import com.elmuj.learnmath.ui.FindMissingActivity;
import com.elmuj.learnmath.ui.InputActivity;
import com.elmuj.learnmath.ui.QuizActivity;
import com.elmuj.learnmath.ui.TrueFalseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Constant {

    public static final int DEFAULT_THEME = 0;


    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;
    public static final int DEFAULT_LEVEL = 30;
    public static final int DEFAULT_QUESTION = 20;
    public static final int TABLE_SIZE = 10;
    public static final int DEVICE_720 = 720;
    public static final int DEVICE_1080 = 1080;
    //    public static final String PRIVACY_POLICY_LINK = "http://www.google.co.uk/";
    public static final String THEMEPOSITION = "THEME_POSITION";
    public static final String POSITION = "POSITION";
    public static final int DEFAULT_QUESTION_SIZE = 20;
    public static final int TIMER = 30;
    public static final String MyPref = "MyPref";
    public static final String LANGUAGEPREF = "LanguagePref";
    public static final String DUEL_MODEL1 = "DUEL_MODEL1";
    public static final String DUEL_MODEL2 = "DUEL_MODEL2";
    public static final String SHARE_LINK = "https://play.google.com/store/apps/details?id=";
    public static final String RATE_LINK = "market://details?id=";
    public static final String ISDraw = "ISDraw";
    public static final int DELAY_SEOCND = 400;
    private static final String LEARN_MODEL = "LEARN_MODEL";
    private static final String MAIN_MODEL = "MAIN_MODEL";
    private static final String SUB_MODEL = "SUB_MODEL";
    private static final String HISTORY = "HISTORY";
    private static final String IsFirstTime = "IsFirstTime";
    private static final String HISTORY_SIZE = "HISTORY_SIZE";
    private static final String COINS = "Coins";
    private static final String SOUND = "Sound";
    private static final String VIBRATE = "Vibrate";
    private static final String EXPANDPOSITION = "EXPANDPOSITION";
    private static final String NIGHT_MODE = "Night_mode";
    private static final String ISReminder = "IsReminder";
    private static final String LANGUAGE_CODE1 = "LANGUAGE_CODE1";
    public static String FROM_SETTINGS = "fromSettings";
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.US);

    public static String getTextString(String s) {
        return s;
    }


    public static int getPlusScore(int countTime) {
        if (countTime < 30 && countTime >= 25) {
            return 500;
        } else if (countTime < 25 && countTime >= 15) {
            return 400;
        } else if (countTime < 15 && countTime >= 5) {
            return 250;
        } else {
            return 100;
        }
    }

    public static List<String> getReminderDaysList() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Mon");
        stringList.add("Tue");
        stringList.add("Wed");
        stringList.add("Thu");
        stringList.add("Fri");
        stringList.add("Sat");
        stringList.add("Sun");
        return stringList;
    }

    public static void sendFeedback(Activity activity, String s) {

        String mailto = "mailto:" + activity.getString(R.string.feedback_mail) +
                "?cc=" + "" +
                "&subject=" + Uri.encode(activity.getString(R.string.app_name)) +
                "&body=" + Uri.encode(s);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));

        try {
            activity.startActivity(emailIntent);
        } catch (ActivityNotFoundException ignored) {
        }

    }

    public static Class getTypeClass(SubModel subModel) {

        if (subModel.TYPE_CODE == MainData.INPUT) {
            return InputActivity.class;
        }
        if (subModel.TYPE_CODE == MainData.TRUE_FALSE) {
            return TrueFalseActivity.class;
        }
        if (subModel.TYPE_CODE == MainData.FIND_MISSING) {
            return FindMissingActivity.class;
        } else {
            return QuizActivity.class;
        }
    }


    public static void share(Context context) {
        Intent sharingIntent1 = new Intent(Intent.ACTION_SEND);
        sharingIntent1.setType("text/plain");
        sharingIntent1.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        sharingIntent1.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + context.getPackageName() + System.getProperty("line.separator"));
        context.startActivity(Intent.createChooser(sharingIntent1, "Share via"));

    }


    public static void setDefaultLanguage(Activity activity) {
        Locale locale = new Locale(getLanguageCode(activity));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config,
                activity.getBaseContext().getResources().getDisplayMetrics());

        Log.e("s----", "" + getLanguageCode(activity));
        Constant.setDefaultTheme(activity);

    }


    public static List<HistoryModel> getHistoryModel(Context context) {
        List<HistoryModel> modelList = new ArrayList<>();

        for (int i = 0; i < getHistorySize(context); i++) {
            SharedPreferences sharedPreferences1 = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences1.getString(HISTORY + i, null);
            HistoryModel model = gson.fromJson(json, HistoryModel.class);
            if (model != null) {
                modelList.add(model);
            }
        }

        return modelList;
    }

    public static void addModel(String title, String tableName, int typeCode, Context context, List<HistoryModel> historyModels) {

        setHistorySize(context, historyModels.size());
        for (int i = 0; i < historyModels.size(); i++) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(historyModels.get(i));
            editor.putString(HISTORY + i, json);
            editor.apply();
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(tableName, null);
        Type type = new TypeToken<ReviewTestListModel>() {
        }.getType();
        ReviewTestListModel arrPackageData1 = gson.fromJson(json, type);
        List<ReviewTestModel> arrPackageData;


        if (arrPackageData1 == null) {
            arrPackageData1 = new ReviewTestListModel();
            arrPackageData = new ArrayList<>();
        } else {
            arrPackageData = arrPackageData1.reviewTestModels;
        }

        SubModel subModel = getSubModel(context);

        ReviewTestModel reviewTestModel = new ReviewTestModel();
        reviewTestModel.historyModels = historyModels;
        reviewTestModel.title = title;
        reviewTestModel.right_count = subModel.right_count;
        reviewTestModel.wrong_count = subModel.wrong_count;
        reviewTestModel.typeCode = typeCode;
        reviewTestModel.name = "Test " + arrPackageData.size() + 1;

        ReviewTestListModel reviewTestListModel = new ReviewTestListModel();
        reviewTestListModel.title = title;

        arrPackageData.add(reviewTestModel);
        reviewTestListModel.reviewTestModels = arrPackageData;

        json = gson.toJson(arrPackageData1);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(tableName, json);
        editor.apply();


    }


    public static ReviewTestListModel getReviewTestList(Activity context, String tableName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(tableName, null);
        Type type = new TypeToken<ReviewTestListModel>() {
        }.getType();

        return gson.fromJson(json, type);
    }


    public static String returnFormatNumber(double answer1) {
        return String.format(Locale.US, "%.2f", answer1);
    }


    public static void saveLearnModel(Activity activity, LearnModel learnModel) {

        SharedPreferences sharedPreferences = activity.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(learnModel);
        editor.putString(LEARN_MODEL, json);
        editor.apply();

    }


    public static void saveMainModel(Activity activity, MainModel mainModel) {

        SharedPreferences sharedPreferences = activity.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mainModel);
        editor.putString(MAIN_MODEL, json);
        editor.apply();

    }

    public static void saveSubModel(Activity activity, SubModel subModel) {

        SharedPreferences sharedPreferences = activity.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(subModel);
        editor.putString(SUB_MODEL, json);
        editor.apply();

    }


    public static void saveDuelModel(Activity activity, DualScoreModel dualScoreModel, String s) {

        SharedPreferences sharedPreferences = activity.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dualScoreModel);
        editor.putString(s, json);
        editor.apply();

    }

    public static void setDeviceLanguage(Activity activity) {

        setLanguageCode(activity, getDeviceLanguage(activity, Locale.getDefault().getLanguage()));
        Locale locale = new Locale(getLanguageCode(activity));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config,
                activity.getBaseContext().getResources().getDisplayMetrics());

        Constant.setDefaultTheme(activity);
        Constant.setIsFirstTime(activity);


    }

    public static String getDeviceLanguage(Context context, String s) {


        if (s.equalsIgnoreCase(context.getString(R.string.en_code))) {
            return context.getString(R.string.en_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.fr_code))) {
            return context.getString(R.string.fr_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.es_code))) {
            return context.getString(R.string.es_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.ar_code))) {
            return context.getString(R.string.ar_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.ru_code))) {
            return context.getString(R.string.ru_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.ms_code))) {
            return context.getString(R.string.ms_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.zh_code))) {
            return context.getString(R.string.zh_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.hi_code))) {
            return context.getString(R.string.hi_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.gu_code))) {
            return context.getString(R.string.gu_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.mr_code))) {
            return context.getString(R.string.mr_code);
        } else {
            return context.getString(R.string.en_code);
        }


    }


    public static void setDefaultTheme(Activity activity) {
        if (getNightMode(activity)) {
            activity.setTheme(R.style.DarkFullScreenWindow);
        } else {
            activity.setTheme(R.style.LightFullScreenWindow);
        }

        Resources.Theme theme = activity.getTheme();
        theme.applyStyle(Constant.getParentThemeList().get(Constant.getThemePosition(activity)), true);

    }


    public static String getLanguageCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LANGUAGEPREF, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LANGUAGE_CODE1, context.getString(R.string.en_code));
    }


    public static void setSound(Context context, boolean s) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SOUND, s);
        editor.apply();
    }

    public static void setVibrate(Context context, boolean s) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(VIBRATE, s);
        editor.apply();
    }

    public static void setExpandPosition(Context context, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(EXPANDPOSITION, position);
        editor.apply();

    }


    public static int getExpandPosition(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(EXPANDPOSITION, 0);
    }


    public static LearnModel getLearnModel(Context context) {
        SharedPreferences sharedPreferences1 = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences1.getString(LEARN_MODEL, null);
        return gson.fromJson(json, LearnModel.class);
    }


    public static MainModel getMainModel(Context context) {
        SharedPreferences sharedPreferences1 = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences1.getString(MAIN_MODEL, null);
        return gson.fromJson(json, MainModel.class);
    }

    public static SubModel getSubModel(Context context) {
        SharedPreferences sharedPreferences1 = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences1.getString(SUB_MODEL, null);
        return gson.fromJson(json, SubModel.class);
    }


    public static DualScoreModel getDuelModel(Context context, String s) {
        SharedPreferences sharedPreferences1 = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences1.getString(s, null);
        return gson.fromJson(json, DualScoreModel.class);
    }


    public static void setNightMode(Context context, boolean s) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NIGHT_MODE, s);
        editor.apply();
    }


    public static void setThemeColor(Context context, int themePosition) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(THEMEPOSITION, themePosition);
        editor.apply();
    }

    public static int getThemePosition(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        return sharedPreferences.getInt(THEMEPOSITION, 0);

    }


    public static void setLanguageCode(Context context, String s) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LANGUAGEPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LANGUAGE_CODE1, s);
        editor.apply();
    }

    public static void setIsReminder(Context context, boolean s) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ISReminder, s);
        editor.apply();
    }


    public static boolean getSound(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(SOUND, true);
    }

    public static boolean getVibrate(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(VIBRATE, true);
    }


    public static boolean getNightMode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(NIGHT_MODE, false);

    }

    public static boolean getIsReminder(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(ISReminder, true);
    }


    public static String getLanguageCodeFromLanguage(Context context, String s) {


        if (s.equalsIgnoreCase(context.getString(R.string.english))) {
            return context.getString(R.string.en_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.french))) {
            return context.getString(R.string.fr_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.spanish))) {
            return context.getString(R.string.es_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.arabic))) {
            return context.getString(R.string.ar_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.russian))) {
            return context.getString(R.string.ru_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.malay))) {
            return context.getString(R.string.ms_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.mandarin_chinese))) {
            return context.getString(R.string.zh_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.hindi))) {
            return context.getString(R.string.hi_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.gujrati))) {
            return context.getString(R.string.gu_code);
        } else if (s.equalsIgnoreCase(context.getString(R.string.marathi))) {
            return context.getString(R.string.mr_code);
        }


        return null;
    }


    public static List<String> getLanguageList(Context context) {
        List<String> languageList = new ArrayList<>();

        languageList.add(context.getString(R.string.english));
        languageList.add(context.getString(R.string.spanish));
        languageList.add(context.getString(R.string.arabic));
        languageList.add(context.getString(R.string.malay));
        languageList.add(context.getString(R.string.russian));
        languageList.add(context.getString(R.string.french));
        languageList.add(context.getString(R.string.mandarin_chinese));
        languageList.add(context.getString(R.string.hindi));
        languageList.add(context.getString(R.string.marathi));
        languageList.add(context.getString(R.string.gujrati));


        return languageList;
    }


    public static Bitmap getBitmapFromAsset(Context activity, String strName) {
        AssetManager assetManager = activity.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(istr);
    }

    public static int getStarCount(int progress) {
        if (progress == 0) {
            return 0;
        } else {
            if (progress < 50) {
                return 1;
            } else if (progress < 90 && progress > 50) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    private static void setHistorySize(Context context, int history_size) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(HISTORY_SIZE, history_size);
        editor.apply();
        editor.apply();
    }

    private static int getHistorySize(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(HISTORY_SIZE, 0);
    }


    public static void setCoins(Context context, int coins) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COINS, coins);
        editor.apply();
        editor.apply();
    }

    public static int getCoins(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(COINS, 0);
    }


    public static boolean getIsFirstTime(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IsFirstTime, true);
    }


    public static void setIsFirstTime(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IsFirstTime, false);
        editor.apply();
    }


    public static GradientDrawable customViewOval(int colors) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(colors);
        shape.setShape(GradientDrawable.OVAL);
        return shape;
    }

    public static GradientDrawable customPrimaryViewOval(Activity activity) {

        int[] color = new int[]{Constant.getThemeColor(activity, R.attr.colorPrimary), Constant.getThemeColor(activity, R.attr.colorPrimaryDark)};

        GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, color);
        shape.setShape(GradientDrawable.OVAL);
        return shape;
    }


    public static int getThemeColor(Activity activity, int color) {

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(color, typedValue, true);
        return typedValue.data;

    }


    public static List<Integer> getThemeList() {
        List<Integer> themeList = new ArrayList<>();

        themeList.add(R.color.colorPrimary);            // theme position 0
        themeList.add(R.color.deepPurpleColorPrimary);  // theme position 1
        themeList.add(R.color.blueColorPrimary);        // theme position 2
        themeList.add(R.color.pinkColorPrimary);        // theme position 3
        themeList.add(R.color.orangeColorPrimary);      // theme position 4
        themeList.add(R.color.greenColorPrimary);       // theme position 5
        themeList.add(R.color.grayColorPrimary);        // theme position 6
        return themeList;
    }


    public static ArrayList<Integer> getParentThemeList() {
        ArrayList<Integer> themeList = new ArrayList<>();

        themeList.add(R.style.PrimaryTheme);
        themeList.add(R.style.DeepPurplePrimaryTheme);
        themeList.add(R.style.BluePrimaryTheme);
        themeList.add(R.style.PinkPrimaryTheme);
        themeList.add(R.style.OrangePrimaryTheme);
        themeList.add(R.style.GreenPrimaryTheme);
        themeList.add(R.style.GrayPrimaryTheme);
        return themeList;
    }


    public static String getAllTranslatedDigit(String s) {
        return s;
    }


}

