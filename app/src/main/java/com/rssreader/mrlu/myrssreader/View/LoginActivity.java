package com.rssreader.mrlu.myrssreader.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rssreader.mrlu.myrssreader.R;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button btnLocalhost = (Button) findViewById(R.id.btn_localhost);
        Button btnFeedly = (Button) findViewById(R.id.btn_feedly);


        btnLocalhost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "该功能将在后续版本提供，敬请期待", Toast.LENGTH_SHORT).show();

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
