package com.rssreader.mrlu.myrssreader.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;
import com.rssreader.mrlu.myrssreader.R;

import butterknife.ButterKnife;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

import static com.rssreader.mrlu.myrssreader.R.color.appBaseColor;


public class InputRssLinkActivity extends AppCompatActivity {

    private EditText mEtRssLink;
    private ImageView mIvRssSearch;

    public int rssItemCount = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            RSSFeed feed = (RSSFeed) msg.obj;


        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_rss_link);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        mEtRssLink = (EditText) findViewById(R.id.et_rssLink);
        mIvRssSearch = (ImageView) findViewById(R.id.iv_rss_search);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);

        StatusBarUtil.setColor(this, getResources().getColor(appBaseColor), 0);

        //设置弹出键盘
//        showSoftInputFromWindow(this, mEtRssLink);

        //返回按钮
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //搜索按钮
        mIvRssSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("过程打印", "search已点击");

                final String link = mEtRssLink.getText().toString();

                Log.i("rssLink打印", link);

                //利用跳转时间，异步请求feed数据，并插入到数据库
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        //判断feed是否为空
//                        if (feed == null) {
//                            Log.e("feed", "feed为空");
//                        } else {
//                            Log.i("恭喜！", "feed通过");
//                            for (Object map :
//                                    feed.getAllItemsForListView()) {
//
//                                HashMap<String, String> hashMap = (HashMap<String, String>) map;
//
//                                Log.i("item", hashMap.get("title"));
//
//                            }
//                        }


                        Log.i("Message", "Message已生成");


                    }
                }).start();


                //跳转到主界面
//                Intent intent = new Intent(InputRssLinkActivity.this, mainView.class);
//                startActivity(intent);

            }
        });

    }

    //键盘自动弹出
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(this, this.getClass().getCanonicalName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JAnalyticsInterface.onPageEnd(this, this.getClass().getCanonicalName());
    }
}


