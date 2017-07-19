package com.rssreader.mrlu.myrssreader.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rssreader.mrlu.myrssreader.R;

public class ShowDescriptionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_description);

        ScrollView slContent = (ScrollView) findViewById(R.id.sl_content);
        TextView tvTitile = (TextView) findViewById(R.id.tv_titile);

        slContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float startY = 0;
                float endY = 0;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        endY = event.getY();
                        break;

                }

                //根据endY-startY的值来判断乡下或向上滑，进而进行处理

                if (startY - endY > 50)


                return false;
            }
        });

        String content = null;
        Intent startingIntent = getIntent();

        if (startingIntent != null){
            Bundle bundle = startingIntent.getBundleExtra("android.intent.extra.rssItem");

            if (bundle == null){
                content = "不好意思，程序出现错误╮(╯_╰)╭";
            }else {

//                URL url = bundle.getString()
                content = bundle.getString("title") + "\n\n"
                + bundle.getString("pubDate") + "\n\n"
                        + bundle.getString("description").replace('\n', ' ')
                        + "\n\n 详细信息请访问以下网址:\n" + bundle.getString("link");

            }
        }else {
            content = "不好意思，程序出现错误╮(╯_╰)╭";

        }



        TextView textView = (TextView) findViewById(R.id.tv_content);
//        textView.setText(content);

    }


}
