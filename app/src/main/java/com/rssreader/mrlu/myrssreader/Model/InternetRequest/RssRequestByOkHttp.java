package com.rssreader.mrlu.myrssreader.Model.InternetRequest;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;
import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
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

    public RssRequestByOkHttp(Context context) {
        this.mContext = context;
    }

    public void getRssFeed(final String rssLink) {

//        String link = null;
        String rsslink = rssLink;

        if (!rssLink.contains("http"))
            rsslink = "http://" + rssLink;
        Log.i("link", rsslink);
        try {

            //使用OkHttp3作为网络请求框架
            okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder().url(rsslink);
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

                    RSSFeed feed = null;

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
                        System.out.println("---------/n该feed的rssitem数据-----"
                                + feed.Count()
                                + "/n--------------------/n"
                                + feed.getFeedLink());

                        SQLiteHandle sqLiteHandle = new SQLiteHandle(mContext);

                        if (!sqLiteHandle.isHasFeed(feed.getName())) {

                            sqLiteHandle.insertFeed(feed.getName(), feed.getFeedDescription(), feed.getFeedLink(), feed.Count(), false);

                            for (Object map :
                                    feed.getAllItemsForListView()) {

                                ArrayMap<String, String> arrayMap = (ArrayMap<String, String>) map;
                                String title = arrayMap.get("title");
                                String pubdate = arrayMap.get("pubdate");
                                String description = arrayMap.get("description");
                                String itemLink = arrayMap.get("link");

                                sqLiteHandle.insertUnreadItem(feed.getName(), title, pubdate, itemLink, description);

                                Log.i("item插入", "item:" + title + ":"
                                        + pubdate + ":" + itemLink
                                        + "   ----"
                                        + "description:" + description);
                            }
                        } else {
                            Log.e("添加feed", "该feed已添加");
                        }
                        sqLiteHandle.dbClose();
                        sqLiteHandle = null;
                    }
                }
            });

        } catch (Exception e) {
            Log.e("okhttp3请求", e.toString());
        }

    }

}
