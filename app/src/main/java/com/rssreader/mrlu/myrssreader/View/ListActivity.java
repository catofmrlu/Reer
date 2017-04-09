package com.rssreader.mrlu.myrssreader.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSHandler;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSItem;
import com.rssreader.mrlu.myrssreader.R;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public  String tag = "RSSReader";

    private RequestQueue mRequestQueue;

    private SwipeRefreshLayout mSrl;

    private SimpleAdapter adapter;
    RSSFeed feed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl_list);

        Intent intent = getIntent();

        if (intent != null){

//            Bundle bundle = intent.getExtras();
//            feed = (RSSFeed) bundle.getSerializable("feed");

            feed = (RSSFeed) intent.getSerializableExtra("feed");

        }else {
            Log.e("intent错误", "intent为空");
        }

        showListView();
    }

    //列表显示获取的RSS
    private void showListView() {
        ListView itemlist = (ListView) findViewById(R.id.lv_rssList);

        adapter = new SimpleAdapter(this, feed.getAllItemsForListView(),
                android.R.layout.simple_list_item_2, new String[]{
                RSSItem.TITLE, RSSItem.PUBDATE
        },
                new int[]{
                        android.R.id.text1, android.R.id.text2
                });

        itemlist.setAdapter(adapter);
        itemlist.setOnItemClickListener(this);
        itemlist.setSelection(0);
    }

    //处理列表的单击事件
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        Intent itemIntent = new Intent(this, ShowDescriptionActivity.class);

        //传递rssItem的内容
        Bundle bundle = new Bundle();
        bundle.putString("title", feed.getItem(position).getTitle());
        bundle.putString("description", feed.getItem(position).getTitle());
        bundle.putString("link", feed.getItem(position).getTitle());
        bundle.putString("pubdate", feed.getItem(position).getTitle());

        itemIntent.putExtra("android.intent.extra.rssItem", bundle);

        startActivityForResult(itemIntent, 0);

    }

}
