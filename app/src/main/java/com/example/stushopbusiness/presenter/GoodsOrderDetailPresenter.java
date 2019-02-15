package com.example.stushopbusiness.presenter;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.base.BasePresenter;
import com.example.stushopbusiness.contract.GoodsOrderDetailContract;
import com.example.stushopbusiness.gson.OrderGson;
import com.example.stushopbusiness.http.BaseObserver;
import com.example.stushopbusiness.model.GoodsOrderDetailModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodsOrderDetailPresenter extends BasePresenter<GoodsOrderDetailContract.View> implements GoodsOrderDetailContract.Presenter {

    public GoodsOrderDetailPresenter(GoodsOrderDetailContract.View mMvpView) {
        super(mMvpView);
    }

    private GoodsOrderDetailModel goodsOrderDetailModel = new GoodsOrderDetailModel();

    @Override
    public void queryOrderDetail(String goodsId, String orderNum) {
        mMvpView.showDialog();
        goodsOrderDetailModel.queryOrderDetail(goodsId, orderNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<OrderGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<OrderGson> orderGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (orderGsonBaseGson.isStatus()) {
                            mMvpView.queryOrderDetail(orderGsonBaseGson.getData().get(0));
                        } else {
                            mMvpView.showError(orderGsonBaseGson.getMsg());
                        }

                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.hideDialog();
                    }
                });
    }
}
