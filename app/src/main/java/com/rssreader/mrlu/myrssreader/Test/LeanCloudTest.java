package com.rssreader.mrlu.myrssreader.Test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.rssreader.mrlu.myrssreader.R;

public class LeanCloudTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_pull);

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(getBaseContext(),"82qsYPgbphS5SeeyMHa69XlD-gzGzoHsz","VxeyroDmOAcdcq4uEnpgQBXA");


        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words", "Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d("saved", "success!");
                }
            }
        });
    }
}
