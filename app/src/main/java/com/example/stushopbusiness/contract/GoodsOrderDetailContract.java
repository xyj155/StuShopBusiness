package com.example.stushopbusiness.contract;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.base.BaseView;
import com.example.stushopbusiness.gson.OrderGson;

import rx.Observable;

public interface GoodsOrderDetailContract {
    interface Model {
        Observable<BaseGson<OrderGson>> queryOrderDetail(String goodsId,String  orderNum);
    }

    interface View extends BaseView {
        void queryOrderDetail(OrderGson orderGson);
    }

    interface Presenter {
        void queryOrderDetail(String goodsId,String  orderNum);
    }
}
