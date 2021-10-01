package com.elmuj.learnmath.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.elmuj.learnmath.R;
import com.elmuj.learnmath.adapter.HistoryAdapter;
import com.elmuj.learnmath.model.HistoryModel;
import com.elmuj.learnmath.model.SubModel;
import com.elmuj.learnmath.utils.CenteredToolbar;
import com.elmuj.learnmath.utils.Constant;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import static com.elmuj.learnmath.ui.MainActivity.rate;
import static com.elmuj.learnmath.utils.Constant.sendFeedback;
import static com.elmuj.learnmath.utils.ConstantDialog.addWatermark;
import static com.elmuj.learnmath.utils.ConstantDialog.combineImages;

public class ReviewAnswerActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    String scrollViewPath;
    Button btn_view_answer;
    RelativeLayout header;
    TextView tv_total_set;
    TextView tv_right_count, tv_wrong_count;
    List<HistoryModel> historyNotesList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.setDefaultLanguage(this);
        setContentView(R.layout.activity_review_answer);
        init();


    }

    public String getTranslatedString(String s) {
        return Constant.getAllTranslatedDigit(s);
    }


    public static Bitmap getRecyclerViewScreenshot(RecyclerView view) {
        int size = view.getAdapter().getItemCount();
        RecyclerView.ViewHolder holder = view.getAdapter().createViewHolder(view, 0);
        view.getAdapter().onBindViewHolder(holder, 0);
        holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
        Bitmap bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), holder.itemView.getMeasuredHeight() * size,
                Bitmap.Config.ARGB_8888);
        Canvas bigCanvas = new Canvas(bigBitmap);
        bigCanvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        int iHeight = 0;
        holder.itemView.setDrawingCacheEnabled(true);
        holder.itemView.buildDrawingCache();
        bigCanvas.drawBitmap(holder.itemView.getDrawingCache(), 0f, iHeight, paint);
        holder.itemView.setDrawingCacheEnabled(false);
        holder.itemView.destroyDrawingCache();
        iHeight += holder.itemView.getMeasuredHeight();
        for (int i = 1; i < size; i++) {
            view.getAdapter().onBindViewHolder(holder, i);
            holder.itemView.setDrawingCacheEnabled(true);
            holder.itemView.buildDrawingCache();
            bigCanvas.drawBitmap(holder.itemView.getDrawingCache(), 0f, iHeight, paint);
            iHeight += holder.itemView.getMeasuredHeight();
            holder.itemView.setDrawingCacheEnabled(false);
            holder.itemView.destroyDrawingCache();
        }
        return bigBitmap;
    }


    public boolean checkPermission(Context context) {
        int i3 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int i4 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return i3 == PackageManager.PERMISSION_GRANTED
                && i4 == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 123);
    }


    private Bitmap captureHeaderImage() {
        // TODO Auto-generated method stub


        Bitmap bitmap = Bitmap.createBitmap(header.getWidth(), header.getHeight(),
                Bitmap.Config.ARGB_8888);


        Canvas b = new Canvas(bitmap);
        header.draw(b);


        return bitmap;
    }


    private void init() {
        SubModel subModel = Constant.getSubModel(this);

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> backIntent());
        getSupportActionBar().setTitle(getString(R.string.view_answer));

        tv_total_set = findViewById(R.id.tv_total_set);


        tv_right_count = findViewById(R.id.tv_right_count);
        tv_right_count.setText(getTranslatedString(String.valueOf(subModel.right_count)));

        header = findViewById(R.id.header);
        btn_view_answer = findViewById(R.id.btn_view_answer);
        tv_wrong_count = findViewById(R.id.tv_wrong_count);
        tv_wrong_count.setText(getTranslatedString(String.valueOf(subModel.wrong_count)));


        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        historyNotesList.add(new HistoryModel(0, getString(R.string.str_question), getString(R.string.answer), getString(R.string.your_answer)));

        new SetReviewAnswer().execute();


        btn_view_answer.setOnClickListener(v -> {
            if (!checkPermission(getApplicationContext())) {
                requestPermission();
            } else {
                new DownloadcaptureScrollViewTask().execute();
            }
        });


    }


    @SuppressLint("StaticFieldLeak")
    public class DownloadcaptureScrollViewTask extends AsyncTask<String, Integer, Bitmap> {


        protected void onPreExecute() {

        }

        protected Bitmap doInBackground(String... urls) {


            scrollViewPath = addWatermark(ReviewAnswerActivity.this, combineImages(ReviewAnswerActivity.this, captureHeaderImage(), getRecyclerViewScreenshot(recyclerView)));
            return null;
        }


        protected void onPostExecute(Bitmap result) {
            shareImage();

        }
    }


    public class SetReviewAnswer extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            historyNotesList.addAll(Constant.getHistoryModel(getApplicationContext()));

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            HistoryAdapter historyAdapter = new HistoryAdapter(ReviewAnswerActivity.this, historyNotesList);
            recyclerView.setAdapter(historyAdapter);
            tv_total_set.setText(getTranslatedString(String.valueOf(historyNotesList.size() - 1)));
        }
    }


    public void backIntent() {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_feedback:
                sendFeedback(this,null);
                return true;
            case R.id.menu_share:
                if (!checkPermission(getApplicationContext())) {
                    requestPermission();
                } else {
                    new DownloadcaptureScrollViewTask().execute();
                }
                return true;

            case R.id.menu_rate:
                rate(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new DownloadcaptureScrollViewTask().execute();
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




    public void shareImage() {
        File file1 = new File(scrollViewPath);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/png");
        Uri photoURI;
        photoURI = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", file1);
        share.putExtra(Intent.EXTRA_STREAM, photoURI);
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName() + System.getProperty("line.separator"));
        startActivity(Intent.createChooser(share, getString(R.string.share_image)));
    }


}
