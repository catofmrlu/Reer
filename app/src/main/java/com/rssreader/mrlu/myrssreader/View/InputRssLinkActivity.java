package com.rssreader.mrlu.myrssreader.View;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
import com.rssreader.mrlu.myrssreader.R;

public class InputRssLinkActivity extends AppCompatActivity {


    private EditText mEtRssLink;
    private ImageView mIvRssSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_rss_link);
        initView();

        mIvRssSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("过程打印", "search已点击");

                String link = mEtRssLink.getText().toString();

                Log.i("rssLink打印", link);

                try {
                    SQLiteHandle sqLiteHandle = new SQLiteHandle(InputRssLinkActivity.this);
                    sqLiteHandle.insertFeed("威锋网", "威锋网", link);

                    Log.i("间隔", "------------------");

//                    sqLiteHandle.queryAllFeeds();

                    //关闭数据库
                    sqLiteHandle.dbClose();
                    sqLiteHandle = null;
//
//                    sqLiteHandle = new SQLiteHandle(InputRssLinkActivity.this);
//                    sqLiteHandle.queryAllFeeds("AllFeeds");
//                    sqLiteHandle.dbClose();

                } catch (SQLException e) {
                    Log.e("数据库添加feed问题", e.getMessage());
                }

                //传递rss链接到网络请求部分InputRssLink类
//                Intent intent = new Intent(InputRssLinkActivity.this, mainView.class);
//
//                Bundle bundle = new Bundle();
//
//                bundle.putString("rssLink", link);
//
//                intent.putExtras(bundle);
//
//                startActivity(intent);
//
//                setResult(1, intent);
//                finish();
            }
        });
    }


    private void initView() {
        mEtRssLink = (EditText) findViewById(R.id.et_rssLink);
        mIvRssSearch = (ImageView) findViewById(R.id.iv_rss_search);
    }

    private void submit() {
        // validate
        String rssLink = mEtRssLink.getText().toString().trim();
        if (TextUtils.isEmpty(rssLink)) {
            Toast.makeText(this, "请输入rss", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
