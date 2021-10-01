package com.elmuj.learnmath.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elmuj.learnmath.R;

import com.elmuj.learnmath.adapter.ReivewTestListAdapter;
import com.elmuj.learnmath.model.MainModel;
import com.elmuj.learnmath.model.ReviewTestModel;

import com.elmuj.learnmath.utils.CenteredToolbar;
import com.elmuj.learnmath.utils.Constant;

import java.util.ArrayList;

import java.util.List;


public class AllReviewTestActivity extends AppCompatActivity implements ReivewTestListAdapter.ItemClick {

    ReivewTestListAdapter reivewTestListAdapter;
    TextView textView;
    RecyclerView recyclerView;
    List<ReviewTestModel> reviewTestTypeModels = new ArrayList<>();
    List<ReviewTestModel> reviewTestModels = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.setDefaultLanguage(this);
        setContentView(R.layout.activity_all_pdf);
        init();
    }


    private void init() {
        progressDialog = new ProgressDialog(this);

        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        toolbar.setNavigationOnClickListener(view -> backIntent());

        getSupportActionBar().setTitle(getString(R.string.review_test));

        setAdapter();

    }

    public void setAdapter() {
        textView = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        MainModel mainModel = Constant.getMainModel(this);


        if (Constant.getReviewTestList(this, mainModel.tableName) != null) {
            reviewTestModels = Constant.getReviewTestList(this, mainModel.tableName).reviewTestModels;

            if (reviewTestModels != null) {

                if (reviewTestModels.size() > 0) {
                    textView.setVisibility(View.GONE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                }


                new SetListAdapter().execute();


            } else {
                textView.setVisibility(View.VISIBLE);
            }
        }


    }

    public class SetListAdapter extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            progressDialog.setMessage(getString(R.string.please_wait));
        }

        @Override
        protected String doInBackground(Void... voids) {


            for (int i = 0; i < reviewTestModels.size(); i++) {
                Log.e("typeCode===", "" + reviewTestModels.get(i).typeCode + "===" + Constant.getSubModel(AllReviewTestActivity.this).TYPE_CODE);
                if (reviewTestModels.get(i).typeCode == Constant.getSubModel(AllReviewTestActivity.this).TYPE_CODE) {
                    reviewTestTypeModels.add(reviewTestModels.get(i));
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            reivewTestListAdapter = new ReivewTestListAdapter(AllReviewTestActivity.this, reviewTestTypeModels);
            recyclerView.setAdapter(reivewTestListAdapter);
            reivewTestListAdapter.setListener(AllReviewTestActivity.this);

        }
    }


    @Override
    public void onBackPressed() {
        backIntent();
    }

    public void backIntent() {

        Intent intent = new Intent(this, LevelActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }


    @Override
    public void itemClick(int position) {

        Intent intent = new Intent(AllReviewTestActivity.this, ReviewTestActivity.class);
        intent.putExtra(Constant.POSITION, position);
        startActivity(intent);
    }


}
