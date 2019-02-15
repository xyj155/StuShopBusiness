package com.example.stushopbusiness.model;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.contract.UserFormContract;
import com.example.stushopbusiness.gson.OrderGson;
import com.example.stushopbusiness.http.RetrofitUtils;

import java.util.List;

import rx.Observable;

public class UserFormModel implements UserFormContract.Model {


    @Override
    public Observable<BaseGson<List<OrderGson>>> queryShopOrderByStatus(String userId, String status) {
        return RetrofitUtils.getInstance().create().queryShopOrderByStatus(userId,status);
    }
}
