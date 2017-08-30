package com.rssreader.mrlu.myrssreader.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jaeger.library.StatusBarUtil;
import com.rssreader.mrlu.myrssreader.R;

public class openScreenActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.FullscreenTheme);
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.md_grey_50_color_code), 0);
        setContentView(R.layout.activity_open_screen);



        //从sp文件中取出isHasFeed,判断是否显示初始页
        SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);

        if (false) {

            boolean isEnterAppearPages = sharedPreferences.getBoolean("isEnterAppearPages", false);

            //从sp文件中取出isHasFeed,判断是否显示初始页
            boolean isLoadLoginPage = sharedPreferences.getBoolean("isLoadLoginPage", false);

            if (!isEnterAppearPages) {

                //如果数据库存在feed，直接跳转到mainView
                if (isLoadLoginPage) {

                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            SystemClock.sleep(1500);

                            Log.i("过程打印", "存在feed，跳转到mainview");

                            Intent intent = new Intent(openScreenActivity.this, mainView.class);

                            startActivity(intent);
                            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                            finish();

                        }
                    }).start();

                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            SystemClock.sleep(1500);

                            Intent intent = new Intent(openScreenActivity.this, LoginActivity.class);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
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
                SystemClock.sleep(1500);
                Intent intent = new Intent(openScreenActivity.this, AppearPageActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        }).start();
    }
}
