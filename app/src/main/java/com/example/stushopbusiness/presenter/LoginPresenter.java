package com.example.stushopbusiness.presenter;

import com.example.stushopbusiness.base.BaseGson;
import com.example.stushopbusiness.base.BasePresenter;
import com.example.stushopbusiness.contract.LoginContract;
import com.example.stushopbusiness.gson.UserGson;
import com.example.stushopbusiness.http.BaseObserver;
import com.example.stushopbusiness.model.LoginModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    public LoginPresenter(LoginContract.View mMvpView) {
        super(mMvpView);
    }

    private LoginModel loginModel = new LoginModel();

    @Override
    public void userlogin(String username, String password) {
        mMvpView.showDialog();
        loginModel.userlogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<UserGson> userGsonBaseGson) {
                        mMvpView.hideDialog();
                        if (userGsonBaseGson.isStatus()) {
                            mMvpView.userlogin(userGsonBaseGson.getSingleData());
                        } else {
                            mMvpView.showError("登陆失败");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mMvpView.showError("登陆失败");
                    }
                });
    }
}
