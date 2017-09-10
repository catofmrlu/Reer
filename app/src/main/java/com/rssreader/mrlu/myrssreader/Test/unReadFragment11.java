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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.rssreader.mrlu.myrssreader.Controller.InputRssLinkActivity;
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
    //    public int rssItemCount = 0;
    public String rssItemCount = "0";

    View view;
    ListView itemlist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final FloatingActionButton fab = null;
        Log.i("过程打印", "重建了unread");

        mSqLiteHandle = new SQLiteHandle(getActivity());
        Log.i("过程打印", "创建mSqLiteHandle完成");

        Cursor cursor = mSqLiteHandle.queryAllFeeds();

        if (cursor != null) {
            Log.i("过程打印", "存在Feed");

            view = inflater.inflate(R.layout.activity_rss_feed_list, container, false);

            Log.i("过程打印", "queryAllFeeds查询完成");

//            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);

//            boolean isLoadGuideView = sharedPreferences.getBoolean("isLoadGuideView", true);
//            Log.i("isLoadGuideView", String.valueOf(isLoadGuideView));

            FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_add);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), InputRssLinkActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

                }
            });
//
            mRssUnreadList = new ArrayList<Map<String, String>>();
//
            ArrayMap<String, String> arrayMap = new ArrayMap<String, String>();
            arrayMap.put("rssName", "全部未读");
            arrayMap.put("rssCount", rssItemCount);
            mRssUnreadList.add(arrayMap);
//
//            Log.i("过程标记", "list显示完成");

            Log.i("count数", String.valueOf(cursor.getCount()));

            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("RssName"));
                String link = cursor.getString(cursor.getColumnIndex("RssLink"));
                int count = cursor.getInt(cursor.getColumnIndex("ItemsCount"));
                Log.i("查询数据库", name + "\n" + link);

                ArrayMap<String, String> arrayMap1 = new ArrayMap<String, String>();
                arrayMap.put("rssName", name);
                arrayMap.put("rssCount", String.valueOf(count));
                mRssUnreadList.add(arrayMap);

            }
            cursor.close();
            mSqLiteHandle.dbClose();
            mSqLiteHandle = null;
            Log.i("unReadFragment11", "onCreateView:mSqLiteHandle已关闭");

        } else {
            Log.i("过程打印", "不存在Feed");
            view = inflater.inflate(R.layout.black_unread, container, false);
        }



        showListView();

        return view;
    }

    public void init() {


    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    //region getfeed部分
    //获取feed
//    private void getFeed(final String urlString) {
//
//        try {
//
//            //新建SAX--xml解析工厂类
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//
//            SAXParser parser = factory.newSAXParser();
//
//            final XMLReader reader = parser.getXMLReader();
//
//            final RSSHandler rssHander = new RSSHandler();
//
//            reader.setContentHandler(rssHander);
//
//            mRequestQueue = Volley.newRequestQueue(getActivity());
//            StringRequest mStringRequest = new StringRequest(urlString,
//                    new com.android.volley.Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//
//                            Log.i("respone:", response);
//
//
//                            Log.i("间隔", "请求执行完成");
//
//                            InputStream is = new ByteArrayInputStream(response.getBytes());
//
//                            try {
//                                if (is != null) {
//                                    isc = new InputSource(is);
//
//                                    Log.i("IS", "IS转换完成");
//
//                                    Log.i("IS", isc.toString());
//
//                                    reader.parse(isc);
//
//                                    feed = rssHander.getFeed();
//
//                                    //累加各个feed的item数
//                                    rssItemCount += feed.Count();
//
//                                    if (feed == null) {
//                                        Log.e("feed", "feed为空");
//                                    } else {
//                                        Log.i("恭喜！", "feed通过");
//
//                                        System.out.println("---------/n" + feed.Count() + "/n------");
//
//                                        Log.i("过程标记", "list装载完成");
//                                        try {
//
//                                            SQLiteHandle sqLiteHandle = new SQLiteHandle(getActivity());
//
////                                            sqLiteHandle.queryAllFeeds("AllFeeds");
//
//                                            if (!sqLiteHandle.urlQuery(urlString)) {
//
//                                                sqLiteHandle.dbClose();
//
//                                                //异步将feed插入数据库
//                                                new Thread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        SQLiteHandle sqLiteHandle = new SQLiteHandle(getActivity());
//
//                                                        sqLiteHandle.insertFeed(feed.getName(), feed.getFeedDescription(),
//                                                                urlString);
//
//                                                        feed.isInserted = true;
//
//                                                        //插入sp文件设置值
//                                                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
//
//                                                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
//
//                                                        editor.putBoolean("isHasFeed", true);
//
//                                                        editor.commit();//提交修改
//
//                                                        sqLiteHandle.queryAllFeeds("AllFeeds");
//                                                    }
//                                                }).start();
//                                            } else {
//                                                Log.i("数据库", "已插入！");
//                                            }
//
//                                        } catch (Exception e) {
//                                            Log.e("sql数据库问题", e.getMessage());
//                                        }
//
//                                    }
//
//                                } else {
//                                    Log.e("is", "is为空");
//                                }
//
//                            } catch (Exception e) {
//                                Log.e("is转换", e.getMessage());
//                            }
//
//                            showListView();
//                            Log.i("过程标记", "list显示完成");
//
//                        }
//                    },
//
//                    new com.android.volley.Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                            Log.e("error", error.getMessage());
//                        }
//                    }
//
//            );
//
//            mRequestQueue.add(mStringRequest);
//
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }
//
//    }
    //endregion


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


            Toast.makeText(getActivity(), "解析系统暂不可用", Toast.LENGTH_SHORT).show();

//        Intent itemIntent = new Intent(getActivity(), ListActivity.class);
//
//        Bundle bundle = new Bundle();
//
//        bundle.putSerializable("feed", feed);
//
////        itemIntent.putExtra("android.intent.rssItem", bundle);
//        itemIntent.putExtras(bundle);
//
//        startActivityForResult(itemIntent, 0);

        }catch (Exception e){
            Log.i("error", e.getMessage());
        }

    }

}
