package com.rssreader.mrlu.myrssreader.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Mr Lu on 2017/1/6.
 */

public class RSSFeed {

    private String title = null;
    private String pubdate = null;
    private int itemcount = 0;
    private List<RSSItem> itemlist;

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

    public String getPubdate() {
        return pubdate;
    }

    int getItemcount() {
        return itemcount;
    }

    public String getTitle() {
        return title;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
