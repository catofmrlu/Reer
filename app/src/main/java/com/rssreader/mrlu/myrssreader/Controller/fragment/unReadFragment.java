package com.rssreader.mrlu.myrssreader.Controller.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.rssreader.mrlu.myrssreader.Controller.InputRssLinkActivity;
import com.rssreader.mrlu.myrssreader.Controller.ListActivity;
import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
import com.rssreader.mrlu.myrssreader.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LuXin on 2017/2/26.
 */

public class unReadFragment extends Fragment implements AdapterView.OnItemClickListener {

    private SimpleAdapter adapter;
    private List<Map<String, String>> mRssUnreadList;
    private SQLiteHandle mSqLiteHandle;
    public String rssItemCount = "0";

    View view;
    ListView itemlist;
    SwipeRefreshLayout srlFeedList;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            adapter.notifyDataSetChanged();
            srlFeedList.setRefreshing(false);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_rss_feed_list, container, false);
        init();

        return view;
    }

    public void init() {
        mRssUnreadList = new ArrayList<Map<String, String>>();

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InputRssLinkActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

            }
        });

        srlFeedList = (SwipeRefreshLayout) view.findViewById(R.id.srl_rssList);
        srlFeedList.setColorSchemeResources(R.color.appBaseColor);

        srlFeedList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlFeedList.setRefreshing(true);
                Log.i("loading...", "new thread1");

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Log.i("loading...", "new thread2");
                        loadSQLiteData();

                        SystemClock.sleep(600);
                        handler.sendEmptyMessage(0);
                    }
                }).start();
            }
        });

        mSqLiteHandle = new SQLiteHandle(getActivity());
        Cursor cursor = mSqLiteHandle.queryAllFeeds();

        if (cursor != null) {
            Log.i("过程打印", "存在Feed");

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
        }

        cursor.close();
        mSqLiteHandle.dbClose();
        mSqLiteHandle = null;
        Log.i("unReadFragment", "onCreateView:mSqLiteHandle已关闭");


        showListView();
    }

    //列表显示获取的RSS项目
    private void showListView() {
        Log.i("过程标记", "进入showListView()");

        try {
            itemlist = (ListView) view.findViewById(R.id.rv_feed);

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
            Log.i("list显示问题", e.getMessage());
        }

        itemlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                if (position == 0)
                    Toast.makeText(getActivity(), "该条目不可删除！", Toast.LENGTH_SHORT).show();
                else {
                    final String rssName = mRssUnreadList.get(position).get("rssName");
                    new AlertDialog.Builder(getActivity())
                            .setTitle("删除rss订阅源")
                            .setMessage("是否删除「 " + rssName + " 」？")
                            .setPositiveButton("是的", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mSqLiteHandle = new SQLiteHandle(getActivity());
                                            mSqLiteHandle.deleteFeed(mRssUnreadList.get(position).get("rssName"));
                                            mSqLiteHandle.deleteFeedUnreadItem(mRssUnreadList.get(position).get("rssName"));

                                            mRssUnreadList.remove(position);

                                            mSqLiteHandle.dbClose();
                                            mSqLiteHandle = null;

                                            Message message = new Message();
                                            message.obj = "delete";

                                            handler.sendMessage(message);
                                        }
                                    }).start();
                                }
                            })
                            .setNeutralButton("误触", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                }
                return true;
            }
        });
    }

    //处理列表的单击事件
    public void onItemClick(AdapterView parent, View v, int position, long id) {

        try {
            Intent itemIntent = new Intent(getActivity(), ListActivity.class);

            itemIntent.putExtra("rssName", mRssUnreadList.get(position).get("rssName"));
            startActivity(itemIntent);
        } catch (Exception e) {
            Log.i("onItemClick:error", e.getMessage());
        }
    }

    public void loadSQLiteData() {
        {
            mSqLiteHandle = new SQLiteHandle(getActivity());
            Cursor cursor = mSqLiteHandle.queryUnappearFeeds();

            if (cursor != null) {
                Log.i("过程打印", "存在Feed");
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
            }

            mSqLiteHandle.updateUnAppearFeeds();

            cursor.close();
            mSqLiteHandle.dbClose();
            mSqLiteHandle = null;
            Log.i("unReadFragment", "onCreateView:mSqLiteHandle已关闭");
        }
    }
}
