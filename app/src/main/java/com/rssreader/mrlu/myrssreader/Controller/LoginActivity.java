package com.rssreader.mrlu.myrssreader.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.rssreader.mrlu.myrssreader.R;


public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //从sp文件中取出isHasFeed,判断是否显示初始页
        SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
        boolean isHasFeed = sharedPreferences.getBoolean("isHasFeed", false);

        //如果数据库存在feed，直接跳转到mainView
        if (isHasFeed) {
            Log.i("过程打印", "存在feed，跳转到mainview");

            Intent intent = new Intent(this, mainView.class);

            Bundle bundle = new Bundle();


            startActivity(intent);

        } else {


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            StatusBarUtil.setColor(this,getResources().getColor(R.color.log),0);


            Button btnLocalhost = (Button) findViewById(R.id.btn_localhost);
            Button btnFeedly = (Button) findViewById(R.id.btn_feedly);



            btnLocalhost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(LoginActivity.this, "该功能将在后续版本提供，敬请期待", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, mainView.class);

                    startActivity(intent);
                    finish();

                    //插入sp文件设置值isHasFeed,再次启动app时不再显示login页面
                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("sp", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器

                    editor.putBoolean("isHasFeed", true);

                    editor.commit();//提交修改
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
