package com.example.stushopbusiness.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.stushopbusiness.R;
import com.example.stushopbusiness.adapter.ConversationAdapter;
import com.example.stushopbusiness.base.BaseFragment;
import com.example.stushopbusiness.presenter.EmptyPresenter;
import com.example.stushopbusiness.util.RouterUtil;
import com.example.stushopbusiness.util.SharePreferenceUtil;
import com.example.stushopbusiness.weight.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.api.BasicCallback;

@Route(path = RouterUtil.Kind_Fragment_Main)
public class MessageFragment extends BaseFragment<EmptyPresenter> {
    @BindView(R.id.ry_recent)
    RecyclerView ryRecent;
    @BindView(R.id.sml_message)
    SmartRefreshLayout smlMessage;
    Unbinder unbinder;
    private ConversationAdapter conversationAdapter;
    @Override
    public void initData() {

    }

    private static final String TAG = "MessageFragment";
    @Override
    public void onResume() {
        super.onResume();
//        JMessageClient.login(String.valueOf(SharePreferenceUtil.getUser("username", "String")), "xuyijie", new BasicCallback() {
//            @Override
//            public void gotResult(int i, String s) {
//                Log.i(TAG, "gotResult: " + s);
//                if (i == 0) {
                    List<Conversation> conversationList = JMessageClient.getConversationList();
                    if (conversationList.size()!=0||conversationList!=null){
                        Log.i(TAG, "gotResult: " + conversationList.size());
                        conversationAdapter.replaceData(conversationList);
                    }
//                } else {
//                    ToastUtils.show("消息列表获取失败，错误代码：" + i);
//                    Log.i(TAG, "gotResult: 消息列表获取失败，错误代码");
//                    smlMessage.finishRefresh();
//                }
//            }
//        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }
    public void onEventMainThread(MessageEvent event) {
        //do your own business
        Log.i(TAG, "onEventMainThread: ");
        List<Conversation> conversationList = JMessageClient.getConversationList();
        if (conversationList != null) {
            Log.i(TAG, "gotResult:conversationList " + conversationList.size());
            if (conversationAdapter != null) {
                conversationAdapter.replaceData(conversationList);
                conversationAdapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        ryRecent.setLayoutManager(new LinearLayoutManager(getContext()));
        conversationAdapter = new ConversationAdapter(null);
        ryRecent.setAdapter(conversationAdapter);
        conversationAdapter.bindToRecyclerView(ryRecent);
        View inflate = View.inflate(getContext(), R.layout.common_empty_message, null);
        conversationAdapter.setEmptyView(inflate);
        JMessageClient.registerEventReceiver(this);
        JMessageClient.login(String.valueOf(SharePreferenceUtil.getUser("username", "String")), "xuyijie", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i(TAG, "gotResult: " + s);
                if (i == 0) {
                    List<Conversation> conversationList = JMessageClient.getConversationList();
                    Log.i(TAG, "gotResult: " + conversationList.size());
                    conversationAdapter.replaceData(conversationList);
                    smlMessage.finishRefresh();
                } else {
                    ToastUtils.show("消息列表获取失败，错误代码：" + i);
                    Log.i(TAG, "gotResult: 消息列表获取失败，错误代码");
                    smlMessage.finishRefresh();
                }
            }
        });
        smlMessage.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                JMessageClient.login(String.valueOf(SharePreferenceUtil.getUser("username", "String")), "xuyijie", new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        Log.i(TAG, "gotResult: " + s);
                        if (i == 0) {
                            List<Conversation> conversationList = JMessageClient.getConversationList();
                            Log.i(TAG, "gotResult: " + conversationList.size());
                            conversationAdapter.replaceData(conversationList);
                            smlMessage.finishRefresh();
                        } else {
                            ToastUtils.show("消息列表获取失败，错误代码：" + i);
                            Log.i(TAG, "gotResult: 消息列表获取失败，错误代码");
                            smlMessage.finishRefresh();
                        }
                    }
                });
            }
        });
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
