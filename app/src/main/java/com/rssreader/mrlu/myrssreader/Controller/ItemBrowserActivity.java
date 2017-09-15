package com.rssreader.mrlu.myrssreader.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rssreader.mrlu.myrssreader.R;

public class ItemBrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_browser);

        String itemLink = getIntent().getStringExtra("itemLink");

        final WebView wbItem = (WebView) findViewById(R.id.wv_item);
        Log.i("加载WebView", itemLink);
        wbItem.loadUrl(itemLink);

        WebSettings settings = wbItem.getSettings();
        settings.setJavaScriptEnabled(true);

        wbItem.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }

        });
    }
}
