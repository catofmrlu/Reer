package com.rssreader.mrlu.myrssreader.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rssreader.mrlu.myrssreader.R;

public class ShowDescriptionActivity extends AppCompatActivity {


    private TextView tvTitile;
    private ScrollView slContent;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_description);

        initView();

        //取出intent传递的数据
        Bundle bundle = getIntent().getExtras();

        String title = bundle.getString("title");
        String pubdate = bundle.getString("pubdate");
        String description = bundle.getString("description");

        if (description != null) {
            if (description.equals(""))
                tvContent.setText("内容已滚回了火星");
            else
                tvContent.setText(description + "ssssssss");
        } else {
            Log.e("ShowDescriptionActivity", "description为空！！");
            tvContent.setText("内容已滚回了火星");
        }

        tvTitile.setText(title);
    }

    private void initView() {

        slContent = (ScrollView) findViewById(R.id.sl_content);
        tvTitile = (TextView) findViewById(R.id.tv_titile);
        tvContent = (TextView) findViewById(R.id.tv_content);
        final ImageView ivStar = (ImageView) findViewById(R.id.iv_star);

        slContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float startY = 0;
                float endY = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        endY = event.getY();
                        break;
                }

                //根据endY-startY的值来判断乡下或向上滑，进而进行处理
                if (startY - endY > 50) {
                    tvTitile.setVisibility(com.mingle.widget.View.GONE);
                } else if (endY - startY > 50)
                    tvTitile.setVisibility(com.mingle.widget.View.VISIBLE);

                return false;
            }
        });

        //标记为星标项目
        ivStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                ivStar.setImageResource(R.drawable.long_press_starred);
                boolean isStared = true;

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                }).start();
            }
        });
    }


}
