package com.rssreader.mrlu.myrssreader.View;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
import com.rssreader.mrlu.myrssreader.R;

public class InputRssLinkActivity extends AppCompatActivity {


    EditText etRssLink;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_rss_link);

        etRssLink = (EditText) findViewById(R.id.et_rssLink);
        btnOk = (Button) findViewById(R.id.btn_done);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = etRssLink.getText().toString();

                Log.i("rssLink打印", link);

                SQLiteHandle sqLiteHandle = new SQLiteHandle(InputRssLinkActivity.this);
                sqLiteHandle.insertFeed("威锋网", "威锋网", link);

                sqLiteHandle.queryAllFeeds();

                //关闭数据库
                sqLiteHandle.dbClose();

                //传递rss链接到网络请求部分InputRssLink类
                Intent intent = new Intent(InputRssLinkActivity.this, mainView.class);

                Bundle bundle = new Bundle();

                bundle.putString("rssLink", link);

                intent.putExtras(bundle);

                //
                setResult(1, intent);
                finish();
            }
        });
    }



}
