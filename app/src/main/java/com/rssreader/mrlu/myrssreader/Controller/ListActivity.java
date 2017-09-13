package com.rssreader.mrlu.myrssreader.Controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
import com.rssreader.mrlu.myrssreader.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public String tag = "RSSReader";

    List<Map<String, String>> mapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        SQLiteHandle sqLiteHandle = new SQLiteHandle(this);
        Cursor cursor = sqLiteHandle.queryAllUnreadItems();

        if (cursor != null) {
            Log.i("cursor", "存在");

            mapList = new ArrayList<Map<String, String>>();

            while (cursor.moveToNext()) {
                Map<String, String> map = new ArrayMap<String, String>();

                map.put("title", cursor.getString(cursor.getColumnIndex("ItemTitle")));
                map.put("pubdate", cursor.getString(cursor.getColumnIndex("ItemPubdate")));

                Log.i("appear-item", map.get("title") + ":" + map.get("pubdate"));
                mapList.add(map);
            }

            showListView(mapList);
        } else {
            Log.i("ListActivity", "cursor为空");
        }

    }

    //列表显示获取的RSS
    private void showListView(List<Map<String, String>> list) {
        SwipeMenuListView itemlist = (SwipeMenuListView) findViewById(R.id.lv_rssList);

        SimpleAdapter mAdapter = new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_2, new String[]{
                "title", "pubdate"
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

        startActivity(itemIntent);

    }


}
