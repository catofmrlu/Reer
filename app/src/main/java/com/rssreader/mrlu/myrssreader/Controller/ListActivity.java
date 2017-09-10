package com.rssreader.mrlu.myrssreader.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSItem;
import com.rssreader.mrlu.myrssreader.R;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public  String tag = "RSSReader";

    private RequestQueue mRequestQueue;

    private SwipeRefreshLayout mSrl;

    private SimpleAdapter mAdapter;
    private RSSFeed mFeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl_list);
//
//        Intent intent = getIntent();
//
//        if (intent != null){
//
//            Bundle bundle = intent.getBundleExtra("feed");
//            mFeed = (RSSFeed) bundle.getSerializable("feed");
//
//        }else {
//            Log.e("intent错误", "intent为空");
//        }
//
//        showListView();
    }

    //列表显示获取的RSS
    private void showListView() {
        ListView itemlist = (ListView) findViewById(R.id.lv_rssList);

        mAdapter = new SimpleAdapter(this, mFeed.getAllItemsForListView(),
                android.R.layout.simple_list_item_2, new String[]{
                RSSItem.TITLE, RSSItem.PUBDATE
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
        Bundle bundle = new Bundle();
        bundle.putString("title", mFeed.getItem(position).getTitle());
        bundle.putString("description", mFeed.getItem(position).getTitle());
        bundle.putString("link", mFeed.getItem(position).getTitle());
        bundle.putString("pubdate", mFeed.getItem(position).getTitle());

        itemIntent.putExtra("android.intent.extra.rssItem", bundle);

        startActivityForResult(itemIntent, 0);

    }

}
