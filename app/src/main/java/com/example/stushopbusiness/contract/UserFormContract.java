package com.example.stushopbusiness.contract;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.base.BaseView;
import com.example.stushopbusiness.gson.OrderGson;

import java.util.List;

import rx.Observable;

public interface UserFormContract {
    interface Model {
        Observable<BaseGson<List<OrderGson>>> queryShopOrderByStatus(String userId, String status);
    }

    interface View extends BaseView {
        void queryShopOrderByStatus(List<List<OrderGson>> orderGsonList);
    }

    interface Presenter {
        void queryShopOrderByStatus(String userId,String status);
    }
}
