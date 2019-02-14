package com.example.stushopbusiness.view.fragment;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.stushopbusiness.R;
import com.example.stushopbusiness.base.BaseFragment;
import com.example.stushopbusiness.presenter.EmptyPresenter;
import com.example.stushopbusiness.util.RouterUtil;

@Route(path = RouterUtil.MESSAGE_Fragment_Main)
public class UserFragment extends BaseFragment<EmptyPresenter> {
    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public int initLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public EmptyPresenter initPresenter() {
        return null;
    }
}
