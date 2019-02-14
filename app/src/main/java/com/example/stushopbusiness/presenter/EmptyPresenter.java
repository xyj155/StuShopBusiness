package com.example.stushopbusiness.presenter;

import com.example.stushopbusiness.base.BasePresenter;
import com.example.stushopbusiness.contract.EmptyContract;

public class EmptyPresenter extends BasePresenter<EmptyContract.View> implements EmptyContract.Presenter {
    public EmptyPresenter(EmptyContract.View mMvpView) {
        super(mMvpView);
    }
}
