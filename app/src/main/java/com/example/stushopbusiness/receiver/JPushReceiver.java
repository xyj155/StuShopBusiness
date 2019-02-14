package com.example.stushopbusiness.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();// 获取其他的推送信息
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.i(TAG,"JPush用户注册成功id: " + regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
            Log.i(TAG,"接受到推送下来的自定义消息id: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            // 在里可以做些统计，或者做些其他工作
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.i(TAG,"接受到推送下来的通知id: " + notifactionId);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            // 在这里可以自己写代码去定义用户点击后的行为
            Log.i(TAG,"用户点击打开了通知");
        } else {
            Log.i(TAG,"Unhandled intent :" + intent.getAction());
        }
    }
}