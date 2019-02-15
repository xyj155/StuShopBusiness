package com.example.stushopbusiness.api;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.gson.EmptyGson;
import com.example.stushopbusiness.gson.OrderGson;
import com.example.stushopbusiness.gson.UserGson;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface API {
    @FormUrlEncoded
    @POST("/StuShop/public/index.php/shop/Shop/userlogin")
    Observable<BaseGson<UserGson>> userlogin(@Field("username") String username, @Field("password") String password);

    @GET("/StuShop/public/index.php/shop/Order/queryShopOrderByStatus")
    Observable<BaseGson<List<OrderGson>>> queryShopOrderByStatus(@Query("userId") String userId, @Query("status") String status);

    @GET("/StuShop/public/index.php/shop/Order/queryOrderDetail")
    Observable<BaseGson<OrderGson>> queryOrderDetail(@Query("goodsId") String goodsId, @Query("orderNum") String orderNum);

    @FormUrlEncoded
    @POST("/StuShop/public/index.php/shop/Order/submitExpressNum")
    Observable<BaseGson<EmptyGson>> submitExpressNum(@Field("orderNum") String orderNum, @Field("goodsId") String goodsId, @Field("expressNum") String expressNum);

}
