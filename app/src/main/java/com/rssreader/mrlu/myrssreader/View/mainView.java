package com.rssreader.mrlu.myrssreader.View;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.rssreader.mrlu.myrssreader.R;
import com.rssreader.mrlu.myrssreader.View.CustomView.NoScrollViewPager;
import com.rssreader.mrlu.myrssreader.View.fragment.starredFragment;
import com.rssreader.mrlu.myrssreader.View.fragment.unReadFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class mainView extends FragmentActivity implements View.OnClickListener {




    //声明ViewPager
    private NoScrollViewPager mNoScrollViewPager;
    //适配器
    private FragmentPagerAdapter mAdapter;
    //装载Fragment的集合
    private List<Fragment> mFragments;

    //四个Tab对应的布局
    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;

    //四个Tab对应的ImageButton
    private ImageButton mImgWeixin;
    private ImageButton mImgFrd;


    String rssUrl;

    Window window;

    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)

//    float x1 = 0;
//    float x2 = 0;

    float y1 = 0;
    float y2 = 0;

    private View mLayFrame;
    SwipeMenuListView mSwipeMenuListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        ButterKnife.bind(this);

        window = getWindow();

//        final Window window = getWindow();

        mLayFrame = findViewById(R.id.id_noviewpager);
//
//        mSwipeMenuListView = (SwipeMenuListView) layFrame.findViewById(R.id.lv_rssList);
//
//        mLayFrame.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        y1 = event.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        y2 = event.getY();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        y2 = event.getY();
//                        break;
//                    default:
//                        break;
//                }
//
//                if (y1 - y2 >= 40 && y2 != 0) {
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                        //透明状态栏
//                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                        //透明导航栏
//                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//
//                        View layBottom = findViewById(R.id.lay_bottom);
//
//
//                        layBottom.setVisibility(View.GONE);
//                    }
//
//                }
//
//                return true;
//
//            }
//        });


//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        //取出带来的rssLink
        Bundle bundle = this.getIntent().getExtras();
        rssUrl = bundle.getString("rssLink");

        Log.i("传递值打印mainView", rssUrl);

        initViews();//初始化控件

        initEvents();//初始化事件

        initDatas();//初始化数据

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        selectTab(0);

    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                downX = (int) ev.getX();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int deltaX = (int) ( ev.getX()- downX);
//
//                if(Math.abs(deltaX)>8){
//                    return true;
//                }
//                break;
//        }
//        return super.onInterceptTouchEvent(ev);
////        return super.onInterceptTouchEvent(ev);
//    }

    //    处理触摸事件，滑动全屏
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        float startY = 0;
//        float endY = 0;
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                startY = event.getY();
//                Log.i("触摸事件", "位置为：" + startY);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                endY = endY + event.getY();
//                Log.i("触摸事件", "位置为：" + endY);
//
//                if (startY - endY >= (float) 40 && endY != 0) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//                        //透明状态栏
//                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                        //透明导航栏
//                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//
//                        //隐藏tab栏
//                        View layBottom = findViewById(R.id.lay_bottom);
//                        layBottom.setVisibility(View.GONE);
//                    }
//                }
//
//                break;
//            case MotionEvent.ACTION_UP:
//
//                break;
//        }
//
//        return true;
//    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //继承了Activity的onTouchEvent方法，直接监听点击事件
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            //当手指按下的时候
//            x1 = event.getX();
//            y1 = event.getY();
//        }
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            //当手指离开的时候
//            x2 = event.getX();
//            y2 = event.getY();
//            if (y1 - y2 > 50) {
//                Toast.makeText(mainView.this, "向上滑", Toast.LENGTH_SHORT).show();
//            } else if (y2 - y1 > 50) {
//                Toast.makeText(mainView.this, "向下滑", Toast.LENGTH_SHORT).show();
//            } else if (x1 - x2 > 50) {
//                Toast.makeText(mainView.this, "向左滑", Toast.LENGTH_SHORT).show();
//            } else if (x2 - x1 > 50) {
//                Toast.makeText(mainView.this, "向右滑", Toast.LENGTH_SHORT).show();
//            }
//        }
//        return super.onTouchEvent(event);
//    }


    private void initDatas() {
        mFragments = new ArrayList<>();
        //将四个Fragment加入集合中
        mFragments.add(new unReadFragment());
        mFragments.add(new starredFragment());

        //初始化适配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {//从集合中获取对应位置的Fragment
                return mFragments.get(position);
            }

            @Override
            public int getCount() {//获取集合中Fragment的总数
                return mFragments.size();
            }

        };

        //不要忘记设置ViewPager的适配器
        mNoScrollViewPager.setAdapter(mAdapter);
        //设置ViewPager的切换监听
        mNoScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                mNoScrollViewPager.setCurrentItem(position);
                resetImgs();
                selectTab(position);
            }

            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvents() {
        //设置四个Tab的点击事件
        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);

    }

    //初始化控件
    private void initViews() {
        mNoScrollViewPager = (NoScrollViewPager) findViewById(R.id.id_noviewpager);

        mTabWeixin = (LinearLayout) findViewById(R.id.id_tab_weixin);
        mTabFrd = (LinearLayout) findViewById(R.id.id_tab_frd);

        mImgWeixin = (ImageButton) findViewById(R.id.id_tab_weixin_img);
        mImgFrd = (ImageButton) findViewById(R.id.id_tab_frd_img);

    }

    @Override
    public void onClick(View v) {
        //先将四个ImageButton置为灰色
        resetImgs();

        //根据点击的Tab切换不同的页面及设置对应的ImageButton为绿色
        switch (v.getId()) {
            case R.id.id_tab_weixin:
                selectTab(0);
                break;
            case R.id.id_tab_frd:
                selectTab(1);
                break;
        }
    }

    private void selectTab(int i) {
        //根据点击的Tab设置对应的ImageButton为绿色
        switch (i) {
            case 0:
                //mImgWeixin.setImageResource(R.drawable.feed_read);
                mTabWeixin.setBackgroundColor(Color.parseColor("#00acc1"));
                break;
            case 1:
                //EventBus发送消息
                EventBus.getDefault().post(rssUrl);

                //mImgFrd.setImageResource(R.drawable.long_press_starred);
                mTabFrd.setBackgroundColor(Color.parseColor("#fd4181"));
                break;
        }
        //设置当前点击的Tab所对应的页面
        mNoScrollViewPager.setCurrentItem(i);
    }

    //将四个ImageButton设置为灰色
    private void resetImgs() {
        mTabFrd.setBackgroundColor(Color.parseColor("#393a3f"));
        mTabWeixin.setBackgroundColor(Color.parseColor("#393a3f"));

    }


}


