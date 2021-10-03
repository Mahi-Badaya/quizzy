package com.elmuj.learnmath.utils;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.elmuj.learnmath.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

import static com.elmuj.learnmath.ui.ReviewTestActivity.SAVED_REVIEW_ANSWER;
import static com.elmuj.learnmath.utils.Constant.setDefaultLanguage;

public class ConstantDialog {

    public static void showVideoDialogs(Activity activity, AdVideoInterface adVideoInterface) {

        setDefaultLanguage(activity);
        final ConnectionDetector[] connectionDetector = {new ConnectionDetector(activity)};
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_video, null);
        builder.setView(view);
        builder.setCancelable(false);
        TextView btn_show_video = view.findViewById(R.id.btn_show_video);
        TextView btn_cancel = view.findViewById(R.id.btn_cancel);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        btn_cancel.setOnClickListener(v -> {
            dialog.dismiss();
            if (adVideoInterface != null) {
                adVideoInterface.cancelClick();
            }

        });
        btn_show_video.setOnClickListener(v -> {


            if (connectionDetector[0].isConnectingToInternet()) {

                if (adVideoInterface != null) {
                    adVideoInterface.showVideoClick(dialog);
                }
            } else {
                Toast.makeText(activity, "" + activity.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

        });
    }


    public static void showGetLivesDialogs(Activity activity, AdVideoInterface adVideoInterface) {
        setDefaultLanguage(activity);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_get_lives, null);
        builder.setView(view);

        TextView btn_ok = view.findViewById(R.id.btn_ok);

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        btn_ok.setOnClickListener(v -> {
            dialog.dismiss();


            if (adVideoInterface != null) {
                adVideoInterface.getLivesClick();
            }


        });
    }


    public static void showExitDialog(Activity activity, ExitInterface exitInterface) {

        setDefaultLanguage(activity);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_exit_quiz, null);
        builder.setView(view);

        TextView btn_yes = view.findViewById(R.id.btn_yes);
        TextView btn_no = view.findViewById(R.id.btn_no);

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        btn_yes.setOnClickListener(v -> {


            if (exitInterface != null) {
                exitInterface.onExit();
            }

            dialog.dismiss();
        });


        btn_no.setOnClickListener(v -> {

            if (exitInterface != null) {
                exitInterface.onNo();
            }
            dialog.dismiss();
        });

    }


    public static Bitmap combineImages(Activity activity, Bitmap c, Bitmap s) {
        Bitmap cs;

        int width, height;

        width = c.getWidth();
        height = c.getHeight() + s.getHeight() + 75;

        int space = (c.getWidth() - s.getWidth()) / 2;


        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(width, 50, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Constant.getThemeColor(activity, R.attr.background));

        Canvas comboImage = new Canvas(cs);

        comboImage.drawColor(Constant.getThemeColor(activity, R.attr.background));

        comboImage.drawBitmap(bitmap, 0f, 0f, null);
        comboImage.drawBitmap(c, 0f, 50, null);
        comboImage.drawBitmap(s, space, c.getHeight(), null);
        comboImage.drawBitmap(bitmap, 0f, (s.getHeight() + c.getHeight() + bitmap.getHeight()), null);


        return cs;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    public static String addWatermark(Activity activity, Bitmap source) {

        Bitmap waterMark = drawableToBitmap(activity.getResources().getDrawable(R.mipmap.ic_launcher));

        waterMark = Bitmap.createScaledBitmap(waterMark, 80, 80, true);

//         waterMark = RotateBitmap(waterMark,180);

        Paint alphaPaint = new Paint();
        alphaPaint.setAlpha(70);
        Canvas comboImage = new Canvas(source);
        int centreX = (source.getWidth() / 2);

        int centreY = source.getHeight() / 2;


        comboImage.drawBitmap(waterMark, centreX, centreY, alphaPaint);


        File root = activity.getExternalFilesDir(null);
        assert root != null;
        String path1 = root.getAbsolutePath() + SAVED_REVIEW_ANSWER;


        File dir = new File(path1);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        OutputStream output;

        File saveFile1 = new File(dir, System.currentTimeMillis() + ".jpg");

        if (saveFile1.exists()) {
            saveFile1.delete();
        }


        String scrollViewPath = saveFile1.getAbsolutePath();
        try {
            output = new FileOutputStream(saveFile1);
            source.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.parse("file://"
                + Environment.getExternalStorageDirectory());
        mediaScanIntent.setData(contentUri);
        activity.sendBroadcast(mediaScanIntent);
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(saveFile1.getAbsolutePath()))));


        return scrollViewPath;
    }


}
