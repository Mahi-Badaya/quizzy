package com.elmuj.learnmath.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.elmuj.learnmath.R;
import com.elmuj.learnmath.adapter.LevelAdapter;
import com.elmuj.learnmath.database.DatabaseAccess;
import com.elmuj.learnmath.model.MainModel;

import com.elmuj.learnmath.model.ProgressModel;
import com.elmuj.learnmath.model.SubModel;
import com.elmuj.learnmath.utils.CenteredToolbar;
import com.elmuj.learnmath.utils.Constant;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import static com.elmuj.learnmath.ui.MainActivity.rate;
import static com.elmuj.learnmath.utils.Constant.sendFeedback;
import static com.elmuj.learnmath.utils.Constant.share;


public class LevelActivity extends AppCompatActivity implements LevelAdapter.ItemClick {

    RecyclerView recyclerView;
    ImageView btn_drawer;
    TextView text_header;
    ProgressDialog progressDialog;
    RelativeLayout layout_cell;
    TextView tv_total_set, tv_total_question;
    View view;
    AdView adView;
    MainModel mainModel;
    SubModel subModel;
    DatabaseAccess databaseAccess;
    List<ProgressModel> progressModelLis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.setDefaultLanguage(this);
        setContentView(R.layout.activity_level);
        init();
        adview();
    }

    private void adview() {
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void init() {


        subModel = Constant.getSubModel(this);
        mainModel = Constant.getMainModel(this);
        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> backIntent());
        getSupportActionBar().setTitle(null);
        progressDialog = new ProgressDialog(this);


        btn_drawer = findViewById(R.id.btn_drawer);
        text_header = findViewById(R.id.text_header);
        layout_cell = findViewById(R.id.layout_cell);
        tv_total_set = findViewById(R.id.tv_total_set);
        tv_total_question = findViewById(R.id.tv_total_question);
        view = findViewById(R.id.view);


        text_header.setText(mainModel.title);

        getSupportActionBar().setTitle(subModel.title);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        databaseAccess = DatabaseAccess.getInstance(LevelActivity.this);
        databaseAccess.open();
        int size = databaseAccess.getProgressLevels(mainModel.tableName, subModel.type).size();
        databaseAccess.close();


        Log.e("size==", "" + size);

        if (size > 0) {
            setAdapter();
        } else {
            new AddData().execute();
        }


    }


    public class AddData extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            progressDialog.setMessage(getString(R.string.please_wait));
        }

        @Override
        protected String doInBackground(Void... voids) {


            for (int i = 0; i < Constant.DEFAULT_LEVEL; i++) {
                databaseAccess = DatabaseAccess.getInstance(LevelActivity.this);
                databaseAccess.open();
                Log.e("databaseAccess", "" + databaseAccess.insertProgressData(new ProgressModel(mainModel.tableName, subModel.type, (i + 1), 0)));
                databaseAccess.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            databaseAccess = DatabaseAccess.getInstance(LevelActivity.this);
            databaseAccess.open();
            int size = databaseAccess.getProgressLevels(mainModel.tableName, subModel.type).size();
            databaseAccess.close();


            Log.e("size==", "" + size);


            setAdapter();
        }
    }


    public void setAdapter() {


        databaseAccess = DatabaseAccess.getInstance(LevelActivity.this);
        databaseAccess.open();
        progressModelLis = databaseAccess.getProgressShowLevel(mainModel.tableName, subModel.type);
        databaseAccess.close();


        LevelAdapter levelAdapter = new LevelAdapter(this, progressModelLis);
        recyclerView.setAdapter(levelAdapter);
        levelAdapter.setClickListener(this);
        tv_total_set.setText(Constant.getAllTranslatedDigit(String.valueOf(Constant.DEFAULT_LEVEL)));
        tv_total_question.setText(Constant.getAllTranslatedDigit(String.valueOf((Constant.DEFAULT_LEVEL * Constant.DEFAULT_QUESTION))));


    }


    @Override
    public void itemClick(int position) {
        subModel.level_no = (position);
        Constant.saveSubModel(this, subModel);
        startActivity(new Intent(this, Constant.getTypeClass(subModel)));

    }

    public void backIntent() {
        startActivity(new Intent(this, MainActivity.class));
    }


    @Override
    public void onBackPressed() {
        backIntent();
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
                sendFeedback(this, null);
                return true;
            case R.id.menu_test:
                startActivity(new Intent(this, AllReviewTestActivity.class));
                return true;
            case R.id.menu_share:
                share(this);
                return true;
            case R.id.menu_rate:
                rate(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem register = menu.findItem(R.id.menu_test);
        register.setVisible(true);
        return true;
    }


}
