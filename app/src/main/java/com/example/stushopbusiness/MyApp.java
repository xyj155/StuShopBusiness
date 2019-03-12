package com.example.stushopbusiness;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.stushopbusiness.util.ApplicationInitial;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;



public class MyApp extends Application {

    private static MyApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
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
                .initJpush()
                .initMob()
                .initRongIm()
                .initPermission()
                .initToast()
                .initX5();

    }

    public static Application getInstance() {
        return instance;
    }
}
