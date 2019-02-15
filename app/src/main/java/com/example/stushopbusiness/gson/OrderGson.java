package com.example.stushopbusiness.gson;

public class OrderGson {


    @Override
    public String toString() {
        return "OrderGson{" +
                "styleName='" + styleName + '\'' +
                ", goodsPicUrl='" + goodsPicUrl + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", addTime='" + addTime + '\'' +
                ", count=" + count +
                ", stylePrice='" + stylePrice + '\'' +
                ", goodsId=" + goodsId +
                '}';
    }

    /**
     * styleName : 升级12：i7-8550/16G/512G固态+1T/MX150独显/送16重礼
     * goodsPicUrl : https://img.alicdn.com/imgextra/i3/2781172225/O1CN01s3v4Eo1SJ5jsphXY3_!!0-item_pic.jpg_430x430q90.jpg
     * orderNum : 2019021510024
     * addTime : 2019-02-15 02:51:40
     * count : 1
     * stylePrice : 7388.00
     * goodsId : 43
     */


    private String styleName;
    private String goodsPicUrl;
    private String expressName;

    public String getExpressName() {
        return expressName;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    private String orderNum;
    private String addTime;
    private String count;
    private String stylePrice;
    private int goodsId;

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getGoodsPicUrl() {
        return goodsPicUrl;
    }

    public void setGoodsPicUrl(String goodsPicUrl) {
        this.goodsPicUrl = goodsPicUrl;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStylePrice() {
        return stylePrice;
    }

    public void setStylePrice(String stylePrice) {
        this.stylePrice = stylePrice;
    }

    public int getGoodsId() {
        return goodsId;
    }

    private String gid;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;

    }
}
