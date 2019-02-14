package com.example.stushopbusiness.model;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.contract.LoginContract;
import com.example.stushopbusiness.gson.UserGson;
import com.example.stushopbusiness.http.RetrofitUtils;

import rx.Observable;

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<BaseGson<UserGson>> userlogin(String username, String password) {
        return RetrofitUtils.getInstance().create().userlogin(username,password);
    }
}
