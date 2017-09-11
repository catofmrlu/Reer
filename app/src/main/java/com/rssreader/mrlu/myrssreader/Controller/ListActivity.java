package com.rssreader.mrlu.myrssreader.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSItem;
import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
import com.rssreader.mrlu.myrssreader.Model.XmlParse.RSSHandler;
import com.rssreader.mrlu.myrssreader.R;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public String tag = "RSSReader";

    private RequestQueue mRequestQueue;

    private SwipeRefreshLayout mSrl;

    private SimpleAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl_list);

        String rssLink = "https://movie.douban.com/feed/review/movie";


        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(rssLink);
        builder.method("GET", null);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d("rssLink请求", "请求返回失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream isRss = response.body().byteStream();

                try {

                    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                    SAXParser parser = saxParserFactory.newSAXParser();

                    RSSHandler rssHandler = new RSSHandler();
                    parser.parse(isRss, rssHandler);

                    RSSFeed feed = rssHandler.getFeed();


                    //判断feed是否为空
                    if (feed == null) {
                        Log.e("feed", "feed为空");
                    } else {
                        Log.i("恭喜！", "feed通过");

                        //统计添加源的项目数
                        System.out.println("---------/n该feed的rssitem数据" + feed.Count() + "/n------");

                        for (Object map :
                                feed.getAllItemsForListView()) {

                            ArrayMap<String, String> arrayMap = (ArrayMap<String, String>) map;
                            Log.i("item", arrayMap.get("title"));
                        }

                        //sqlite插入部分
                        SQLiteHandle sqLiteHandle = new SQLiteHandle(getApplicationContext());
                        sqLiteHandle.insertFeed(feed.getName(), feed.getFeedDescription(), feed.getFeedLink(), feed.Count());
                        Log.i("sqlite插入", feed.getName() + ":插入成功！！");

                        sqLiteHandle.dbClose();
                        sqLiteHandle = null;

                    }
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

//


            }

        });


    }

//列表显示获取的RSS

    private void showListView(RSSFeed mFeed) {
        ListView itemlist = (ListView) findViewById(R.id.lv_rssList);

        mAdapter = new SimpleAdapter(this, mFeed.getAllItemsForListView(),
                android.R.layout.simple_list_item_2, new String[]{
                RSSItem.TITLE, RSSItem.PUBDATE
        },
                new int[]{
                        android.R.id.text1, android.R.id.text2
                });

        itemlist.setAdapter(mAdapter);
        itemlist.setOnItemClickListener(this);
        itemlist.setSelection(0);
    }

    //处理列表的单击事件
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        Intent itemIntent = new Intent(this, ShowDescriptionActivity.class);

        //传递rssItem的内容
//        Bundle bundle = new Bundle();
//        bundle.putString("title", mFeed.getItem(position).getTitle());
//        bundle.putString("description", mFeed.getItem(position).getDescription());
//        bundle.putString("link", mFeed.getItem(position).getLink());
//        bundle.putString("pubdate", mFeed.getItem(position).getPubdate());
//
//        itemIntent.putExtra("android.intent.extra.rssItem", bundle);

        startActivityForResult(itemIntent, 0);

    }

}
