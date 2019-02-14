package com.example.stushopbusiness;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.stushopbusiness.util.ApplicationInitial;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;

public class MyApp extends Application {

    private static MyApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JMessageClient.registerEventReceiver(this);
        JMessageClient.setDebugMode(true);
        JMessageClient.init(MyApp.getInstance(), true);
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        ApplicationInitial applicationInitial = new ApplicationInitial();
        applicationInitial
                .initArouter()
                .initBuygly()
                .initEpay()
                .initIMClient()
                .initJpush()
                .initMob()
                .initPermission()
                .initToast()
                .initX5();

    }
    public void onEventMainThread(MessageEvent event) {
        //do your own business

    }

    public void onEvent(NotificationClickEvent event) {

    }
    public static Application getInstance() {
        return instance;
    }
}
