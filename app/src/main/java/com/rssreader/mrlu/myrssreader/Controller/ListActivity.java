package com.rssreader.mrlu.myrssreader.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
import com.rssreader.mrlu.myrssreader.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<Map<String, String>> mapList;
    SimpleAdapter mAdapter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch ((String) msg.obj) {

                case "delete":
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        TextView tvFeedName = (TextView) findViewById(R.id.tv_feedname);

        String feedName = getIntent().getStringExtra("rssName");
        tvFeedName.setText(feedName);

        SQLiteHandle sqLiteHandle = new SQLiteHandle(this);
        Cursor cursor = sqLiteHandle.queryFeedUnreadItems(feedName);

        if (cursor != null) {
            Log.i("cursor", "存在");

            mapList = new ArrayList<Map<String, String>>();

            while (cursor.moveToNext()) {
                Map<String, String> map = new ArrayMap<String, String>();

                map.put("title", cursor.getString(cursor.getColumnIndex("ItemTitle")));
                map.put("pubdate", cursor.getString(cursor.getColumnIndex("ItemPubdate")));
                map.put("description", cursor.getString(cursor.getColumnIndex("ItemDescription")));
                map.put("itemLink", cursor.getString(cursor.getColumnIndex("ItemLink")));
                map.put("rssName", cursor.getString(cursor.getColumnIndex("RssName")));

                Log.i("appear-item", map.get("title") + ":" + map.get("pubdate") + ":" + map.get("itemLink"));
                mapList.add(map);
            }

            showListView(mapList);
        } else {
            Log.i("ListActivity", "cursor为空");
        }
    }

    //列表显示获取的RSS
    private void showListView(List<Map<String, String>> list) {
        SwipeMenuListView itemlist = (SwipeMenuListView) findViewById(R.id.smlv_rssList);

        mAdapter = new SimpleAdapter(this, list,
                R.layout.item_list_item, new String[]{
                "rssName", "title", "pubdate"
        },
                new int[]{
                        R.id.tv_rssname, R.id.tv_itemname, R.id.tv_itempubdate
                });

        itemlist.setAdapter(mAdapter);
        itemlist.setOnItemClickListener(this);
        itemlist.setSelection(0);

        //注册项目的侧滑选项
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                deleteItem.setWidth(dp2px(70));
                // set item title
                deleteItem.setTitle("Delete");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setBackground(R.color.md_red_500_color_code);
                // add to menu
                menu.addMenuItem(deleteItem);
                // create "delete" item
                SwipeMenuItem staredItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                staredItem.setBackground(R.color.green);
                // set item width
                staredItem.setWidth(dp2px(70));
                // set a icon
                staredItem.setIcon(R.drawable.long_press_unstarred);
                // add to menu
                menu.addMenuItem(staredItem);
            }

        };
        // set creator
        itemlist.setMenuCreator(creator);

        itemlist.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // delete
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SQLiteHandle sqLiteHandle = new SQLiteHandle(ListActivity.this);
                                sqLiteHandle.deleteUnreadItem(mapList.get(position).get("title"));
                                Log.i("删除标记条目", mapList.get(position).get("title"));

                                sqLiteHandle.dbClose();
                                sqLiteHandle = null;

                                mapList.remove(position);

                                Message message = new Message();
                                message.obj = "delete";
                                handler.sendMessage(message);
                            }
                        }).start();
                        break;
                    case 1:
                        // stared
                        menu.getMenuItem(index).setIcon(R.drawable.long_press_starred);
                        Toast.makeText(ListActivity.this, "已添加到标记", Toast.LENGTH_SHORT).show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("new Thread:stared", mapList.get(position).get("title"));

                                //保存sp数据
                                SharedPreferences sharedPreferences =
                                        getSharedPreferences("sp", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                                editor.putBoolean(mapList.get(position).get("title"), true);
                                editor.commit();//提交修改

                                //插入已标记数据表
                                SQLiteHandle sqLiteHandle = new SQLiteHandle(ListActivity.this);
                                sqLiteHandle.insertStaredItem(mapList.get(position).get("rssName"),
                                        mapList.get(position).get("title"),
                                        mapList.get(position).get("pubdate"),
                                        mapList.get(position).get("itemLink"),
                                        false);

                                sqLiteHandle.dbClose();
                                sqLiteHandle = null;
                            }
                        }).start();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        SwipeMenuListView swipeMenuListView = (SwipeMenuListView) findViewById(R.id.smlv_rssList);

        swipeMenuListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
    }

    //处理列表的单击事件
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        Intent itemIntent = new Intent(this, ItemBrowserActivity.class);

        //传递点击的项的数据
        Bundle bundle = new Bundle();

        Log.i("过程打印", mapList.get(position).get("title"));
        Log.i("过程打印", mapList.get(position).get("description"));
        Log.i("过程打印", mapList.get(position).get("pubdate"));
        Log.i("过程打印", mapList.get(position).get("itemLink"));
        Log.i("过程打印", mapList.get(position).get("rssName"));


        bundle.putString("title", mapList.get(position).get("title"));
        bundle.putString("description", mapList.get(position).get("description"));
        bundle.putString("pubdate", mapList.get(position).get("pubdate"));
        bundle.putString("itemLink", mapList.get(position).get("itemLink"));
        bundle.putString("rssName", mapList.get(position).get("rssName"));

        itemIntent.putExtras(bundle);
        startActivity(itemIntent);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


}
