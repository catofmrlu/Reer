package com.rssreader.mrlu.myrssreader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public final String RSS_URL = "http://free.apprcn.com/category/ios/feed/";
    public final String tag = "RSSReader";
    private RSSFeed feed = null;

    OkHttpClient mOkHttpClient;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        feed = getFeed(RSS_URL);

        showListView();
    }

    private RSSFeed getFeed(String urlString) {
        try {

            final URL url = new URL(urlString);

            //新建SAX--xml解析工厂类
            SAXParserFactory factory = SAXParserFactory.newInstance();

            SAXParser parser = factory.newSAXParser();

            final XMLReader reader = parser.getXMLReader();

            RSSHandler rssHander = new RSSHandler();



            ///okhttp3 网络连接

//            mOkHttpClient=new OkHttpClient();
//            Request.Builder requestBuilder = new Request.Builder().url(url);
//            //可以省略，默认是GET请求
//            requestBuilder.method("GET",null);
//            Request request = requestBuilder.build();
//            Call mcall= mOkHttpClient.newCall(request);
//            mcall.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    if (null != response.cacheResponse()) {
//                        String str = response.cacheResponse().toString();
//                        Log.i("wangshu", "cache---" + str);
//                    } else {
//                        response.body().string();
//                        String str = response.networkResponse().toString();
//                        Log.i("wangshu", "network---" + str);
//                    }
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });


             // is = new InputSource(url.openStream());


            new Thread(){
                @Override
                public void run() {
                    try {

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        InputStream is = conn.getInputStream();

                        InputSource isc = new InputSource(is);

                        reader.parse(isc);

                    }catch (IOException e){
                        Log.e("io", e.toString());
                    }catch (SAXException e){
                        Log.e("SAX", e.toString());
                    }
                }
            }.start();



            return rssHander.getFeed();


        } catch (Exception e) {
            Log.e("e", e.toString());
            return null;
        }
    }

    //列表显示获取的RSS
    private void showListView(){
        ListView itemlist = (ListView) findViewById(R.id.lv_rssList);
        if (feed == null){
            setTitle("访问的RSS无效");
            return;
        }else {
            setTitle(tag);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, feed.getAllItemsForListView(),
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
    public void onItemClick(AdapterView parent, View v, int position, long id){
        Intent itemIntent = new Intent(this, ShowDescriptionActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("title", feed.getItem(position).getTitle());
        bundle.putString("description", feed.getItem(position).getTitle());
        bundle.putString("link", feed.getItem(position).getTitle());
        bundle.putString("pubdate", feed.getItem(position).getTitle());

        itemIntent.putExtra("android.intent.extra.rssItem", bundle);

        startActivityForResult(itemIntent, 0);
    }

}
