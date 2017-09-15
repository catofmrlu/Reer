package com.rssreader.mrlu.myrssreader.Controller.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.rssreader.mrlu.myrssreader.Controller.ShowDescriptionActivity;
import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
import com.rssreader.mrlu.myrssreader.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LuXin on 2017/2/26.
 */

public class starredFragment extends Fragment implements AdapterView.OnItemClickListener {

    List<Map<String, String>> mapList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        SQLiteHandle sqLiteHandle = new SQLiteHandle(getActivity());
        Cursor cursor = sqLiteHandle.queryAllstaredItems();

        if (cursor != null || cursor.getCount() != 0 || cursor.getCount() != -1) {
            Log.i("cursor", "存在");

            //判断是否有标记项，来处理加载不同的view
            view = inflater.inflate(R.layout.stared_list, container, false);

            mapList = new ArrayList<Map<String, String>>();

            while (cursor.moveToNext()) {
                Map<String, String> map = new ArrayMap<String, String>();

                map.put("title", cursor.getString(cursor.getColumnIndex("ItemTitle")));
                map.put("pubdate", cursor.getString(cursor.getColumnIndex("ItemPubdate")));
                map.put("description", cursor.getString(cursor.getColumnIndex("ItemDescription")));


                Log.i("appear-item", map.get("title") + ":" + map.get("pubdate"));
                mapList.add(map);
            }

            showListView(mapList, view);
        } else {
            Log.i("ListActivity", "cursor为空");
            view = inflater.inflate(R.layout.black_starred, container, false);
        }

        return view;
    }


    //列表显示获取的RSS
    private void showListView(List<Map<String, String>> list, View view) {
        SwipeMenuListView itemlist = (SwipeMenuListView) view.findViewById(R.id.smlv_rssList);

        SimpleAdapter mAdapter = new SimpleAdapter(getActivity(), list,
                android.R.layout.simple_list_item_2, new String[]{
                "title", "pubdate"
        },
                new int[]{
                        android.R.id.text1, android.R.id.text2
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
                        getActivity());
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
                        getActivity());
                // set item background
                staredItem.setBackground(R.color.green);
                // set item width
                staredItem.setWidth(dp2px(70));
                // set a icon
                staredItem.setIcon(R.drawable.long_press_starred);
                // add to menu
                menu.addMenuItem(staredItem);
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
                        Toast.makeText(getActivity(), "已添加到标记", Toast.LENGTH_SHORT).show();
                        break;
                }

                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent itemIntent = new Intent(getActivity(), ShowDescriptionActivity.class);

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
}
