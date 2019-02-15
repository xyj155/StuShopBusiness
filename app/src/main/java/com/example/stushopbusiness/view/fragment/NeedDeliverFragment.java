package com.example.stushopbusiness.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.stushopbusiness.R;
import com.example.stushopbusiness.adapter.OrderAdapter;
import com.example.stushopbusiness.base.BaseFragment;
import com.example.stushopbusiness.contract.UserFormContract;
import com.example.stushopbusiness.gson.OrderGson;
import com.example.stushopbusiness.presenter.UserFormPresenter;
import com.example.stushopbusiness.util.SharePreferenceUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NeedDeliverFragment extends BaseFragment<UserFormPresenter> implements UserFormContract.View {
    @BindView(R.id.ry_order)
    RecyclerView ryOrder;
    Unbinder unbinder;
    @BindView(R.id.sml_order)
    SmartRefreshLayout smlOrder;
    private OrderAdapter orderAdapter;

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        orderAdapter = new OrderAdapter(null, getContext());
        ryOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        ryOrder.setAdapter(orderAdapter);
        smlOrder.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.queryShopOrderByStatus((String) SharePreferenceUtil.getUser("userId", "String"), "3");
            }
        });
        Log.i(TAG, "initView: "+(String) SharePreferenceUtil.getUser("userId", "String"));
//        mPresenter.queryShopOrderByStatus((String) SharePreferenceUtil.getUser("userId", "String"), "2");
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.queryShopOrderByStatus((String) SharePreferenceUtil.getUser("userId", "String"), "3");
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_needpayment;
    }

    @Override
    public UserFormPresenter initPresenter() {
        return new UserFormPresenter(this);
    }

    private static final String TAG = "NeedPaymentFragment";


    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog() {
        createDialog();
    }

    @Override
    public void hideDialog() {
        dialogCancel();
        smlOrder.finishRefresh();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void queryShopOrderByStatus(List<List<OrderGson>> orderGsonList) {
        smlOrder.finishRefresh();
        orderAdapter.replaceData(orderGsonList);
    }
}
