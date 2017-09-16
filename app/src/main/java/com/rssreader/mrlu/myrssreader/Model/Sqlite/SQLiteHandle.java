package com.rssreader.mrlu.myrssreader.Model.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by LuXin on 2017/2/28.
 */

public class SQLiteHandle {
    private Context mContext;

    private SQLiteDatabase db;

    public SQLiteHandle(Context context) {
        mContext = context;
        db = RssSqliteHelper.getInstance(context);
        Log.i("sqlite", "db打开");
    }

    public void insertFeed(String rssName, String rssDescription,
                           String rssLink, int itemsCount, Boolean isAppear) {
        Log.i("sqlite", "开始");

        int IsAppear;
        //boolean转化为int类型
        if (isAppear)
            IsAppear = 1;
        else
            IsAppear = 0;

        db.beginTransaction();

        String sql_insert = "insert into AllFeeds"
                + "(RssName, RssDescription, RssLink, ItemsCount, IsAppear) values"
                + "('" + rssName + "','" + rssDescription
                + "','" + rssLink + "','" + itemsCount + "','" + IsAppear + "')";

        //打印SQL执行语句验证是否正确
        Log.i("sql语句验证", sql_insert);

        db.execSQL(sql_insert);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void insertUnreadItem(String rssName, String itemName, String itemPubdate, String itemLink, String ItemDescription) {
        Log.i("sqlite", "开始");

        db.beginTransaction();

        String sql_insert = "insert into unReadItems"
                + "(RssName, ItemTitle, ItemPubdate, ItemLink, ItemDescription) values"
                + "('" + rssName + "','" + itemName
                + "','" + itemPubdate
                + "','" + itemLink
                + "','" + ItemDescription + "')";

        //打印SQL执行语句验证是否正确
        Log.i("sql语句验证", sql_insert);

        db.execSQL(sql_insert);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void insertStaredItem(String rssName, String itemName, String itemPubdate, String itemLink, boolean isAppear) {
        Log.i("sqlite", "开始");

        int IsAppear;

        //boolean转化为int类型
        if (isAppear)
            IsAppear = 1;
        else
            IsAppear = 0;

        db.beginTransaction();

        String sql_insert = "insert into StarItems"
                + "(RssName, ItemTitle, ItemPubdate, ItemLink, IsAppear) values"
                + "('" + rssName + "','" + itemName
                + "','" + itemPubdate
                + "','" + itemLink + "','"
                + IsAppear + "')";

        //打印SQL执行语句验证是否正确
        Log.i("sql语句验证", sql_insert);

        db.execSQL(sql_insert);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void deleteStaredItem(String itemName) {
        Log.i("sqlite", "开始");
        String where = "ItemTitle = '" + itemName + "'";

        db.beginTransaction();

        db.delete("StarItems", where, null);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void logAllFeeds() {
//        db = mSqlHelper.getWritableDatabase();
        Log.i("sqlite", "开始");

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query("AllFeeds", null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("RssName"));
                String description = cursor.getString(cursor.getColumnIndex("RssDescription"));
                String link = cursor.getString(cursor.getColumnIndex("RssLink"));
                int count = cursor.getInt(cursor.getColumnIndex("ItemsCount"));

                //打印查询的数据
                System.out.println(name + "---" + description + "---" + link);
            }

        } else {
            Log.e("查询allFeeds", "没有数据！！");
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        cursor.close();
    }

    public Cursor queryAllFeeds() {
        Log.i("sqlite", "开始");

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query("AllFeeds", null, null, null, null, null, null);

        db.setTransactionSuccessful();
        db.endTransaction();

        return cursor;
    }

    public Cursor queryUnappearFeeds() {
        Log.i("sqlite", "开始");

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query("AllFeeds", null, "IsAppear = '0'", null, null, null, null);

        db.setTransactionSuccessful();
        db.endTransaction();

        return cursor;
    }

    public Cursor queryAllUnreadItems() {
        Log.i("sqlite", "开始");

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query("unReadItems", null, null, null, null, null, null);

        Log.i("unReadItems数量", "共计有：「" + String.valueOf(cursor.getCount()) + "」项");

        db.setTransactionSuccessful();
        db.endTransaction();

        return cursor;
    }

    public Cursor queryFeedUnreadItems(String feedName) {
        Log.i("sqlite", "开始");

        //开启事务
        db.beginTransaction();

        String selection = "RssName = " + "'" + feedName + "'";
        Log.i("selection语句", selection);

        Cursor cursor = db.query("unReadItems", null, selection, null, null, null, null);

        Log.i("unReadItems数量", "共计有：「" + String.valueOf(cursor.getCount()) + "」项");

        db.setTransactionSuccessful();
        db.endTransaction();

        return cursor;
    }

    public Cursor queryAllstaredItems() {
        Log.i("sqlite", "开始");

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query("StarItems", null, null, null, null, null, null);

        Log.i("unReadItems数量", "共计有：「" + String.valueOf(cursor.getCount()) + "」项");

        db.setTransactionSuccessful();
        db.endTransaction();

        return cursor;
    }

    public Cursor queryUnappearstaredItems() {
        Log.i("sqlite", "开始");

        String selection = "IsAppear = '0'";
        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query("StarItems", null, selection, null, null, null, null);

        Log.i("UnappearstaredItems数量", "共计有：「" + String.valueOf(cursor.getCount()) + "」项");

        db.setTransactionSuccessful();
        db.endTransaction();

        return cursor;
    }

    public int queryAllFeedsCount() {
        Log.i("sqlite", "开始");

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query("AllFeeds", null, null, null, null, null, null);
        db.setTransactionSuccessful();

        db.endTransaction();

        return cursor.getCount();
    }

    public boolean queryHasFeed() {
        Log.i("sqlite", "开始");

        boolean isHasFeed;

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query("AllFeeds", null, null, null, null, null, null);

        if (cursor.getCount() != 0)
            isHasFeed = true;
        else
            isHasFeed = false;

        db.setTransactionSuccessful();
        db.endTransaction();

        return isHasFeed;
    }

    public boolean urlQuery(String url) {
        Log.i("sqlite", "开始");

        boolean isUrl = false;

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

        db.setTransactionSuccessful();
        db.endTransaction();

        return isUrl;
    }

    public void dbClose() {
        db.close();
        Log.i("sqlite", "关闭");
    }

    public void updateUnAppearFeeds() {

        ContentValues contentValues = new ContentValues();
        contentValues.put("IsAppear", 1);

        db.beginTransaction();

        db.update("AllFeeds", contentValues, "IsAppear = '0'", null);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void updateUnAppearStaredItems() {

        ContentValues contentValues = new ContentValues();
        contentValues.put("IsAppear", 1);

        db.beginTransaction();

        db.update("StarItems", contentValues, "IsAppear = '0'", null);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public boolean isHasItem(String sqliteTable) {
        Log.i("sqlite", "开始");

        boolean isHasItem;

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query(sqliteTable, null, null, null, null, null, null);

        db.setTransactionSuccessful();
        db.endTransaction();

        Log.i("unReadItems数量", "共计有：「" + String.valueOf(cursor.getCount()) + "」项");

        if (cursor.getCount() == 0 || cursor == null)
            isHasItem = false;
        else
            isHasItem = true;

        return isHasItem;
    }

    public boolean isHasFeed(String feedName) {
        Log.i("sqlite", "开始");

        boolean isHasItem;
        String selection = "RssName = '" + feedName + "'";

        //开启事务
        db.beginTransaction();

        Cursor cursor = db.query("AllFeeds", null, selection, null, null, null, null);

        db.setTransactionSuccessful();
        db.endTransaction();

        Log.i("unReadItems数量", "共计有：「" + String.valueOf(cursor.getCount()) + "」项");

        if (cursor.getCount() == 0 || cursor == null)
            isHasItem = false;
        else
            isHasItem = true;

        return isHasItem;
    }

    public void sqliteExect(String sqliteExect) {

        db.beginTransaction();

        db.execSQL(sqliteExect);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

}
