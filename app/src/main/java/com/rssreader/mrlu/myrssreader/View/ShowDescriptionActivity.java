package com.rssreader.mrlu.myrssreader.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rssreader.mrlu.myrssreader.R;

public class ShowDescriptionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_description);

        String content = null;
        Intent startingIntent = getIntent();

        if (startingIntent != null){
            Bundle bundle = startingIntent.getBundleExtra("android.intent.extra.rssItem");

            if (bundle == null){
                content = "不好意思，程序出现错误╮(╯_╰)╭";
            }else {
                content = bundle.getString("title") + "\n\n"
                + bundle.getString("pubDate") + "\n\n"
                        + bundle.getString("description").replace('\n', ' ')
                        + "\n\n 详细信息请访问以下网址:\n" + bundle.getString("link");

            }
        }else {
            content = "不好意思，程序出现错误╮(╯_╰)╭";

        }

        TextView textView = (TextView) findViewById(R.id.content);
        textView.setText(content);

        Button backBtn = (Button) findViewById(R.id.back);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
