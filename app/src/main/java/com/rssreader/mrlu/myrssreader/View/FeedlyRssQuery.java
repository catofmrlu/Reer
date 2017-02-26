package com.rssreader.mrlu.myrssreader.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rssreader.mrlu.myrssreader.R;

public class FeedlyRssQuery extends AppCompatActivity {

    RequestQueue mRequestQueue;

    Button btnQuery;
    EditText etQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedly_rss_query);


        btnQuery = (Button) findViewById(R.id.btn_rssQuery);
        etQuery = (EditText) findViewById(R.id.et_rssQuery);

        String rssQuery = etQuery.getText().toString();
        Log.i("rssQuery打印", "rssQuery为" + rssQuery);


        //volley网络请求部分
        mRequestQueue = Volley.newRequestQueue(this);
        StringRequest mStringRequest = new StringRequest(rssQuery,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("respone", "resspone为:" + response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("respone响应失败", "error为：" + error);
                    }
                }


        );

        mRequestQueue.add(mStringRequest);
    }
}

