package com.rssreader.mrlu.myrssreader.Model.Rss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        int size = itemlist.size();
        for (int i = 0; i < size; i++) {
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put(RSSItem.TITLE, itemlist.get(i).getTitle());
            item.put(RSSItem.PUBDATE, itemlist.get(i).getPubdate());
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
