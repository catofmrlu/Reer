package com.rssreader.mrlu.myrssreader.Model.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by LuXin on 2017/2/28.
 */

public class RssSqliteHelper extends SQLiteOpenHelper {

    public RssSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table unReadItems" + "(" + "_id integer primary key,"
                + "ItemTitle varchar,"
                + "ItemPubdate varchar,"
                + "ItemLink varchar)");
        Log.i("创建数据表", "创建未读items表成功！");


        db.execSQL("create table ReadItems" + "(" + "_id integer primary key,"
                + "RssTitle varchar,"
                + "RssDescription varchar,"
                + "RssLink varchar)");
        Log.i("创建数据表", "创建已读items表成功！");


        db.execSQL("create table StarItems" + "(" + "_id integer primary key,"
                + "RssTitle varchar,"
                + "RssDescription varchar,"
                + "RssLink varchar)");
        Log.i("创建数据表", "创建标记items表成功！");


        db.execSQL("create table AllFeeds" + "(" + "_id integer primary key,"
                + "RssName varchar,"
                + "RssDescription varchar,"
                + "RssLink varchar)");
        Log.i("创建数据表", "创建全部feed表成功！");


        Log.i("创建数据表", "--------------------------------------------\n创建全部表成功！");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
