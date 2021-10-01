package com.elmuj.learnmath.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.elmuj.learnmath.R;
import com.elmuj.learnmath.utils.Constant;

import static com.elmuj.learnmath.ui.MainActivity.rate;
import static com.elmuj.learnmath.utils.Constant.sendFeedback;


public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.setDefaultLanguage(this);
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
            case R.id.menu_test:
                startActivity(new Intent(this, AllReviewTestActivity.class));
                return true;
            case R.id.menu_share:
                Constant.share(BaseActivity.this);
                return true;

            case R.id.menu_rate:
                rate(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }







}