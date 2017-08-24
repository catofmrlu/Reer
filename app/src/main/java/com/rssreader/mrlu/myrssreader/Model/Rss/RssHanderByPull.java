package com.rssreader.mrlu.myrssreader.Model.Rss;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by luxin on 2017/8/24.
 */

public class RssHanderByPull {

    private RSSFeed mFeed;
    private RSSItem mItem;
    private List<RSSItem> mRssList;

    public  RSSFeed parseRss(String rssUrl){

        HttpURLConnection conn = null;
        try {
            URL url = new URL(rssUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();

            PullParser(is);

        }catch (Exception e){
            Log.e("转化url", e.getMessage());
        }finally {

            conn.disconnect();
            conn = null;
        }

        return mFeed;
    }

    public RSSFeed PullParser(InputStream is){

        try {
            // 使用工厂类XmlPullParserFactory
            XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullFactory.newPullParser();

            parser.setInput(is, "UTF-8");
            int eventType = parser.getEventType();
            // 只要不是文档结束事件，就一直循环
            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        mFeed = new RSSFeed();
                        break;
                    case XmlPullParser.START_TAG:

                        String itemStartName = parser.getName();

                        switch (itemStartName){
                            case "title":
                                mFeed.setName(parser.nextText());
                                break;
                            case "link":
                                mFeed.setFeedLink(parser.nextText());
                                break;

                            case "pubDate":
                                mFeed.setPubDate(parser.nextText());
                                break;
                            case "description":
                                mFeed.setFeedDescription(parser.nextText());
                                break;
                            case "item":
                                mItem = new RSSItem();
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        String itemEndItem = parser.getName();
                        if (itemEndItem == "item"){
                            mFeed.addItem(mItem);
                        }

                }
            }


        }catch (Exception e){
            Log.e("PullParser方法部分", e.getMessage());
        }
        return mFeed;
    }
}
