package com.example.stushopbusiness.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.stushopbusiness.R;
import com.example.stushopbusiness.base.BaseFragment;
import com.example.stushopbusiness.presenter.EmptyPresenter;
import com.example.stushopbusiness.util.InRongIMConnect;
import com.example.stushopbusiness.util.RongUtil;
import com.example.stushopbusiness.util.RouterUtil;
import com.example.stushopbusiness.util.SharePreferenceUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;


@Route(path = RouterUtil.Kind_Fragment_Main)
public class MessageFragment extends BaseFragment<EmptyPresenter> {


    @BindView(R.id.sml_message)
    SmartRefreshLayout smlMessage;
    Unbinder unbinder;

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        smlMessage.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initRongFragment();
            }
        });
        initRongFragment();
    }

    private void initRongFragment() {

        Log.i(TAG, "initRongFragment: " + (String) SharePreferenceUtil.getUser("imToken", "String"));
        RongUtil.connect((String) SharePreferenceUtil.getUser("imToken", "String"), new InRongIMConnect() {
            @Override
            public void onConnectSuccess() {
                RongIM.getInstance().setMessageAttachedUserInfo(true);
                Log.i(TAG, "onConnectSuccess: ");
                ConversationListFragment listFragment = (ConversationListFragment) ConversationListFragment.instantiate(getContext(), ConversationListFragment.class.getName());
                Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(RongPushClient.ConversationType.PRIVATE.getName(), "false")
                        .appendQueryParameter(RongPushClient.ConversationType.GROUP.getName(), "false")
                        .appendQueryParameter(RongPushClient.ConversationType.DISCUSSION.getName(), "false")
                        .appendQueryParameter(RongPushClient.ConversationType.PUBLIC_SERVICE.getName(), "false")
                        .appendQueryParameter(RongPushClient.ConversationType.SYSTEM.getName(), "false")
                        .build();
                listFragment.setUri(uri);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //将融云的Fragment界面加入到我们的页面。
                transaction.add(R.id.conversationlist, listFragment);
                transaction.commitAllowingStateLoss();
            }

            @Override
            public void onConnectFailed() {
                Log.i(TAG, "onConnectFailed: ");
            }
        });
    }

    private static final String TAG = "MessageFragment";

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public int initLayout() {
        return R.layout.fragment_message;
    }

    @Override
    public EmptyPresenter initPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);


        return rootView;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
