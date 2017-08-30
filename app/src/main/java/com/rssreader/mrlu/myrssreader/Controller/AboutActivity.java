package com.rssreader.mrlu.myrssreader.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;
import com.rssreader.mrlu.myrssreader.R;

import static com.rssreader.mrlu.myrssreader.R.color.appBaseColor;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, getResources().getColor(appBaseColor), 0);
        setContentView(R.layout.activity_about);
    }

}
