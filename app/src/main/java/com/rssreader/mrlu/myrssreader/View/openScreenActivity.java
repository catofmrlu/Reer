package com.rssreader.mrlu.myrssreader.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        //从sp文件中取出isHasFeed,判断是否显示初始页
        SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
        boolean isHasFeed = sharedPreferences.getBoolean("isHasFeed", false);

        if (isHasFeed) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(2000);

                    Intent intent = new Intent(openScreenActivity.this, LoginActivity.class);

                    startActivity(intent);

                    finish();
                }
            }

            ).start();

        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(2000);

                    Intent intent = new Intent(openScreenActivity.this, AppearPageActivity.class);

                    startActivity(intent);

                    finish();
                }

            }).start();
        }
    }
}
