package com.example.stushopbusiness.view.activity;


import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.stushopbusiness.R;
import com.example.stushopbusiness.adapter.ShopServiceConversationAdapter;
import com.example.stushopbusiness.base.BaseActivity;
import com.example.stushopbusiness.contract.EmptyContract;
import com.example.stushopbusiness.entity.ConversationEntity;
import com.example.stushopbusiness.presenter.EmptyPresenter;
import com.example.stushopbusiness.util.KPSwitchConflictUtil;
import com.example.stushopbusiness.util.KeyboardUtil;
import com.example.stushopbusiness.util.NoFastClickUtils;
import com.example.stushopbusiness.util.RouterUtil;
import com.example.stushopbusiness.util.SharePreferenceUtil;
import com.example.stushopbusiness.weight.KPSwitchPanelLinearLayout;
import com.example.stushopbusiness.weight.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.ChatRoomMessageEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;


@SuppressLint("Registered")
@Route(path = RouterUtil.SHOPSERVICE)
public class ShopServiceConversationActivity extends BaseActivity<EmptyContract.View, EmptyPresenter> {

    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.sml_conversation)
    SmartRefreshLayout smlConversation;

    @BindView(R.id.msg_recycle_view)
    RecyclerView msgRecycleView;
    private ShopServiceConversationAdapter shopServiceConversationAdapter = new ShopServiceConversationAdapter(null);
    private View mSubPanel1;
    @BindView(R.id.voice_text_switch_iv)
    ImageView mPlusIv1;
    private int count;
    @BindView(R.id.panel_root)
    KPSwitchPanelLinearLayout mPanelRoot;
    List<ConversationEntity> conversationEntities = new ArrayList<>();
    private Conversation singleConversation1;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public EmptyPresenter getPresenter() {
        return null;
    }

    public void onEventMainThread(ChatRoomMessageEvent event) {
        List<Message> msgs = event.getMessages();
        for (Message msg : msgs) {
            Log.i(TAG, "onEvent111: " + msg);
            boolean username = msg.getFromUser().getUserName().equals(SharePreferenceUtil.getUser("username", "String"));
            if (username) {
                shopServiceConversationAdapter.addData(ConversationEntity.client(msg));
            } else {
                shopServiceConversationAdapter.addData(ConversationEntity.service(msg));
            }
        }
        Log.i(TAG, "onEventMainThread: " + msgs.size());
        shopServiceConversationAdapter.notifyDataSetChanged();
    }


    /**
     * 接收消息
     *
     * @param event
     */
    public void onEventMainThread(MessageEvent event) {
        Message newMessage = event.getMessage();//获取此次离线期间会话收到的新消息列表
        Log.i(TAG, "onEvent:2222 " + newMessage);
        boolean username = newMessage.getFromUser().getUserName().equals(SharePreferenceUtil.getUser("username", "String"));
        if (username) {
            conversationEntities.add(ConversationEntity.client(newMessage));
        } else {
            conversationEntities.add(ConversationEntity.service(newMessage));
        }
        newMessage.setHaveRead(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i(TAG, "gotResult: 已读");
            }
        });
        shopServiceConversationAdapter.replaceData(conversationEntities);
        shopServiceConversationAdapter.notifyDataSetChanged();
    }


    @Override
    public int intiLayout() {
        return R.layout.activity_shop_service;
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            KPSwitchConflictUtil.hidePanelAndKeyboard(mPanelRoot);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    // 当屏幕分屏/多窗口变化时回调
    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        KPSwitchConflictUtil.onMultiWindowModeChanged(isInMultiWindowMode);
    }

    private Message message = null;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        createDialog();
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle(getIntent().getStringExtra("shopName"));
        mPanelRoot.setIgnoreRecommendHeight(true);
        mSubPanel1 = mPanelRoot.findViewById(R.id.sub_panel_1);
        smlConversation.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (count > 0) {
                    List<Message> messagesFromOldest = new ArrayList<Message>();
                    List<ConversationEntity> list = new ArrayList<ConversationEntity>();
                    if (count > 12) {//这个是极光SDK 第一个参数是请求的位置  第二个是请求的条数 减一
                        count = count - 12;//每次获取12个
                        List<Message> messagesFromOldest1 = singleConversation1.getMessagesFromOldest(count, 12);
                        messagesFromOldest.addAll(messagesFromOldest1);//获取位置   获取条数
                    } else { // 第二次加载的数据比较多啊，不是 12 条了吧等我回家帮你看看吧，我路上想想我要下班了 好
                        List<Message> messagesFromOldest1 = singleConversation1.getMessagesFromOldest(0, count);
                        messagesFromOldest.addAll(messagesFromOldest1);//获取位置   获取条数
                        count = 0;
                    }
                    for (Message message1 : messagesFromOldest) {
                        message1.setHaveRead(new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                Log.i(TAG, "gotResult: 已读");
                            }
                        });
                        if (message1.getFromName().equals(String.valueOf(SharePreferenceUtil.getUser("username", "String")))) {
                            list.add(ConversationEntity.client(message1));
                        } else {
                            list.add(ConversationEntity.service(message1));
                        }
                    }
                    shopServiceConversationAdapter.addData(0, list);
                    list.clear();
                }
                smlConversation.finishRefresh(200);
            }
        });
        KeyboardUtil.attach(this, mPanelRoot,
                new KeyboardUtil.OnKeyboardShowingListener() {
                    @Override
                    public void onKeyboardShowing(boolean isShowing) {
                        Log.d(TAG, String.format("Keyboard is %s", isShowing
                                ? "showing" : "hiding"));
                    }
                });
        KPSwitchConflictUtil.attach(mPanelRoot, etInput,
                new KPSwitchConflictUtil.SwitchClickListener() {
                    @Override
                    public void onClickSwitch(View v, boolean switchToPanel) {
                        if (switchToPanel) {
                            etInput.clearFocus();
                        } else {
                            etInput.requestFocus();
                        }
                    }
                },
                new KPSwitchConflictUtil.SubPanelAndTrigger(mSubPanel1, mPlusIv1));
        msgRecycleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    KPSwitchConflictUtil.hidePanelAndKeyboard(mPanelRoot);
                }
                return false;
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShopServiceConversationActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        msgRecycleView.setLayoutManager(linearLayoutManager);
        msgRecycleView.setAdapter(shopServiceConversationAdapter);
        shopServiceConversationAdapter.bindToRecyclerView(msgRecycleView);
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    tvSend.setBackgroundResource(R.drawable.abc_tv_conversation_send_normal_bg);
                    tvSend.setClickable(false);
                    mPlusIv1.setVisibility(View.VISIBLE);
                    tvSend.setVisibility(View.GONE);
                } else {
                    tvSend.setBackgroundResource(R.drawable.abc_tv_conversation_send_changed_bg);
                    tvSend.setClickable(true);
                    mPlusIv1.setVisibility(View.GONE);
                    tvSend.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Log.i(TAG, "initView: " + SharePreferenceUtil.getUser("username", "String"));
        JMessageClient.login(String.valueOf(SharePreferenceUtil.getUser("username", "String")), "xuyijie", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    mhideDialog();
                    Log.i(TAG, "gotResult: gotResult" + i + s);
                    Log.i(TAG, "gotResult: "+getIntent().getStringExtra("appkey"));
                    if (getIntent().getStringExtra("appkey").isEmpty()) {
                        singleConversation1 = Conversation.createSingleConversation(getIntent().getStringExtra("username"));
                    } else {
                        singleConversation1 = Conversation.createSingleConversation(getIntent().getStringExtra("username"), getIntent().getStringExtra("appkey"));
                    }

                    Log.i(TAG, "gotResult: 31233111" + singleConversation1);
                    if (singleConversation1 != null) {
                        count = singleConversation1.getAllMessage().size();
                        Log.i(TAG, "gotResult: 313113" + count);
                        List<Message> messagesFromOldest1 = singleConversation1.getMessagesFromOldest(count - 12, 12);
                        for (Message msg : messagesFromOldest1) {
                            Log.i(TAG, "onEvent111:312313 " + msg);
                            boolean username = msg.getFromUser().getUserName().equals(String.valueOf(SharePreferenceUtil.getUser("username", "String")));
                            if (username) {
                                shopServiceConversationAdapter.addData(ConversationEntity.client(msg));
                            } else {
                                shopServiceConversationAdapter.addData(ConversationEntity.service(msg));
                            }
                        }
                        tvSend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i(TAG, "onClick: ");
                                if (!etInput.getText().toString().isEmpty()) {
                                    if (NoFastClickUtils.isFastClick()) {
                                        ToastUtils.show("不要发送太快哦！");
                                    } else {
                                        if (singleConversation1 != null) {
                                            if (getIntent().getStringExtra("appkey").isEmpty()) {
                                                message = singleConversation1.createSendMessage(new TextContent(etInput.getText().toString()));
                                            } else {
                                                message = singleConversation1.createSendMessage(new TextContent(etInput.getText().toString()), getIntent().getStringExtra("appkey"));
                                            }
                                            if (message != null) {
                                                JMessageClient.sendMessage(message);
                                                Log.i(TAG, "onClick: ");
                                                message.setOnSendCompleteCallback(new BasicCallback() {
                                                    @Override
                                                    public void gotResult(int responseCode, String responseDesc) {
                                                        Log.i(TAG, "gotResult: " + responseDesc + responseCode);
                                                        if (responseCode == 0) {
                                                            shopServiceConversationAdapter.addData(ConversationEntity.client(message));
                                                            //消息发送成功
                                                            msgRecycleView.scrollToPosition(shopServiceConversationAdapter.getItemCount() - 1);
                                                            Log.i(TAG, "gotResult: 发送成功");
                                                            etInput.setText("");
                                                        } else {
                                                            Log.i(TAG, "gotResult: 消息发送失败");
                                                            //消息发送失败
                                                        }

                                                    }
                                                });
                                            }
                                        }

                                    }

                                }
                            }
                        });
                    }
                    tvSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (NoFastClickUtils.isFastClick()) {
                                ToastUtils.show("不要发送太快哦！");
                            } else {
                                if (singleConversation1 != null) {
                                    final Message message = singleConversation1.createSendMessage(new TextContent(etInput.getText().toString()), "3a23b8ad0545565bb6d71ecc");
                                    JMessageClient.sendMessage(message);
                                    Log.i(TAG, "onClick: ");
                                    message.setOnSendCompleteCallback(new BasicCallback() {
                                        @Override
                                        public void gotResult(int responseCode, String responseDesc) {
                                            Log.i(TAG, "gotResult: " + responseDesc + responseCode);
                                            if (responseCode == 0) {
                                                shopServiceConversationAdapter.addData(ConversationEntity.client(message));
                                                //消息发送成功
                                                msgRecycleView.scrollToPosition(shopServiceConversationAdapter.getItemCount() - 1);
                                                Log.i(TAG, "gotResult: 发送成功");
                                            } else {
                                                Log.i(TAG, "gotResult: 消息发送失败");
                                                //消息发送失败
                                            }
                                            etInput.setText("");
                                        }
                                    });

                                }

                            }
                        }
                    });
                } else {
                    mhideDialog();
                    ToastUtils.show("登陆失败");
                }
            }
        });

    }


    @Override
    public void initData() {


    }


}