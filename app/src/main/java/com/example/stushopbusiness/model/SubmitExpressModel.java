package com.example.stushopbusiness.model;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.contract.SubmitExpressContract;
import com.example.stushopbusiness.gson.EmptyGson;
import com.example.stushopbusiness.http.RetrofitUtils;

import rx.Observable;

public class SubmitExpressModel implements SubmitExpressContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> submitExpressNum(String goodsId, String orderNum, String expressNum) {
        return RetrofitUtils.getInstance().create().submitExpressNum(orderNum,goodsId,expressNum);
    }
}
