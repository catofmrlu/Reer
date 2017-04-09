package com.rssreader.mrlu.myrssreader.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.rssreader.mrlu.myrssreader.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RssFeedListActivity extends AppCompatActivity {


    @BindView(R.id.lv_rssList)
    SwipeMenuListView lvRssList;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed_list);

        ButterKnife.bind(this);

        init();

        showListView();

    }

    void init() {
        //listview左滑部分
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        RssFeedListActivity.this);
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(15);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        RssFeedListActivity.this);
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.feed_read);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        lvRssList.setMenuCreator(creator);
    }

    private void showListView() {

        List<String> list = new ArrayList<String>();
//
//        RSSFeed rssFeed = new RSSFeed();
//        rssFeed.setName("反斗--IOS");
//        rssFeed.setFeedLink("www.ba");
        list.add("反斗--IOS");
//
//        RSSFeed rssFeed1 = new RSSFeed();
//        rssFeed1.setName("知乎日报");
//        rssFeed1.setFeedLink("https://www.zhihu.com/rss");
        list.add("知乎日报");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);

        lvRssList.setAdapter(adapter);
        lvRssList.setSelection(0);

        lvRssList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        setIntentString("http://free.apprcn.com/category/ios/feed/");
                        break;

                    case 1:
                        setIntentString("https://www.zhihu.com/rss");
                        break;

                    default:
                        break;
                }

            }
        });
    }

    //传入要跳转的rssLink
    public void setIntentString(String rssLink) {

        Intent rssListIntent = new Intent(RssFeedListActivity.this, mainView.class);

        Bundle bundle = new Bundle();
        bundle.putString("rssLink", rssLink);

        rssListIntent.putExtra("android.intent.extra.rssFeed", bundle);

        startActivity(rssListIntent);
    }

    //转换dp与px
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
