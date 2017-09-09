package com.rssreader.mrlu.myrssreader.Model.InternetRequest;

import android.content.Context;
import android.util.Log;

import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;
import com.rssreader.mrlu.myrssreader.Model.XmlParse.RSSHandler;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by luxin on 2017/9/8.
 */

public class RssRequestByOkHttp {

    private Context mContext;

    OkHttpClient okHttpClient;

    RSSFeed feed;

    public RssRequestByOkHttp(Context context) {
        this.mContext = context;
    }

    public RSSFeed getRssFeed(String rssLink) {


        try {


            okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder().url(rssLink);
            builder.method("GET", null);
            Request request = builder.build();
            Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {


                    InputStream isRss = response.body().byteStream();
//                    String rssXml = response.body().string();
//                    Log.i("网络返回", "onResponse: " + rssXml);
//
//                    RssHanderByPull rssHanderByPull = new RssHanderByPull();
//                    RSSFeed feed = rssHanderByPull.parseRss(isRss);


                    try {
                        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                        SAXParser parser = saxParserFactory.newSAXParser();

                        RSSHandler rssHandler = new RSSHandler();
                        parser.parse(isRss, rssHandler);

                        feed = rssHandler.getFeed();

                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }

                    //判断feed是否为空
                    if (feed == null) {
                        Log.e("feed", "feed为空");
                    } else {
                        Log.i("恭喜！", "feed通过");

                        //统计添加源的项目数
                        System.out.println("---------/n该feed的rssitem数据" + feed.Count() + "/n------");
//                try {

//                    SQLiteHandle sqLiteHandle = new SQLiteHandle(InputRssLinkActivity.this);
//                    sqLiteHandle.insertFeed(feed.getName(), feed.getFeedDescription(), urlString);
//
//                    sqLiteHandle.dbClose();
//
//                    sqLiteHandle = null;
//                } catch (Exception e) {
//                    Log.e("sqllite插入问题", e.getMessage());
//
//                }
                    }
                }
            });

        } catch (Exception e) {
            Log.e("okhttp3请求", e.toString());
        }

        return feed;

    }
}
