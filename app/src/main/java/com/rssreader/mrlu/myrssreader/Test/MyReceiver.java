package com.rssreader.mrlu.myrssreader.Test;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Log.i("接收者服务", "MyReceiver已调用");

        String bast = intent.getAction();

        Log.i("接收者服务", "broadcast:" + bast);

        Intent intent1 = new Intent(context, MyService.class);
        context.startService(intent1);


    }
}
