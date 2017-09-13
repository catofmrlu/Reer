package com.rssreader.mrlu.myrssreader.Model.InternetRequest;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rssreader.mrlu.myrssreader.Model.Rss.RSSFeed;

import org.xml.sax.InputSource;

/**
 * Created by LuXin on 2017/4/25.
 */

public class RssVolleyRequest {

    private boolean mResult;
    private String mLink;
    private RequestQueue mRequestQueue;

    private Context mContext;

    private InputSource isc;
    private RSSFeed feed;

    public RssVolleyRequest(Context context) {
        mContext = context;
    }

    public RSSFeed getRssRequest(String urlString) {

        try {

            //新建SAX--xml解析工厂类
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            SAXParser parser = factory.newSAXParser();
//            final XMLReader reader = parser.getXMLReader();
//            final RSSHandler rssHander = new RSSHandler();
//            reader.setContentHandler(rssHander);

            //Volley请求xml部分
            mRequestQueue = Volley.newRequestQueue(mContext);
            StringRequest mStringRequest = new StringRequest(urlString,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.i("respone:", response);


                            Log.i("间隔", "请求执行完成");

                            //转换respone由InputStream为InputSource类型
//                            InputStream is = new ByteArrayInputStream(response.getBytes());
                            try {

//                                if (is != null) {
//
//
//                                    isc = new InputSource(is);
//
//                                    Log.i("IS", "IS转换完成");
//
//                                    Log.i("IS", isc.toString());
//
//                                    reader.parse(isc);
//                                    feed = rssHander.getFeed();

                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    },

                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.e("respone error", "respone错误");
                            Log.e("error", error.getMessage());
                        }
                    });

            mRequestQueue.add(mStringRequest);


        } catch (Exception e) {

            Log.e("Volley请求部分", e.getMessage());
        }

        return feed;

    }
}