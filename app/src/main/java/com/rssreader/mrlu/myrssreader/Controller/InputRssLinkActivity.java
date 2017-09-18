package com.rssreader.mrlu.myrssreader.Controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.rssreader.mrlu.myrssreader.Model.InternetRequest.RssRequestByOkHttp;
import com.rssreader.mrlu.myrssreader.R;

import butterknife.ButterKnife;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

import static com.rssreader.mrlu.myrssreader.R.color.appBaseColor;

public class InputRssLinkActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtRssLink;
    private TextView mTvRssSearch;
    ProgressDialog progressDialog;

    public int rssItemCount = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            progressDialog.dismiss();

            final String name = (String) msg.obj;

            new AlertDialog.Builder(InputRssLinkActivity.this)
                    .setTitle("添加rss源")

                    .setMessage("是否添加「 " + name + " 」？")
//                    .setMessage("是否添加「 豆瓣最受欢迎的影评 」？")
                    .setPositiveButton("是的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(InputRssLinkActivity.this,
                                    "「" + name + "」已添加",
//                                    "「豆瓣最受欢迎的影评」已添加",
                                    Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNeutralButton("稍后", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

            mEtRssLink.setText("");
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
        mTvRssSearch = (TextView) findViewById(R.id.tv_rss_search);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        ImageView ivIthome = (ImageView) findViewById(R.id.iv_ithome);
        ImageView ivIfar = (ImageView) findViewById(R.id.iv_ifar);
        ImageView ivSongshu = (ImageView) findViewById(R.id.iv_songshu);
        ImageView ivZhihu = (ImageView) findViewById(R.id.iv_zhihu);
        ImageView ivHuxiu = (ImageView) findViewById(R.id.iv_huxiu);
        ImageView ivJianshu = (ImageView) findViewById(R.id.iv_jianshu);
        ImageView ivDouban = (ImageView) findViewById(R.id.iv_doubanmovie);
        ImageView iv36ke = (ImageView) findViewById(R.id.iv_36ke);
        ImageView ivDajia = (ImageView) findViewById(R.id.iv_dajia);
        ImageView ivZuoan = (ImageView) findViewById(R.id.iv_zuoandushu);
        ImageView ivWephone = (ImageView) findViewById(R.id.iv_wephone);


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

//
        ivIthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("打印rssLink", "www.ithome.com/rss");
                getFeed("www.ithome.com/rss");
            }
        });

        iv36ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("打印rssLink", "www.36kr.com/feed");
                getFeed("www.36kr.com/feed");
            }
        });

        ivDajia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("打印rssLink", "http://hanhanone.sinaapp.com/feed/dajia");
                getFeed("http://hanhanone.sinaapp.com/feed/dajia");
            }
        });

        ivDouban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("打印rssLink", "https://movie.douban.com/feed/review/movie");
                getFeed("https://movie.douban.com/feed/review/movie");
            }
        });

        ivHuxiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("打印rssLink", "https://www.huxiu.com/rss/0.xml");
                getFeed("https://www.huxiu.com/rss/0.xml");
            }
        });

        ivIfar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("打印rssLink", "http://ifanr.com/feed");
                getFeed("http://ifanr.com/feed");
            }
        });

        ivJianshu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("打印rssLink", "http://jianshu.milkythinking.com/feeds/recommendations/notes");
                getFeed("http://jianshu.milkythinking.com/feeds/recommendations/notes");
            }
        });

        ivSongshu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("打印rssLink", "http://songshuhui.net/feed");
                getFeed("http://songshuhui.net/feed");
            }
        });

        ivZuoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("打印rssLink", "http://www.zreading.cn/feed");
                getFeed("http://www.zreading.cn/feed");
            }
        });

        ivWephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("打印rssLink", "www.feng.com/rss");
                getFeed("www.feng.com/rss");
            }
        });
//

        //搜索按钮
        mTvRssSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("过程打印", "search已点击");

                final String link = mEtRssLink.getText().toString();

                Log.i("rssLink打印", link);
                getFeed(link);
            }
        });
    }

    private void getFeed(final String link) {
        //等待对话框loading...
        progressDialog = new ProgressDialog(InputRssLinkActivity.this);
        progressDialog.setIndeterminate(false);//循环滚动
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);//false不能取消显示，true可以取消显示
        progressDialog.show();

        //异步请求feed数据，并插入到数据库
        new Thread(new Runnable() {
            @Override
            public void run() {

                RssRequestByOkHttp rssRequestByOkHttp = new RssRequestByOkHttp(InputRssLinkActivity.this);
                rssRequestByOkHttp.getRssFeed(link);

                SystemClock.sleep(800);

                String name = rssRequestByOkHttp.getRssName();

                Message message = new Message();
                message.obj = name;
                handler.sendMessage(message);
            }
        }).start();
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

    @Override
    public void onClick(View v) {
        RssRequestByOkHttp rssRequestByOkHttp = new RssRequestByOkHttp(InputRssLinkActivity.this);
        switch (v.getId()) {
            case R.id.iv_ifar:
                Log.i("打印rssLink", "www.ithome.com/rss");
                getFeed("www.ithome.com/rss");
                break;
        }
    }
}


