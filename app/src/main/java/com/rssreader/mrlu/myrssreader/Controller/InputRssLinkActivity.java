package com.rssreader.mrlu.myrssreader.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;
import com.rssreader.mrlu.myrssreader.Model.Rss.RssHanderByPull;
import com.rssreader.mrlu.myrssreader.R;


public class InputRssLinkActivity extends AppCompatActivity {


    private EditText mEtRssLink;
    private ImageView mIvRssSearch;

    public int rssItemCount = 0;

    public InputRssLinkActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_rss_link);
        initView();

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
                        getFeed(link);
                    }
                }).start();

                //跳转到主界面
//                Intent intent = new Intent(InputRssLinkActivity.this, mainView.class);
//                startActivity(intent);

            }
        });
    }

    //region getfeed部分
    //获取feed
    private void getFeed(final String urlString) {
        try {
        //调用volley请求xml并解析后返后每个添加源的RssFeed对象
//        RssVolleyRequest rssVolleyRequest = new RssVolleyRequest(InputRssLinkActivity.this);
//        RSSFeed feed = rssVolleyRequest.getRssRequest(urlString);
            RssHanderByPull rssHanderByPull = new RssHanderByPull();

            RSSFeed feed = rssHanderByPull.parseRss(urlString);

                //判断feed是否为空
            if (feed == null) {
                Log.e("feed", "feed为空");
            } else {
                Log.i("恭喜！", "feed通过");

                //统计添加源的项目数
                System.out.println("---------/n" + feed.Count() + "/n------");

                try {

//                    SQLiteHandle sqLiteHandle = new SQLiteHandle(InputRssLinkActivity.this);
//                    sqLiteHandle.insertFeed(feed.getName(), feed.getFeedDescription(), urlString);
//
//                    sqLiteHandle.dbClose();
//
//                    sqLiteHandle = null;
                } catch (Exception e) {
                    Log.e("sqllite插入问题", e.getMessage());

                }
            }
        }catch (Exception e){
            Log.e(" 读取feed", e.toString());
        }finally {

        }
    }

    private void initView() {
        mEtRssLink = (EditText) findViewById(R.id.et_rssLink);
        mIvRssSearch = (ImageView) findViewById(R.id.iv_rss_search);
    }
}


