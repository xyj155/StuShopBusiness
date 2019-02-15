package com.example.stushopbusiness.contract;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.base.BaseView;
import com.example.stushopbusiness.gson.EmptyGson;

import rx.Observable;

public interface SubmitExpressContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> submitExpressNum(String goodsId, String orderNum, String expressNum);
    }

    interface View  extends BaseView {
        void submitSuccess(boolean isSuccess);
    }

    interface Presenter {
        void submitExpressNum(String goodsId, String orderNum, String expressNum);
    }
}
