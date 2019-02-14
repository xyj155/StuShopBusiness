package com.example.stushopbusiness.view.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stushopbusiness.R;
import com.example.stushopbusiness.base.BaseActivity;
import com.example.stushopbusiness.contract.LoginContract;
import com.example.stushopbusiness.gson.UserGson;
import com.example.stushopbusiness.presenter.LoginPresenter;
import com.example.stushopbusiness.util.SharePreferenceUtil;
import com.example.stushopbusiness.weight.CustomVideoView;
import com.example.stushopbusiness.weight.toast.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {


    @BindView(R.id.video_login)
    CustomVideoView videoLogin;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return true;
    }

    @Override
    public LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initVideo();
    }

    private void initVideo() {
        //设置播放加载路径
        videoLogin.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.login_video));
        //播放
        videoLogin.start();
        //循环播放
        videoLogin.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoLogin.start();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void showError(String msg) {
        ToastUtils.show("登陆失败，账号或密码错误！");
        mhideDialog();
    }

    @Override
    public void showDialog() {
        createDialog();
    }


    @Override
    public void hideDialog() {
        mhideDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @Override
    public void userlogin(UserGson userGson) {
        Map<String, Object> user = new HashMap<>();
        user.put("username", userGson.getUsername());
        user.put("shopicon", userGson.getShopIcon());
        user.put("shopicon", userGson.getShopIcon());
        user.put("shopname", userGson.getShopName());
        user.put("userId", String.valueOf(userGson.getId()));
        user.put("owner", userGson.getShopOwner());
        user.put("city", userGson.getShopOwnerCity());
        user.put("login", true);
        SharePreferenceUtil.saveUser(user);
        startActivity(MainActivity.class);
        finish();
    }

    @OnClick(R.id.tv_login)
    public void onViewClicked() {
        mPresenter.userlogin(etUsername.getText().toString(), etPassword.getText().toString());
    }
}
