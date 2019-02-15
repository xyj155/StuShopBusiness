package com.example.stushopbusiness.presenter;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.base.BasePresenter;
import com.example.stushopbusiness.contract.SubmitExpressContract;
import com.example.stushopbusiness.gson.EmptyGson;
import com.example.stushopbusiness.http.BaseObserver;
import com.example.stushopbusiness.model.SubmitExpressModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SubmitExpressPresenter extends BasePresenter<SubmitExpressContract.View> implements SubmitExpressContract.Presenter {

    public SubmitExpressPresenter(SubmitExpressContract.View mMvpView) {
        super(mMvpView);
    }

    private SubmitExpressModel submitExpressModel = new SubmitExpressModel();

    @Override
    public void submitExpressNum(String goodsId, String orderNum, String expressNum) {
        mMvpView.showDialog();
        submitExpressModel.submitExpressNum(goodsId, orderNum, expressNum)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (emptyGsonBaseGson.isStatus()) {
                            mMvpView.submitSuccess(true);
                        } else {
                            mMvpView.submitSuccess(false);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
