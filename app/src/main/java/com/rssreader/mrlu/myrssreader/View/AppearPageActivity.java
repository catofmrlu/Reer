package com.rssreader.mrlu.myrssreader.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rssreader.mrlu.myrssreader.Model.Sqlite.RssSqliteHelper;
import com.rssreader.mrlu.myrssreader.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AppearPageActivity extends AppCompatActivity {

    RssSqliteHelper mSqlHelper;

    @BindView(R.id.vp_appear)
    ViewPager vpAppear;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    private CirclePageIndicator circlePageIndicator;
    //    viewpager mpagerAdapter;
    private List<View> views = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appear_page);
        ButterKnife.bind(this);


        try {

            circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
            LayoutInflater minflater = LayoutInflater.from(this);
            View view1 = minflater.inflate(R.layout.appearpage_1, null);
            View view2 = minflater.inflate(R.layout.appearpage_2, null);
            View view3 = minflater.inflate(R.layout.appearpage_3, null);
            views.add(view1);
            views.add(view2);
            views.add(view3);
            MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(views);

            vpAppear.setAdapter(myViewPagerAdapter);
            circlePageIndicator.setViewPager(vpAppear);

            Log.i("过程打印", "viewpager装载完成");

//            //        创建数据库及数据表
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                    try {
//
//                        mSqlHelper = new RssSqliteHelper(AppearPageActivity.this, "Rss", null, 1);
//                        mSqlHelper.getWritableDatabase();
//
//                    } catch (Exception e) {
//                        Log.e("database", "问题在：" + e.toString());
//                    }
//
//                }
//            }).start();

            Button button = (Button) view3.findViewById(R.id.btn_appear);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(AppearPageActivity.this, LoginActivity.class);
                    startActivity(intent);

                    SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器

                    editor.putBoolean("isEnterAppearPages", true);

                    editor.commit();//提交修改

                    Log.i("过程打印", "以后不会出现引导页");

                    finish();
                }
            });

        } catch (Exception e) {
            Log.e("pager异常", e.getMessage());
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {


        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));//删除页卡
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(mListViews.get(position), 0);//添加页卡
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();//返回页卡的数量
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;//官方提示这样写
        }
    }
}

