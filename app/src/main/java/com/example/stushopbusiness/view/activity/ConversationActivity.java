package com.example.stushopbusiness.view.activity;

import com.example.stushopbusiness.R;
import com.example.stushopbusiness.base.BaseActivity;
import com.example.stushopbusiness.contract.EmptyContract;
import com.example.stushopbusiness.presenter.EmptyPresenter;

public class ConversationActivity extends BaseActivity<EmptyContract.View, EmptyPresenter> {



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
        return R.layout.activity_conversation;

    }

    @Override
    public void initView() {
        initToolBar().setToolBarTitle(getIntent().getData().getQueryParameter("title"));
    }

    @Override
    public void initData() {

    }
}