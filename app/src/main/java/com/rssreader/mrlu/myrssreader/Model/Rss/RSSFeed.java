package com.rssreader.mrlu.myrssreader.Model.Rss;

import android.support.v4.util.ArrayMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Mr Lu on 2017/1/6.
 */

public class RSSFeed implements Serializable {

    public boolean isInserted = false;

    private String name = null;
    private String feedDescription = null;


    private String feedLink = null;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    private String pubDate = null;

    private int itemcount = 0;
    private List<RSSItem> itemlist;


    public int Count() {

        return itemlist.size();

    }

    public RSSFeed() {
        itemlist = new Vector(0);
    }

    public int addItem(RSSItem item) {
        itemlist.add(item);
        itemcount++;
        return itemcount;
    }

    public RSSItem getItem(int location) {
        return itemlist.get(location);
    }

    public List getAllItemsForListView() {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        int size = itemlist.size();
        for (int i = 0; i < size; i++) {
            ArrayMap<String, String> item = new ArrayMap<String, String>();
            item.put("title", itemlist.get(i).getTitle());
            item.put("pubdate", itemlist.get(i).getPubdate());
            item.put("description", itemlist.get(i).getDescription());
            item.put("link", itemlist.get(i).getLink());
            data.add(item);

        }
        return data;
    }

    public List getAllItem() {
        return itemlist;
    }

    int getItemcount() {
        return itemcount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedDescription() {
        return feedDescription;
    }

    public void setFeedDescription(String feedDescription) {
        this.feedDescription = feedDescription;
    }

    public String getFeedLink() {
        return feedLink;
    }

    public void setFeedLink(String feedLink) {
        this.feedLink = feedLink;
    }


}
