package com.rssreader.mrlu.myrssreader.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
import com.rssreader.mrlu.myrssreader.R;

public class ItemBrowserActivity extends AppCompatActivity {

    private ImageView ivShare;
    private ImageView ivStared;
    private String itemLink;
    private String title;
    private String pubdate;
    private String rssName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_browser);

        ivShare = (ImageView) findViewById(R.id.iv_share);
        ivStared = (ImageView) findViewById(R.id.iv_star);
        WebView wvItem = (WebView) findViewById(R.id.wv_item);

        //取出intent传递的数据
        Bundle bundle = getIntent().getExtras();

        title = bundle.getString("title");
        itemLink = bundle.getString("itemLink");
        pubdate = bundle.getString("pubdate");
        rssName = bundle.getString("rssName");

        //从sp文件中取出isHasFeed,判断是否显示初始页
        SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);

        boolean isStared = sharedPreferences.getBoolean(title, false);

        //如果已经标记过，设置星标为已标记样式
        if (isStared)
            ivStared.setImageResource(R.drawable.long_press_starred);

        TextView tvWebItemName = (TextView) findViewById(R.id.tv_webitemname);
        tvWebItemName.setText(title);
        tvWebItemName.setSelected(true);

        final WebView wbItem = (WebView) findViewById(R.id.wv_item);
        Log.i("加载WebView", itemLink);
        wbItem.loadUrl(itemLink);
//
//        WebSettings settings = wbItem.getSettings();
//        settings.setJavaScriptEnabled(true);

        wbItem.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

        //标记为星标项目
        ivStared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ivStared.setImageResource(R.drawable.long_press_starred);
                Toast.makeText(ItemBrowserActivity.this, "项目已标记", Toast.LENGTH_SHORT);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("new Thread:stared", title);

                        //保存sp数据
                        SharedPreferences sharedPreferences =
                                getSharedPreferences("sp", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putBoolean(title, true);
                        editor.commit();//提交修改

                        //插入已标记数据表
                        SQLiteHandle sqLiteHandle = new SQLiteHandle(ItemBrowserActivity.this);
                        sqLiteHandle.insertStaredItem(rssName, title, pubdate, itemLink);

                        sqLiteHandle.dbClose();
                        sqLiteHandle = null;
                    }
                }).start();
            }
        });

        //分享按钮点击执行分享操作
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "这篇文章于君或有益哦：「" + title + "」" + ":"
                        + itemLink);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });
    }


}
