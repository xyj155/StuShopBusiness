package com.example.stushopbusiness.model;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.contract.GoodsOrderDetailContract;
import com.example.stushopbusiness.gson.OrderGson;
import com.example.stushopbusiness.http.RetrofitUtils;

import rx.Observable;

public class GoodsOrderDetailModel implements GoodsOrderDetailContract.Model {
    @Override
    public Observable<BaseGson<OrderGson>> queryOrderDetail(String goodsId, String orderNum) {
        return RetrofitUtils.getInstance().create().queryOrderDetail(goodsId,orderNum);
    }
}
