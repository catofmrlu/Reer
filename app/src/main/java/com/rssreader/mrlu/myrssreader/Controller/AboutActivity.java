package com.rssreader.mrlu.myrssreader.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.rssreader.mrlu.myrssreader.R;

import static com.rssreader.mrlu.myrssreader.R.color.appBaseColor;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        StatusBarUtil.setColor(this, getResources().getColor(appBaseColor), 0);

    }

}
