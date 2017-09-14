package com.rssreader.mrlu.myrssreader.Controller;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
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
                map.put("description", cursor.getString(cursor.getColumnIndex("ItemDescription")));


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

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.long_press_starred);
                // add to menu
                menu.addMenuItem(deleteItem);
            }


        };
// set creator
        itemlist.setMenuCreator(creator);

        itemlist.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // stared
                        Toast.makeText(ListActivity.this, "已添加到标记", Toast.LENGTH_SHORT).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    //处理列表的单击事件
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        Intent itemIntent = new Intent(this, ShowDescriptionActivity.class);

        //传递点击的项的数据
        Bundle bundle = new Bundle();

        Log.i("过程打印", mapList.get(position).get("title"));
        Log.i("过程打印", mapList.get(position).get("description"));
        Log.i("过程打印", mapList.get(position).get("pubdate"));

        bundle.putString("title", mapList.get(position).get("title"));
        bundle.putString("description", mapList.get(position).get("description"));
        bundle.putString("pubdate", mapList.get(position).get("pubdate"));

        itemIntent.putExtras(bundle);

        startActivity(itemIntent);

    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


}
