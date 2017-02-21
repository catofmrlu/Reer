package com.rssreader.mrlu.myrssreader.Test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.rssreader.mrlu.myrssreader.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkTestActivity extends AppCompatActivity {

//    Handler mHadler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 1) {
//                String responeUrl = (String) msg.obj;
//
//                //更新UI...
//                tvNetWork.setText(responeUrl);
//
//            }
//        }
//    };


    OkHttpClient mOkHttpClient;
//        String responeNode;
    public String RSS_URL = "http://free.apprcn.com/category/ios/feed/";
//            "http://free.apprcn.com/category/ios/feed/";


    TextView tvNetWork;
    String responeNode;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_network_test);

        tvNetWork = (TextView) findViewById(R.id.tv_netWorkStaus);

        URL url = null;
        try {

            url = new URL(RSS_URL);
            MyTask mMyTask = new MyTask();
            mMyTask.execute(url);

//            tvNetWork.setText(responeNode);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

//        download();
    }


//    private void download() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // 这里进行下载操作...获得了图片的bitmap
//                //下载完后才，向主线程发送Message
//
//                try {
//
//
//                    URL url = new URL(RSS_URL);
//
//                    System.out.println("进入download");
//
//
//                    mOkHttpClient = new OkHttpClient();
//                    Request.Builder requestBuilder = new Request.Builder().url(url);
//
//                    //可以省略，默认是GET请求
//                    requestBuilder.method("GET", null);
//                    Request request = requestBuilder.build();
//                    Call mcall = mOkHttpClient.newCall(request);
//
//                    mcall.enqueue(new Callback() {
//
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            Log.e("FailureNetWork", e.toString());
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            // TODO Auto-generated method stub
//                            if (response.isSuccessful()) {
//                                responeNode = "网络有响应：" + response.body().toString();
//                                System.out.println(responeNode);
//                            } else {
//                                System.out.println("网络响应失败");
//                            }
//                        }
//                    });
//
//
//                    Message msg = Message.obtain();
//                    msg.obj = responeNode;
//                    msg.what = 1;//区分哪一个线程发送的消息
//                    mHadler.sendMessage(msg);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    System.out.println("以上为网络请求部分");
//                }
//
//            }


    class MyTask extends AsyncTask<URL, Void, String> {


        @Override
        protected String doInBackground(URL... params) {

            String text = params[0].toString();

            System.out.println(text);

            try {

//                URL url = new URL(
//                        "http://192.168.1.100:8080/myweb/hello.jsp?username=abc");
//                // 得到HttpURLConnection对象
//                HttpURLConnection connection = (HttpURLConnection) params[0]
//                        .openConnection();
//                // 设置为GET方式
//                connection.setRequestMethod("GET");
//                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    // 得到响应消息
//                    responeNode = connection.getResponseMessage();
//
//                    System.out.println("返回值：" + responeNode);
//
//                }
//            }

//                URLConnection  conn = params[0].openConnection();
//
//                conn.setRequestProperty("accept", "*/*");
//
//                conn.connect();
//                System.out.println("已连接");


//                // 直接使用URLConnection对象进行连接
//                URL url = new URL("http://192.168.1.100:8080/myweb/hello.jsp");
//                // 得到URLConnection对象
//                URLConnection connection = url.openConnection();
//                InputStream is = conn.getInputStream();
//                byte[] bs = new byte[1024];
//                int len = 0;
//                StringBuffer sb = new StringBuffer();
//                while ((len = is.read(bs)) != -1) {
//                    String str = new String(bs, 0, len);
//                    System.out.println(str);
//                    sb.append(str);
//
//                }
//                showTextView.setText(sb.toString());
//                responeNode = conn.getInputStream().toString();

//                responeNode = sb.toString();


//                    /okhttp3 网络连接

                mOkHttpClient = new OkHttpClient();
                URL url1 = params[0];
                Request.Builder requestBuilder = new Request.Builder().url(url1);

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
                            responeNode = "网络有响应：" + response.body().string();
                            System.out.println(responeNode);

//                            String responeNode1 = "网络有响应：" + response.cacheResponse().body().toString();
//                            System.out.println(responeNode1);
//
//                            String responeNode2 = "网络有响应：" + response.networkResponse().body().toString();
//                            System.out.println(responeNode2);


                        } else {
                            System.out.println("网络响应失败");
                        }
                    }
                });
//
            }catch (Exception e) {
                System.out.println("网络出现问题");

                Log.e("internet", "网络出现问题：" + e.toString());
            } finally {
                System.out.println("以上为网络请求部分");
            }


            return responeNode;
        }

        @Override
        protected void onPostExecute(String params) {


//            SystemClock.sleep(2000);
            System.out.println("返回值为------------------------------------：" + params);

//
//            tvNetWork.setText(s);

            tvNetWork.setText(responeNode);
//            try {
//
//
//                tvNetWork.setText(responeNode);
//
//            } catch (Exception e) {
//                e.toString();
//            }
        }
//        @Override
//        protected Object doInBackground(URL url) {
//            Object ob = null;
//
//            try {
//
//
////                    /okhttp3 网络连接
//
//                mOkHttpClient = new OkHttpClient();
//                URL url1 = (URL) params[0];
//                Request.Builder requestBuilder = new Request.Builder().url(url1);
//
//                //可以省略，默认是GET请求
//                requestBuilder.method("GET", null);
//                Request request = requestBuilder.build();
//                Call mcall = mOkHttpClient.newCall(request);
//
//                mcall.enqueue(new Callback() {
//
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.e("FailureNetWork", e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        // TODO Auto-generated method stub
//                        if (response.isSuccessful()) {
//                            responeNode = "网络有响应：" + response.body().toString();
//                            System.out.println(responeNode);
//
//                        } else {
//                            System.out.println("网络响应失败");
//                        }
//                    }
//                });
//                ob = responeNode;
//
//            } catch (Exception e) {
//                System.out.println("网络出现问题");
//
//                Log.e("internet", "网络出现问题：" + e.toString());
//            } finally {
//                System.out.println("以上为网络请求部分");
//            }
//
//
//            return ob;


//        }
    }
}

