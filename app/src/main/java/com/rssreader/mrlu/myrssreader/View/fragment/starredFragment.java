package com.rssreader.mrlu.myrssreader.View.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rssreader.mrlu.myrssreader.R;

/**
 * Created by LuXin on 2017/2/26.
 */

public class starredFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        //判断是否有标记项，来处理加载不同的view
        view= inflater.inflate(R.layout.black_starred, container, false);
        return view;
    }
}
