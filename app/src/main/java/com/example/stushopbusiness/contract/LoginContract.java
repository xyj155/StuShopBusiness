package com.example.stushopbusiness.contract;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.base.BaseView;
import com.example.stushopbusiness.gson.UserGson;

import rx.Observable;

public interface LoginContract {
    interface Model {
        Observable<BaseGson<UserGson>> userlogin(String username, String password);
    }

    interface View extends BaseView {
        void userlogin(UserGson userGson);
    }

    interface Presenter {
        void userlogin(String username, String password);
    }
}
