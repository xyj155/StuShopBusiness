package com.example.stushopbusiness.api;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.gson.UserGson;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface API {
    @FormUrlEncoded
    @POST("/StuShop/public/index.php/shop/Shop/userlogin")
    Observable<BaseGson<UserGson>> userlogin(@Field("username") String username, @Field("password") String password);
}
