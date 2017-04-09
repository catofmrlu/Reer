package com.rssreader.mrlu.myrssreader.View.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSHandler;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSItem;
import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
import com.rssreader.mrlu.myrssreader.R;
import com.rssreader.mrlu.myrssreader.View.ListActivity;
import com.rssreader.mrlu.myrssreader.View.ShowDescriptionActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by LuXin on 2017/2/26.
 */

public class unReadFragment extends Fragment implements AdapterView.OnItemClickListener {

    public String RSS_URL;
    //            = "http://free.apprcn.com/category/ios/feed/";
    public String tag = "RSSReader";
    private RSSFeed feed = null;

    //    OkHttpClient mOkHttpClient;
    InputSource isc;

    public RequestQueue mRequestQueue;

    SwipeRefreshLayout mSrl;

    SwipeMenuListView mSwipeMenuListView;

    private SimpleAdapter adapter;

    ListView itemlist;

    View view;

    Window window;

    List<Map<String, Object>> mRssUnreadList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_list, container, false);


        init();
        getFeed(RSS_URL);


        return view;
    }

    public void init() {

        RSS_URL = "http://www.feng.com/rss.xml";
//                "http://free.apprcn.com/category/ios/feed/";

        mSrl = (SwipeRefreshLayout) view.findViewById(R.id.srl_list);

        mSwipeMenuListView = (SwipeMenuListView) view.findViewById(R.id.lv_rssList);

        window = getActivity().getWindow();


        //注册EventBus接收者
//        EventBus.getDefault().register(this);
//        Log.i("传递值打印Frame", RSS_URL);


        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refreshed();
                        // 停止刷新
                        mSrl.setRefreshing(false);
                        Toast.makeText(getContext(), "刷新完成！", Toast.LENGTH_SHORT).show();
                    }
                }, 2000); // 2秒后发送消息，停止刷新


            }
        });


        //listview左滑部分
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(15);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.feed_read);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mSwipeMenuListView.setMenuCreator(creator);

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    //获取feed
    private void getFeed(final String urlString) {

        try {

            //新建SAX--xml解析工厂类
            SAXParserFactory factory = SAXParserFactory.newInstance();

            SAXParser parser = factory.newSAXParser();

            final XMLReader reader = parser.getXMLReader();

            final RSSHandler rssHander = new RSSHandler();

            reader.setContentHandler(rssHander);

//                URL url = new URL(urlString);

            mRequestQueue = Volley.newRequestQueue(getContext());
            StringRequest mStringRequest = new StringRequest(urlString,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.i("respone:", response);


                            Log.i("间隔", "请求执行完成");

                            InputStream is = new ByteArrayInputStream(response.getBytes());

                            try {
                                if (is != null) {
                                    isc = new InputSource(is);

                                    Log.i("IS", "IS转换完成");

                                    Log.i("IS", isc.toString());

                                    reader.parse(isc);

                                    feed = rssHander.getFeed();


                                    if (feed == null) {
                                        Log.e("feed", "feed为空");
                                    } else {
                                        Log.i("恭喜！", "feed通过");

                                        System.out.println("---------/n" + feed.Count());

                                        mRssUnreadList = new ArrayList<>();
                                        Map<String, Object> map = new HashMap<String, Object>();
                                        map.put("rssName", "全部未读");
                                        map.put("rssCount", /*feed.Count()*/"www");
                                        mRssUnreadList.add(map);

                                        Map<String, Object> map1 = new HashMap<String, Object>();
                                        map1.put("rssName", /*feed.getName()*/"sss");
                                        map1.put("rssCount", /*feed.Count()*/"ssssss");
                                        mRssUnreadList.add(map1);

                                        Log.i("过程标记", "list装载完成");
//                                        try {
//
//                                            final SQLiteHandle sqLiteHandle = new SQLiteHandle(getContext());
//
//                                            sqLiteHandle.queryAllFeeds("AllFeeds");
//
//                                            if (!sqLiteHandle.urlQuery(urlString)) {
//                                                //异步将feed插入数据库
//                                                new Thread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        sqLiteHandle.insertFeed(feed.getName(), feed.getFeedDescription(),
//                                                                RSS_URL);
//
//                                                        feed.isInserted = true;
//
//                                                        sqLiteHandle.queryAllFeeds("AllFeeds");
//                                                    }
//                                                }).start();
//                                            } else {
//                                                Log.i("数据库", "已插入！");
//                                            }

//                                        } catch (Exception e) {
//                                            Log.e("sql数据库问题", e.getMessage());
//                                        }

                                    }

                                } else {
                                    Log.e("is", "is为空");
                                }

                            } catch (Exception e) {
                                Log.e("is转换", e.getMessage());
                            }

                            showListView();
                            Log.i("过程标记", "list显示完成");

                        }
                    },

                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.e("error", error.getMessage());
                        }
                    }

            );

            mRequestQueue.add(mStringRequest);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    //列表显示获取的RSS项目
    private void showListView() {
        Log.i("过程标记", "进入showListView()");

        itemlist = (ListView) view.findViewById(R.id.lv_rssList);
        if (feed == null) {

//            view.setTitle("访问的RSS无效");
            Log.i("tag", "访问的RSS无效");
            return;
        } else {

//            setTitle(tag);
            Log.i("tag", feed.getName());
        }


        adapter = new SimpleAdapter(getContext(), mRssUnreadList,
                R.layout.rsslist_item, new String[]{
                "rssName", "rssCount"
        },
                new int[]{
                        R.id.tv_rssName, R.id.tv_rssCount
                });

        itemlist.setAdapter(adapter);
        itemlist.setOnItemClickListener(this);
        itemlist.setSelection(0);
    }

    //处理列表的单击事件
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        Intent itemIntent = new Intent(getContext(), ListActivity.class);

        Bundle bundle = new Bundle();
//        bundle.putString("title", feed.getItem(position).getTitle());
//        bundle.putString("description", feed.getItem(position).getDescription());
//        bundle.putString("link", feed.getItem(position).getLink());
//        bundle.putString("pubdate", feed.getItem(position).getPubdate());
//
//        mRssUnreadList.get(position)


        bundle.putSerializable("feed", feed);

//        itemIntent.putExtra("android.intent.rssItem", bundle);
        itemIntent.putExtras(bundle);

        startActivityForResult(itemIntent, 0);

    }

    //下拉刷新数据
    void refreshed() {

        adapter.notifyDataSetChanged();

    }

    @Subscribe
    public void onevent(String data) {
        RSS_URL = data;
        Log.i("发送成功", "rss:" + RSS_URL);

        getFeed(RSS_URL);

        adapter.notifyDataSetChanged();
    }

//    @Override
//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }


}
