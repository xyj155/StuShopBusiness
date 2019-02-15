package com.example.stushopbusiness.presenter;

import android.util.Log;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.base.BasePresenter;
import com.example.stushopbusiness.contract.UserFormContract;
import com.example.stushopbusiness.gson.OrderGson;
import com.example.stushopbusiness.http.BaseObserver;
import com.example.stushopbusiness.model.UserFormModel;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class UserFormPresenter extends BasePresenter<UserFormContract.View> implements UserFormContract.Presenter {
    public UserFormPresenter(UserFormContract.View mMvpView) {
        super(mMvpView);
    }

    private UserFormModel userFormModel = new UserFormModel();

    @Override
    public void queryShopOrderByStatus(String userId, String status) {
        mMvpView.showDialog();
        userFormModel.queryShopOrderByStatus(userId, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<List<OrderGson>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<List<OrderGson>> listBaseGson) {
                        Log.i(TAG, "onNext: "+listBaseGson.getData());
                        mMvpView.hideDialog();
                        mMvpView.queryShopOrderByStatus(listBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {
                        Log.i(TAG, "onError: "+error);
                        mMvpView.hideDialog();
                    }
                });
    }

    private static final String TAG = "UserFormPresenter";
}
