package com.rssreader.mrlu.myrssreader.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.rssreader.mrlu.myrssreader.R;

public class openScreenActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.FullscreenTheme);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_open_screen);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);

                Intent intent = new Intent(openScreenActivity.this, LoginActivity.class);

                startActivity(intent);

                finish();
            }
        }).start();




    }
}
