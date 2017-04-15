package com.rssreader.mrlu.myrssreader.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rssreader.mrlu.myrssreader.Model.Sqlite.RssSqliteHelper;
import com.rssreader.mrlu.myrssreader.R;


public class LoginActivity extends AppCompatActivity {

    RssSqliteHelper mSqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //从sp文件中取出isHasFeed,判断是否显示初始页
        SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
        boolean isHasFeed = sharedPreferences.getBoolean("isHasFeed", false);

        //如果数据库存在feed，直接跳转到mainView
        if (isHasFeed) {

            Intent intent = new Intent(this, mainView.class);

            Bundle bundle = new Bundle();


            startActivity(intent);

        } else {


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

                    } catch (Exception e) {
                        Log.e("database", "问题在：" + e.toString());
                    }

                }
            }).start();


            btnLocalhost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(LoginActivity.this, "该功能将在后续版本提供，敬请期待", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, mainView.class);

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
}
