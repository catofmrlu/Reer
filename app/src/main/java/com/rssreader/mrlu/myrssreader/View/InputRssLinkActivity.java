package com.rssreader.mrlu.myrssreader.View;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

                try {

                    SQLiteHandle sqLiteHandle = new SQLiteHandle(InputRssLinkActivity.this);
                    sqLiteHandle.insertFeed("威锋网", "威锋网", link);

//                    sqLiteHandle.queryAllFeeds();

                    //关闭数据库
                    sqLiteHandle.dbClose();
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


}
