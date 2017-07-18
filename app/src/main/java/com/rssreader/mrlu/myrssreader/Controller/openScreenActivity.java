package com.rssreader.mrlu.myrssreader.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rssreader.mrlu.myrssreader.R;

import butterknife.ButterKnife;

public class openScreenActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.FullscreenTheme);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_open_screen);
        ButterKnife.bind(this);

        //从sp文件中取出isHasFeed,判断是否显示初始页
        SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);

        if (sharedPreferences != null) {

            boolean isEnterAppearPages = sharedPreferences.getBoolean("isEnterAppearPages", false);

            //从sp文件中取出isHasFeed,判断是否显示初始页
            boolean isHasFeed = sharedPreferences.getBoolean("isHasFeed", false);




            if (isEnterAppearPages) {

                //如果数据库存在feed，直接跳转到mainView
                if (isHasFeed) {
                    Log.i("过程打印", "存在feed，跳转到mainview");

                    Intent intent = new Intent(this, mainView.class);

                    Bundle bundle = new Bundle();


                    startActivity(intent);
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            SystemClock.sleep(3000);

                            Intent intent = new Intent(openScreenActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).start();
                }


            } else {
                Log.i("首次跳转", "跳到引导页1");
                skipAppearPageActivity();
            }
        } else {
            Log.i("首次跳转", "跳到引导页2");

            skipAppearPageActivity();
        }
    }

    private void skipAppearPageActivity() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                Intent intent = new Intent(openScreenActivity.this, AppearPageActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }
}
