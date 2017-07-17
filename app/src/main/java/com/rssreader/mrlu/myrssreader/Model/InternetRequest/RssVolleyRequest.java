package com.rssreader.mrlu.myrssreader.Model.InternetRequest;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by LuXin on 2017/4/25.
 */

public class RssVolleyRequest {

    private boolean mResult;

    private String mLink;
    private RequestQueue mRequestQueue;

    private Context mContext;

    RssVolleyRequest(Context context){
        mContext = context;

        }

    private boolean getRssRequest(String urlString) {

            try {

                mRequestQueue = Volley.newRequestQueue(mContext);
                StringRequest mStringRequest = new StringRequest(urlString,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.i("respone:", response);


                                Log.i("间隔", "请求执行完成");

//                                InputStream is = new ByteArrayInputStream(response.getBytes());

                                mResult = true;

                            }
                        },
                                    new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Log.e("error", error.getMessage());
                                    mResult = false;
                                }
                            }
                );

                mRequestQueue.add(mStringRequest);

        }catch (Exception e){
                Log.e("Volley请求", e.getMessage());

            }

            return mResult;


    }

}
