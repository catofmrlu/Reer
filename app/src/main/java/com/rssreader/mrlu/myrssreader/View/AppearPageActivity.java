package com.rssreader.mrlu.myrssreader.View;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.rssreader.mrlu.myrssreader.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;


public class AppearPageActivity extends AppCompatActivity {


    private CirclePageIndicator circlePageIndicator;
    ViewPager viewPager;
//    viewpager mpagerAdapter;
    private List<View> views= new ArrayList<View>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appear_page);

            viewPager= (ViewPager) findViewById(R.id.vp_appear);
            circlePageIndicator= (CirclePageIndicator) findViewById(R.id.indicator);
            LayoutInflater minflater=LayoutInflater.from(this);
            View view1=minflater.inflate(R.layout.appearpage_1,null);
            View view2=minflater.inflate(R.layout.appearpage_2,null);
            View view3=minflater.inflate(R.layout.appearpage_3,null);
            views.add(view1);
            views.add(view2);
            views.add(view3);
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return false;
            }
        };

            viewPager.setAdapter(adapter);
            circlePageIndicator.setViewPager(viewPager);


        }
    }

