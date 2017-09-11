package com.rssreader.mrlu.myrssreader.Test;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.rssreader.mrlu.myrssreader.Controller.InputRssLinkActivity;
import com.rssreader.mrlu.myrssreader.Controller.ListActivity;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;
import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
import com.rssreader.mrlu.myrssreader.R;

import org.xml.sax.InputSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LuXin on 2017/2/26.
 */

public class unReadFragment11 extends Fragment implements AdapterView.OnItemClickListener {

    public String tag = "RSSReader";
    private RSSFeed feed = null;
    InputSource isc;
    private RequestQueue mRequestQueue;
    private SimpleAdapter adapter;
    private List<Map<String, String>> mRssUnreadList;
    private SQLiteHandle mSqLiteHandle;
    public String rssItemCount = "0";

    View view;
    ListView itemlist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mSqLiteHandle = new SQLiteHandle(getActivity());
        Cursor cursor = mSqLiteHandle.queryAllFeeds();

        if (cursor != null) {
            Log.i("过程打印", "存在Feed");

            view = inflater.inflate(R.layout.activity_rss_feed_list, container, false);

            mRssUnreadList = new ArrayList<Map<String, String>>();
//
            ArrayMap<String, String> map = new ArrayMap<String, String>();
            map.put("rssName", "全部未读");
            map.put("rssCount", rssItemCount);
            mRssUnreadList.add(map);

            Log.i("count数", String.valueOf(cursor.getCount()));

            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("RssName"));
                int count = cursor.getInt(cursor.getColumnIndex("ItemsCount"));

                ArrayMap<String, String> arrayMap = new ArrayMap<String, String>();
                arrayMap.put("rssName", name);
                arrayMap.put("rssCount", String.valueOf(count));
                mRssUnreadList.add(arrayMap);
            }


        } else {
            Log.i("过程打印", "不存在Feed");
            view = inflater.inflate(R.layout.black_unread, container, false);
        }

        cursor.close();
        mSqLiteHandle.dbClose();
        mSqLiteHandle = null;
        Log.i("unReadFragment11", "onCreateView:mSqLiteHandle已关闭");

        init();

        return view;
    }

    public void init() {
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InputRssLinkActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

            }
        });

        showListView();

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    //列表显示获取的RSS项目
    private void showListView() {
        Log.i("过程标记", "进入showListView()");

        try {

            itemlist = (ListView) view.findViewById(R.id.lv_rssList);


            adapter = new SimpleAdapter(getActivity(), mRssUnreadList,
                    R.layout.rsslist_item, new String[]{
                    "rssName", "rssCount"
            },
                    new int[]{
                            R.id.tv_rssName, R.id.tv_rssCount
                    });

            itemlist.setAdapter(adapter);
            itemlist.setOnItemClickListener(this);
            itemlist.setSelection(0);
        } catch (Exception e) {
            Log.i("list显示", e.getMessage());
        }
    }

    //处理列表的单击事件
    public void onItemClick(AdapterView parent, View v, int position, long id) {

        try {

//            Toast.makeText(getActivity(), "解析系统暂不可用", Toast.LENGTH_SHORT).show();

            Intent itemIntent = new Intent(getActivity(), ListActivity.class);
            startActivity(itemIntent);

        } catch (Exception e) {
            Log.i("error", e.getMessage());
        }

    }

}
