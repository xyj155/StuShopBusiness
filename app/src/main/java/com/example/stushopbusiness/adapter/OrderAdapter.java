package com.example.stushopbusiness.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.stushopbusiness.R;
import com.example.stushopbusiness.gson.OrderGson;
import com.example.stushopbusiness.util.GlideUtil;
import com.example.stushopbusiness.view.activity.FillIntheExpressNumberActivity;

import java.util.List;

public class OrderAdapter extends BaseQuickAdapter<List<OrderGson>, BaseViewHolder> {
    private Context context;

    public OrderAdapter(@Nullable List<List<OrderGson>> data, Context context) {
        super(R.layout.adapter_goods_order_count, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, List<OrderGson> item) {
        helper.setText(R.id.tv_order, "订单编号" + item.get(0).getOrderNum());
        RecyclerView view = helper.getView(R.id.ry_goods);
        view.setLayoutManager(new LinearLayoutManager(context));
        view.setNestedScrollingEnabled(false);
//        List<List<OrderGson.DataBean>> data = item.ge();
        GoodsAdapter goodsAdapter = new GoodsAdapter(item);
        view.setAdapter(goodsAdapter);
    }

    private class GoodsAdapter extends BaseQuickAdapter<OrderGson, BaseViewHolder> {

        public GoodsAdapter(@Nullable List<OrderGson> data) {
            super(R.layout.adapter_goods_order_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final OrderGson item) {
            Log.i(TAG, "convert: " + item.getStatus());
            View send = helper.getView(R.id.tv_send);
            View express = helper.getView(R.id.tv_express);
            if (item.getStatus().equals("3")) {
                send.setVisibility(View.GONE);
                express.setVisibility(View.VISIBLE);
            } else if (item.getStatus().equals("2")) {
                send.setVisibility(View.VISIBLE);
                express.setVisibility(View.GONE);
            }
            helper.setText(R.id.tv_goods_name, item.getStyleName())
                    .setText(R.id.tv_count, "共" + item.getCount() + "件商品")
                    .setText(R.id.tv_time, "下单时间：" + item.getAddTime())
                    .setText(R.id.tv_price, "￥" + item.getStylePrice())
                    .setOnClickListener(R.id.tv_send, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, FillIntheExpressNumberActivity.class);
                            intent.putExtra("goodsId", String.valueOf(item.getGid()));
                            intent.putExtra("orderNum", String.valueOf(item.getOrderNum()));
                            context.startActivity(intent);
                        }
                    });
            GlideUtil.loadRoundCornerAvatarImage(item.getGoodsPicUrl(), (ImageView) helper.getView(R.id.iv_cover), 8);

        }
    }
}
