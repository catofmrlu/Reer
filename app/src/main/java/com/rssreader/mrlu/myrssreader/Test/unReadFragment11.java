package com.rssreader.mrlu.myrssreader.Test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSHandler;
import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
import com.rssreader.mrlu.myrssreader.R;
import com.rssreader.mrlu.myrssreader.Controller.ListActivity;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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
    //    public int rssItemCount = 0;
    public String rssItemCount = "12";

    View view;
    Window window;
    //    private SwipeMenuListView mSwipeMenuListView;
    ListView mSwipeMenuListView;
    ListView itemlist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mSqLiteHandle = new SQLiteHandle(getActivity());
        Log.i("过程打印", "创建mSqLiteHandle完成");
        Cursor cursor = mSqLiteHandle.queryAllFeeds();

        if (cursor != null) {
            Log.i("过程打印", "存在Feed");

            view = inflater.inflate(R.layout.activity_rss_feed_list, container, false);

            Log.i("过程打印", "queryAllFeeds查询完成");

            mRssUnreadList = new ArrayList<>();
            Map<String, String> map = new HashMap<String, String>();
            map.put("rssName", "全部未读");
            map.put("rssCount", rssItemCount);
            mRssUnreadList.add(map);

            //region 测试listview

//
//                Map<String, String> map1 = new HashMap<String, String>();
//                map1.put("rssName", "威锋网");
//                map1.put("rssCount",  "15");
//                mRssUnreadList.add(map1);
//
//                Map<String, String> map2 = new HashMap<String, String>();
//                map2.put("rssName", "IT之家");
//                map2.put("rssCount",  "20");
//                mRssUnreadList.add(map2);
//
//                Map<String, String> map3 = new HashMap<String, String>();
//                map3.put("rssName", "好奇心日报");
//                map3.put("rssCount",  "8");
//                mRssUnreadList.add(map3);
//
//                Map<String, String> map4 = new HashMap<String, String>();
//                map4.put("rssName", "果壳");
//                map4.put("rssCount",  "5");
//                mRssUnreadList.add(map4);
            //endregion

            showListView();
            Log.i("过程标记", "list显示完成");
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("RssName"));
//                String description = cursor.getString(cursor.getColumnIndex("RssDescription"));
                String link = cursor.getString(cursor.getColumnIndex("RssLink"));
                Log.i("查询数据库", name + "\n" + link);
//
//                //打印查询的数据
//                System.out.println(name + "---" + description + "---" + link);
                try {
//                    getFeed(link);

                } catch (Exception e) {
                    Log.e("getFeed", e.getMessage());
                    view = inflater.inflate(R.layout.unload_unread, container, false);

                }
            }
            cursor.close();
            mSqLiteHandle.dbClose();
            mSqLiteHandle = null;

        } else {
            Log.i("过程打印", "不存在Feed");
            view = inflater.inflate(R.layout.black_unread, container, false);

        }

        return view;

    }

    public void init() {

//        mSwipeMenuListView = (ListView) view.findViewById(R.id.lv_rssList);

      /*  window = getActivity().getWindow();

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity());
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
                        getActivity());
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
        mSwipeMenuListView.setMenuCreator(creator);*/

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    //region getfeed部分
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

            mRequestQueue = Volley.newRequestQueue(getActivity());
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

                                    //累加各个feed的item数
                                    rssItemCount += feed.Count();

                                    if (feed == null) {
                                        Log.e("feed", "feed为空");
                                    } else {
                                        Log.i("恭喜！", "feed通过");

                                        System.out.println("---------/n" + feed.Count() + "/n------");

//                                        Map<String, Object> map = new HashMap<String, Object>();
//                                        map.put("rssName", feed.getName());
//                                        map.put("rssCount", feed.Count());
//                                        mRssUnreadList.add(map);

                                        Log.i("过程标记", "list装载完成");
                                        try {

                                            SQLiteHandle sqLiteHandle = new SQLiteHandle(getActivity());

//                                            sqLiteHandle.queryAllFeeds("AllFeeds");

                                            if (!sqLiteHandle.urlQuery(urlString)) {

                                                sqLiteHandle.dbClose();

                                                //异步将feed插入数据库
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        SQLiteHandle sqLiteHandle = new SQLiteHandle(getActivity());

                                                        sqLiteHandle.insertFeed(feed.getName(), feed.getFeedDescription(),
                                                                urlString);

                                                        feed.isInserted = true;

                                                        //插入sp文件设置值
                                                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);

                                                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器

                                                        editor.putBoolean("isHasFeed", true);

                                                        editor.commit();//提交修改

                                                        sqLiteHandle.queryAllFeeds("AllFeeds");
                                                    }
                                                }).start();
                                            } else {
                                                Log.i("数据库", "已插入！");
                                            }

                                        } catch (Exception e) {
                                            Log.e("sql数据库问题", e.getMessage());
                                        }

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
    //endregion


    //列表显示获取的RSS项目
    private void showListView() {
        Log.i("过程标记", "进入showListView()");

        try {

            itemlist = (ListView) view.findViewById(R.id.lv_rssList);
//            if (feed == null) {
//
////            view.setTitle("访问的RSS无效");
//                Log.i("tag", "访问的RSS无效");
//                return;
//            } else {
//
////            setTitle(tag);
//                Log.i("tag", feed.getName());
//            }

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
        Intent itemIntent = new Intent(getActivity(), ListActivity.class);

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


    //region ？：未来待处理部分

    //下拉刷新数据
//    void refreshed() {
//        adapter.notifyDataSetChanged();
//    }


//    @Subscribe
//    public void onevent(String data) {
//        RSS_URL = data;
//        Log.i("发送成功", "rss:" + RSS_URL);
//
//        getFeed(RSS_URL);
//
//        adapter.notifyDataSetChanged();
//    }

//    @Override
//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }

    //endregion
}
