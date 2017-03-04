package com.rssreader.mrlu.myrssreader.Model.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rssreader.mrlu.myrssreader.View.LoginActivity;

/**
 * Created by LuXin on 2017/2/28.
 */

public class SQLiteHandle {

    RssSqliteHelper mSqlHelper;
    SQLiteDatabase db;

    public SQLiteHandle(Context context) {

        mSqlHelper = new RssSqliteHelper(context, "Rss", null, 1);

    }

    public void insert(String sqlTable, String rssName, String rssDescription, String rssLink) {
        db = mSqlHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("RssName", rssTitle);
//        values.put("RssDescription", rssDescription);
//        values.put("RssLink", rssLink);
//
//        db.insert(sqlTable, null, values);
        String sql_insert = "insert into " + sqlTable
                + "(RssName, RssPubdate, RssLink) values" + "('" + rssName + "','" + rssDescription
                + "','" + rssLink + "'" + ")";

        //打印SQL执行语句验证是否正确
        Log.i("sql语句验证", sql_insert);

        db.execSQL(sql_insert);
        db.close();

    }

    public void query(String sqlTable) {
        db = mSqlHelper.getWritableDatabase();

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query(sqlTable, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("RssName"));
            String description = cursor.getString(cursor.getColumnIndex("RssDescription"));
            String link = cursor.getString(cursor.getColumnIndex("RssLink"));

            //打印查询的数据
            System.out.println(name + "\n" + description + "\n" + link);

        }
        db.close();

    }
}
