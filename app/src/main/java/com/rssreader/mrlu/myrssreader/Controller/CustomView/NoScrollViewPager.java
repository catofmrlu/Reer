package com.rssreader.mrlu.myrssreader.Controller.CustomView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

/**
 * Created by LuXin on 2017/3/11.
 */

public class NoScrollViewPager  extends ViewPager{


    private boolean isCanScroll = true;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public void setIsScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if(isCanScroll){
            return super.onInterceptTouchEvent(arg0);
        }else{
            //false  不能左右滑动
            return false;
        }
    }
}
