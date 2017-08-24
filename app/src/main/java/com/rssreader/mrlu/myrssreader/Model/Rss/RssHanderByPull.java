package com.rssreader.mrlu.myrssreader.Model.Rss;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public RSSFeed parseRss(String rssUrl) {

        HttpURLConnection conn = null;

        try {
            URL url = new URL(rssUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            Log.i("is流获取", "success!!");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;

            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.i("is转化为string", sb.toString());

//            PullParser(is);

        } catch (Exception e) {
            Log.e("转化url-error", e.getMessage());
        } finally {
            conn.disconnect();
            conn = null;
        }

        return mFeed;
    }

    public void PullParser(InputStream is) {

        try {
            // 使用工厂类XmlPullParserFactory
            XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullFactory.newPullParser();

            Log.i("xml解析", "即将开始！！");

            parser.setInput(is, "UTF-8");
            int eventType = parser.getEventType();
            // 只要不是文档结束事件，就一直循环
            while (eventType != XmlPullParser.END_DOCUMENT) {

//                Log.i("xml解析", "进入开始节点！！");

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        mFeed = new RSSFeed();
                        Log.i("xml解析", "进入START_DOCUMENT！！");

                        break;
                    case XmlPullParser.START_TAG:

                        String itemStartName = parser.getName();

                        Log.i("xml解析", itemStartName + "！！");

                        switch (itemStartName) {
                            case "title":
                                Log.i("xml解析", "title！！");

                                if (mItem == null)
                                    mFeed.setName(parser.nextText());
                                mItem.setTitle(parser.nextText());
                                break;
                            case "link":
                                Log.i("xml解析", "link！！");

                                if (mItem == null)
                                    mFeed.setFeedLink(parser.nextText());
                                mItem.setLink(parser.nextText());
                                break;

                            case "pubDate":
                                if (mItem == null)
                                    mFeed.setPubDate(parser.nextText());
                                mItem.setPubdate(parser.nextText());
                                break;
                            case "description":
                                if (mItem == null)
                                    mFeed.setFeedDescription(parser.nextText());
                                mItem.setDescription(parser.nextText());
                                Log.i("打印rss内容", mItem.getDescription());
                                break;
                            case "item":
                                mItem = new RSSItem();
                                break;
                            case "author":
                                mItem.setAuthor(parser.nextText());
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        String itemEndItem = parser.getName();
                        if (itemEndItem.equals("item")) {
                            mFeed.addItem(mItem);
                            mItem = null;
                        }
                        break;
                }
            }


        } catch (Exception e) {
            Log.e("PullParser方法部分", e.getMessage());
        }
    }
}
