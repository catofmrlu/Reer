package com.rssreader.mrlu.myrssreader.View;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rssreader.mrlu.myrssreader.Model.Sqlite.RssSqliteHelper;
import com.rssreader.mrlu.myrssreader.Model.Sqlite.SQLiteHandle;
import com.rssreader.mrlu.myrssreader.R;


public class LoginActivity extends AppCompatActivity {

    RssSqliteHelper mSqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button btnLocalhost = (Button) findViewById(R.id.btn_localhost);
        Button btnFeedly = (Button) findViewById(R.id.btn_feedly);

//        创建数据库及数据表
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    mSqlHelper = new RssSqliteHelper(LoginActivity.this, "Rss", null, 1);
                    mSqlHelper.getWritableDatabase();

                    SQLiteHandle mSqlHandle = new SQLiteHandle(LoginActivity.this);
                    mSqlHandle.insert("AllFeeds", "SSSS", "dddd", "11ssssddssss");

                    Log.i("数据库插入", "插入成功！！");

                    mSqlHandle.query("AllFeeds");

                }catch (Exception e){
                    Log.e("database", "问题在：" + e.toString());
                }

            }
        }).start();


        btnLocalhost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(LoginActivity.this, "该功能将在后续版本提供，敬请期待", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, InputRssLinkActivity.class);

                startActivity(intent);
            }
        });

        btnFeedly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "正在解决feedly的sdk问题，即将推出", Toast.LENGTH_SHORT).show();

            }
        });




    }
}
