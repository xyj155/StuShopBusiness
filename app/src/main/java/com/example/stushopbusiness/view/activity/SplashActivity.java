package com.example.stushopbusiness.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.stushopbusiness.R;
import com.example.stushopbusiness.util.GlideUtil;
import com.example.stushopbusiness.util.SharePreferenceUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        GlideUtil.loadRoundCornerAvatarImage(R.mipmap.app_icon, (ImageView) findViewById(R.id.iv_logo), 30);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLogin= (boolean) SharePreferenceUtil.getUser("login","boolean");
                if (isLogin){
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }
                finish();
            }
        }, 3000);

    }
}
