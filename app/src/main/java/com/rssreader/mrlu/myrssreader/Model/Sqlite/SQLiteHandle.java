package com.rssreader.mrlu.myrssreader.Model.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.security.Principal;
import java.security.PublicKey;

/**
 * Created by LuXin on 2017/2/28.
 */

public class SQLiteHandle {

    private RssSqliteHelper mSqlHelper;
    private SQLiteDatabase db;

    public SQLiteHandle(Context context) {

        db = mSqlHelper.getInstance(context);

    }

    public  void  insertFeed(String rssName, String rssDescription, String rssLink) {
//        db = mSqlHelper.getWritableDatabase();

//        ContentValues values = new ContentValues();
//        values.put("RssName", rssTitle);
//        values.put("RssDescription", rssDescription);
//        values.put("RssLink", rssLink);
//
//        db.insert(sqlTable, null, values);

        String sql_insert = "insert into AllFeeds"
                + "(RssName, RssDescription, RssLink) values" + "('" + rssName + "','" + rssDescription
                + "','" + rssLink + "'" + ")";

        //打印SQL执行语句验证是否正确
        Log.i("sql语句验证", sql_insert);

        db.execSQL(sql_insert);
        db.close();

    }

    public  void queryAllFeeds(String sqlTable) {
//        db = mSqlHelper.getWritableDatabase();

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query("AllFeeds", null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("RssName"));
                String description = cursor.getString(cursor.getColumnIndex("RssDescription"));
                String link = cursor.getString(cursor.getColumnIndex("RssLink"));

                //打印查询的数据
                System.out.println(name + "---" + description + "---" + link);

            }

        }else {
            Log.e("查询allFeeds", "没有数据！！");
        }

        cursor.close();
        db.close();

    }


    public  Cursor queryAllFeeds(){

//        db = mSqlHelper.getWritableDatabase();

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query("AllFeeds", null, null, null, null, null, null);

        db.close();

        return cursor;

    }

    public  boolean queryHasFeed(){

        boolean isHasFeed;
//        db = mSqlHelper.getWritableDatabase();

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query("AllFeeds", null, null, null, null, null, null);

        if (cursor != null)
            isHasFeed = true;
        else
            isHasFeed = false;

        db.close();

        return isHasFeed;
    }

    public  boolean urlQuery(String url) {

        boolean isUrl = false;

//        db = mSqlHelper.getWritableDatabase();

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query("AllFeeds", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String link = cursor.getString(cursor.getColumnIndex("RssLink"));

            if (url.equals(link.trim())) {
                isUrl = true;
                break;

            } else {
                isUrl = false;
            }
        }

        db.close();

        return isUrl;
    }
}
