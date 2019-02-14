package com.example.stushopbusiness.view.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.stushopbusiness.R;
import com.example.stushopbusiness.base.BaseActivity;
import com.example.stushopbusiness.contract.EmptyContract;
import com.example.stushopbusiness.presenter.EmptyPresenter;
import com.example.stushopbusiness.util.RouterUtil;
import com.example.stushopbusiness.view.fragment.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<EmptyContract.View, EmptyPresenter> {


    @BindView(R.id.flContainer)
    FrameLayout flContainer;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_resource)
    RadioButton rbResource;
    @BindView(R.id.rb_message)
    RadioButton rbMessage;
    @BindView(R.id.bottomBar)
    RadioGroup bottomBar;
    @BindView(R.id.rl_container)
    RelativeLayout rlContainer;
    private FragmentManager supportFragmentManager;
    Fragment homeFragment, userFragment, messageFragment;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public EmptyPresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        supportFragmentManager = getSupportFragmentManager();

        bottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                final FragmentTransaction transaction = supportFragmentManager.beginTransaction();
                hideAllFragment(transaction);
                switch (checkedId) {
                    case R.id.rb_home:
                        if (homeFragment == null) {
                            homeFragment = (Fragment) ARouter.getInstance().build(RouterUtil.Home_Fragment_Main).navigation();
                            transaction.add(R.id.flContainer, homeFragment);
                        } else {
                            transaction.show(homeFragment);
                        }
                        break;
                    case R.id.rb_resource:
                        if (messageFragment == null) {
                            messageFragment = (Fragment) ARouter.getInstance().build(RouterUtil.Kind_Fragment_Main).navigation();
                            transaction.add(R.id.flContainer, messageFragment);
                        } else {
                            transaction.show(messageFragment);
                        }
                        break;
                    case R.id.rb_message:
                        if (userFragment == null) {
                            userFragment = (Fragment) ARouter.getInstance().build(RouterUtil.MESSAGE_Fragment_Main).navigation();
                            transaction.add(R.id.flContainer, userFragment);
                        } else {
                            transaction.show(userFragment);
                        }
                        break;
                }
                transaction.commit();
            }
        });
        showFirstPosition();

    }

    private void showFirstPosition() {
        supportFragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.add(R.id.flContainer, homeFragment);
        transaction.commit();
    }

    public void hideAllFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (userFragment != null) {
            transaction.hide(userFragment);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }
}
