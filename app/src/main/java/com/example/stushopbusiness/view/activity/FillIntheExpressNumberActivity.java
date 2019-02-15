package com.example.stushopbusiness.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.stushopbusiness.R;
import com.example.stushopbusiness.base.BaseActivity;
import com.example.stushopbusiness.contract.GoodsOrderDetailContract;
import com.example.stushopbusiness.contract.SubmitExpressContract;
import com.example.stushopbusiness.gson.OrderGson;
import com.example.stushopbusiness.presenter.GoodsOrderDetailPresenter;
import com.example.stushopbusiness.presenter.SubmitExpressPresenter;
import com.example.stushopbusiness.util.GlideUtil;
import com.example.stushopbusiness.weight.MyDialog;
import com.example.stushopbusiness.weight.toast.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FillIntheExpressNumberActivity extends BaseActivity<GoodsOrderDetailContract.View, GoodsOrderDetailPresenter> implements GoodsOrderDetailContract.View, SubmitExpressContract.View {


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_expressName)
    TextView tvExpressName;
    private SubmitExpressPresenter submitExpressPresenter = new SubmitExpressPresenter(this);
    private MyDialog myDialog1;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public GoodsOrderDetailPresenter getPresenter() {
        return new GoodsOrderDetailPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_fillinthe_express_number;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("填写订单");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void initData() {
        Log.i(TAG, "initData: goodsId" + getIntent().getStringExtra("goodsId"));
        Log.i(TAG, "initData: orderNum" + getIntent().getStringExtra("orderNum"));
        mPresenter.queryOrderDetail(getIntent().getStringExtra("goodsId"), getIntent().getStringExtra("orderNum"));

    }

    @Override
    public void queryOrderDetail(final OrderGson orderGson) {
        Log.i(TAG, "queryOrderDetail: " + orderGson);
        tvGoodsName.setText(orderGson.getStyleName());
        tvCount.setText("共" + orderGson.getCount() + "件商品");
        tvTime.setText("下单时间：" + orderGson.getAddTime());
        tvPrice.setText("￥" + orderGson.getStylePrice());
        tvOrder.setText("订单编号：" + orderGson.getOrderNum());
        tvExpressName.setText("快递方式：" + orderGson.getExpressName());
        GlideUtil.loadRoundCornerAvatarImage(orderGson.getGoodsPicUrl(), ivCover, 10);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvPrice.getText().toString().isEmpty()) {
                    ToastUtils.show("单号还没有输入哦！");
                } else {
                    myDialog1 = new MyDialog(FillIntheExpressNumberActivity.this, new int[]{R.id.dialog_btn_close, R.id.dialog_btn_cancel});
                    myDialog1.setContent("快递单号：" + etPrice.getText().toString());
                    myDialog1.setTitle("请确认订快递单号");
                    myDialog1.setOnCenterItemClickListener(new MyDialog.OnCenterItemClickListener() {
                        @Override
                        public void onCenterItemClick(MyDialog dialog, View view) {
                            switch (view.getId()) {
                                case R.id.dialog_btn_close:
                                    dialog.dismiss();
                                    break;
                                case R.id.dialog_btn_cancel:
                                    Log.i(TAG, "onCenterItemClick:getGid "+orderGson.getGid());
                                    Log.i(TAG, "onCenterItemClick: getOrderNum"+orderGson.getOrderNum());
                                    Log.i(TAG, "onCenterItemClick: etPrice"+etPrice.getText().toString());
                                    submitExpressPresenter.submitExpressNum(orderGson.getGid(), orderGson.getOrderNum(), etPrice.getText().toString());

                                    break;
                            }
                        }
                    });
                    myDialog1.show();

                }

            }
        });
    }

    @Override
    public void showError(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void showDialog() {
        createDialog();
    }

    @Override
    public void hideDialog() {
        mhideDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void submitSuccess(boolean isSuccess) {
        if (isSuccess) {
            ToastUtils.show("提交成功");
            finish();
        } else {
            ToastUtils.show("提交失败");
        }
    }


}
