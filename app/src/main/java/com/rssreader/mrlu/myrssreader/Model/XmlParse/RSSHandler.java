package com.rssreader.mrlu.myrssreader.Model.XmlParse;

import android.util.Log;

import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Mr Lu on 2017/1/7.
 */

public class RSSHandler extends DefaultHandler {

    RSSFeed rssFeed;
    RSSItem rssItem;

    String lastElementName = "";
    int currentState = 0;

    final int RSS_TITLE = 1;
    final int RSS_LINK = 2;
    final int RSS_DESCRIPTION = 3;
    final int RSS_CATEGORY = 4;
    final int RSS_PUBDATE = 5;

    private boolean mIsItemTitle = false;


    public RSSHandler() {

    }

    public RSSFeed getFeed() {
        return rssFeed;
    }

    @Override
    public void startDocument() throws SAXException {
        try {


            rssFeed = new RSSFeed();
            rssItem = new RSSItem();
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {


            if (localName.equals("channel")) {
                currentState = 0;
                return;
            }
            if (localName.equals("item")) {
                mIsItemTitle = true;
                rssItem = new RSSItem();
                return;
            }
            if (localName.equals("title")) {
                currentState = RSS_TITLE;
                return;
            }
            if (localName.equals("description")) {
                currentState = RSS_DESCRIPTION;
                return;
            }
            if (localName.equals("link")) {
                currentState = RSS_LINK;
                return;
            }
            if (localName.equals("category")) {
                currentState = RSS_CATEGORY;
                return;
            }
            if (localName.equals("pubDate")) {
                currentState = RSS_PUBDATE;
                return;
            }
            currentState = 0;

        } catch (Exception e) {
            Log.e("sax解析", e.toString());
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("item")) {
            rssFeed.addItem(rssItem);
            return;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String theString = new String(ch, start, length);

//        if (theString.indexOf(" &#8211; ") != -1) {
//            theString.replace(" &#8211; ", "");
//        }
        try {


            switch (currentState) {
                case RSS_TITLE:

                    if (mIsItemTitle) {
                        rssItem.setTitle(theString);
                        currentState = 0;
                    } else {
                        Log.i("title", theString);
                        rssFeed.setName(theString);
                        currentState = 0;
                    }
                    return;


                case RSS_LINK:
                    rssItem.setLink(theString);
                    currentState = 0;
                    return;


                case RSS_DESCRIPTION:

                    if (mIsItemTitle) {
                        rssItem.setDescription(theString);
                        currentState = 0;
                    } else {
                        rssFeed.setFeedDescription(theString);
                        currentState = 0;
                    }

                    return;


                case RSS_CATEGORY:
                    rssItem.setCategory(theString);
                    currentState = 0;
                    return;


                case RSS_PUBDATE:
                    rssItem.setPubdate(theString);
                    currentState = 0;
                    return;

                default:
                    return;
            }
        } catch (Exception e) {
            Log.e("sax解析", e.toString());
        }

    }

    @Override
    public void endDocument() throws SAXException {


    }
}
