package com.rssreader.mrlu.myrssreader.Test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.rssreader.mrlu.myrssreader.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkTestActivity extends AppCompatActivity {

    OkHttpClient mOkHttpClient;
    public String RSS_URL = "http://www.baidu.com";
//            "http://free.apprcn.com/category/ios/feed/";


    TextView tvNetWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_network_test);

        try {

            ///okhttp3 网络连接
            URL url = new URL(RSS_URL);

            mOkHttpClient = new OkHttpClient();
            Request.Builder requestBuilder = new Request.Builder().url(url);

            //可以省略，默认是GET请求
            requestBuilder.method("GET", null);
            Request request = requestBuilder.build();
            Call mcall = mOkHttpClient.newCall(request);


            mcall.enqueue(new Callback() {


                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("FailureNetWork", e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // TODO Auto-generated method stub
                    if (response.isSuccessful()) {
                        tvNetWork.setText("网络有响应：" + response.body().toString());
                    } else {
                        System.out.println("网络响应失败");
                    }
                }


            });


            {
              /*  @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("netWork", e.toString());

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (null != response.cacheResponse()) {
                        String str = response.cacheResponse().toString();
                        Log.i("wangshu", "cache---" + str);
                        tvNetWork.setText("获取成功了！！/n" + "cache---" + str);

                    } else {
                        response.body().string();
                        String str = response.networkResponse().toString();
                        Log.i("wangshu", "network---" + str);
                        tvNetWork.setText("获取成功了！！/n" + "network---" + str);

                    }
                }*/
            }

        } catch (MalformedURLException e) {
            Log.e("url", e.toString());
        } finally {
            System.out.println("以上为网络请求部分");
        }
    }
}
