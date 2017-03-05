package com.rssreader.mrlu.myrssreader.View.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSHandler;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSItem;
import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
import com.rssreader.mrlu.myrssreader.R;
import com.rssreader.mrlu.myrssreader.View.ShowDescriptionActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by LuXin on 2017/2/26.
 */

public class unReadFragment extends Fragment implements AdapterView.OnItemClickListener{


    public String RSS_URL;
    //            = "http://free.apprcn.com/category/ios/feed/";
    public  String tag = "RSSReader";
    private RSSFeed feed = null;

    //    OkHttpClient mOkHttpClient;
    InputSource isc;

    public RequestQueue mRequestQueue;

    SwipeRefreshLayout mSrl;

    private SimpleAdapter adapter;

    ListView itemlist;

    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_list, container, false);



//        RSS_URL = "http://free.apprcn.com/category/ios/feed/";

        mSrl = (SwipeRefreshLayout) view.findViewById(R.id.srl_list);


        //注册EventBus接收者
        EventBus.getDefault().register(this);

        Log.i("传递值打印Frame", RSS_URL);

        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refreshed();
                        // 停止刷新
                        mSrl.setRefreshing(false);
                    }
                }, 2000); // 2秒后发送消息，停止刷新

                Toast.makeText(getContext(), "刷新完成！", Toast.LENGTH_SHORT).show();

            }
        });


        getFeed(RSS_URL);

        return view;
    }


    //获取feed
    private void getFeed(final String urlString) {

//        final String feedString;
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

                                    Log.e("IS", "IS转换完成");

                                    Log.e("IS", isc.toString());

                                    reader.parse(isc);

                                    feed = rssHander.getFeed();

                                    Log.i("Title", "title:" + feed.getTitle());

                                    tag = feed.getTitle();

                                    //response返回把feed存入数据库
//                                    SQLiteHandle mSqliteHandler = new SQLiteHandle(getContext());
//                                    mSqliteHandler.insert("AllFeeds", "dddd", "sss", urlString);

                                    Log.i("sqliite插入", "插入feed成功");

//                                    mSqliteHandler.query("AllFeeds");



                                    if (feed == null){
                                        Log.e("feed", "feed为空");
                                    }else {Log.i("恭喜！", "feed通过");}

                                } else {
                                    Log.e("is", "is为空");
                                }

                            } catch (Exception e) {
                                Log.e("is转换", e.getMessage());
                            }

                            showListView();
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

        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        } catch (SAXException e1) {
            e1.printStackTrace();
        }

    }
//            //新建SAX--xml解析工厂类
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//
//            SAXParser parser = factory.newSAXParser();
//
//            XMLReader reader = parser.getXMLReader();
//
//            RSSHandler rssHander = new RSSHandler();

//
//                new Thread() {
//                    @Override
//                    public void run() {
    ///okhttp3 网络连接

//                        mOkHttpClient = new OkHttpClient();
//                        Request.Builder requestBuilder = new Request.Builder().url(url);
//
//                        //可以省略，默认是GET请求
//                        requestBuilder.method("GET", null);
//                        Request request = requestBuilder.build();
//                        Call mcall = mOkHttpClient.newCall(request);
//                        mcall.enqueue(new Callback() {
//                            @Override
//                            public void onFailure(Call call, IOException e) {
//                                Log.e("netWorkFailure", e.toString());
//
//                            }
//
//                            @Override
//                            public void onResponse(Call call, Response response) throws IOException {
//
//                                if (response.isSuccessful()) {
//
//
//                                    Log.i("responeSuccess", "返回成功了");
//
//                                    String responeNode = response.body().string();
//                                    Log.i("body", "str4---" + responeNode);
//
//
//                                    Message msg = Message.obtain();
//                                    msg.obj = response;
////                                    msg.what = 1;//区分哪一个线程发送的消息
//                                    handler.sendMessage(msg);
//
////                                    is = response.body().byteStream();
//
//
//                                } else {
//                                    System.out.println("返回失败");
//                                }
//                            }
//
//                        });
//
//                    }
//                }.start();
//
////            SystemClock.sleep(2000);
//
////            Log.i("间隔", "请求执行完成");
////
////            try {
////                if (is != null) {
////                    isc = new InputSource(is);
////
////                    Log.e("IS", "IS转换完成");
////                } else {
////                    Log.e("is", "is为空");
////                }
////
////
////                reader.parse(isc);
////
////
////                Log.e("saxJiexi", "chenggong");
////            } catch (SAXException e) {
////                Log.i("SAX", e.toString());
////            }
////            return rssHander.getFeed();
//


    //列表显示获取的RSS
    private void showListView() {
        itemlist = (ListView) view.findViewById(R.id.lv_rssList);
        if (feed == null) {

//            view.setTitle("访问的RSS无效");
            Log.i("tag", "访问的RSS无效");
            return;
        } else {

//            setTitle(tag);
            Log.i("tag", "tag");
        }

        adapter = new SimpleAdapter(getContext(), feed.getAllItemsForListView(),
                android.R.layout.simple_list_item_2, new String[]{
                RSSItem.TITLE, RSSItem.PUBDATE
        },
                new int[]{
                        android.R.id.text1, android.R.id.text2
                });

        itemlist.setAdapter(adapter);
        itemlist.setOnItemClickListener(this);
        itemlist.setSelection(0);
    }

    //处理列表的单击事件
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        Intent itemIntent = new Intent(getContext(), ShowDescriptionActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("title", feed.getItem(position).getTitle());
        bundle.putString("description", feed.getItem(position).getTitle());
        bundle.putString("link", feed.getItem(position).getTitle());
        bundle.putString("pubdate", feed.getItem(position).getTitle());

        itemIntent.putExtra("android.intent.extra.rssItem", bundle);

        startActivityForResult(itemIntent, 0);

    }

    //下拉刷新数据
    void refreshed(){


        int count1 = feed.Count();
        System.out.println(count1);

        RSSItem rssItem = new RSSItem();
        rssItem.setTitle("我是新加来的");
        rssItem.setPubdate("2017.12.23");

        int count2  = feed.addItem(rssItem);
        System.out.println(count2);


        adapter.notifyDataSetChanged();

//        mSrl.setRefreshing(false);

    }

    @Subscribe
    public void onEvent(String data) {
        RSS_URL = data;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

//        class MyTask extends AsyncTask<URL, Void, String> {
//
//            @Override
//            protected String doInBackground(URL... params) {
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//
//            }
//        }


}
