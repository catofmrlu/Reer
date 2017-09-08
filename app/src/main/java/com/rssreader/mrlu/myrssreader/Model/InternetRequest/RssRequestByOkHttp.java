package com.rssreader.mrlu.myrssreader.Model.InternetRequest;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by luxin on 2017/9/8.
 */

public class RssRequestByOkHttp {

    private Context mContext;

    public RssRequestByOkHttp(Context context) {
        this.mContext = context;
    }

    public String getRssReturnString(String rssLink){

        final String[] rssXml = {null};

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(rssLink);
        builder.method("GET", null);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                rssXml[0] = response.body().string();
                Log.i("rssXml返回", "onResponse: " + rssXml[0]);
            }
        });

        return rssXml[0];

    }
}
