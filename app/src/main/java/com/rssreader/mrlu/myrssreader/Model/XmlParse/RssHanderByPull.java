package com.rssreader.mrlu.myrssreader.Model.XmlParse;

import android.util.Log;
import android.util.Xml;

import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by luxin on 2017/8/24.
 */

public class RssHanderByPull {

    private RSSFeed mFeed;
    private RSSItem mItem;
    private List<RSSItem> mRssList;

    private static int i = 0;

    public RSSFeed parseRss(InputStream is) {

//        HttpURLConnection conn = null;

//            URL url = new URL(rssUrl);
//            conn = (HttpURLConnection) url.openConnection();
//
//            conn.setDoInput(true);
//            conn.connect();
//
//            InputStream is = conn.getInputStream();
//            Log.i("is流获取", "success!!");
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            StringBuilder sb = new StringBuilder();
//
//            String line = null;
//
//            try {
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line + "\n");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            Log.i("is转化为string", sb.toString());

//            PullParser(is);
//            ByteArrayInputStream inputStream = new ByteArrayInputStream(rssUrl.getBytes("UTF-8"));
            PullParser(is);

        return mFeed;
    }

    public void PullParser(InputStream is) {
        try {
            // 使用工厂类XmlPullParserFactory
            XmlPullParser parser = Xml.newPullParser();

            Log.i("xml解析", "即将开始！！");

            parser.setInput(is, "UTF-8");
            int eventType = parser.getEventType();
            // 只要不是文档结束事件，就一直循环
            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        mFeed = new RSSFeed();

                        Log.i("START_DOCUMENT", "new RSSFeed()");

                        break;
                    case XmlPullParser.START_TAG:

                        Log.i("START_TAG", parser.getName());

                        switch (parser.getName()) {

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

                        Log.i("END_TAG", parser.getName());

                        if (parser.getName().equals("item")) {
                            mFeed.addItem(mItem);
                            mItem = null;
                        }
                        break;
                }
            }

        } catch (XmlPullParserException e) {

            Log.e("XmlPullParserException", e.getLocalizedMessage() + e.toString());
        } catch (IOException e) {
            Log.e("IOException", e.getLocalizedMessage() + e.toString());
        }
    }
}
