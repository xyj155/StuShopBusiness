package com.example.stushopbusiness.gson;

public class UserGson {

    /**
     * id : 1
     * shopName : 官方自营店
     * shopIcon : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550413231&di=b76387ba44b383d64db030929caf30bc&imgtype=jpg&er=1&src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F01%2F38%2F17%2F90573c76cbd2eba.jpg
     * shopOwner : 0
     * shopOwnerAddress : 浙江省嘉兴市南湖区嘉兴学院梁林校区
     * shopOwnerTelphone : 17374131273
     * shopOwnerCity : 浙江 嘉兴
     * shopPostFree : null
     * isReturn : null
     * isVerify : null
     * imToken : null
     * username : 123
     * password : 123
     */

    private int id;
    private String shopName;
    private String shopIcon;
    private String shopOwner;
    private String shopOwnerAddress;
    private String shopOwnerTelphone;
    private String shopOwnerCity;
    private Object shopPostFree;
    private Object isReturn;
    private Object isVerify;
    private Object imToken;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public String getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    public String getShopOwnerAddress() {
        return shopOwnerAddress;
    }

    public void setShopOwnerAddress(String shopOwnerAddress) {
        this.shopOwnerAddress = shopOwnerAddress;
    }

    public String getShopOwnerTelphone() {
        return shopOwnerTelphone;
    }

    public void setShopOwnerTelphone(String shopOwnerTelphone) {
        this.shopOwnerTelphone = shopOwnerTelphone;
    }

    public String getShopOwnerCity() {
        return shopOwnerCity;
    }

    public void setShopOwnerCity(String shopOwnerCity) {
        this.shopOwnerCity = shopOwnerCity;
    }

    public Object getShopPostFree() {
        return shopPostFree;
    }

    public void setShopPostFree(Object shopPostFree) {
        this.shopPostFree = shopPostFree;
    }

    public Object getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Object isReturn) {
        this.isReturn = isReturn;
    }

    public Object getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(Object isVerify) {
        this.isVerify = isVerify;
    }

    public Object getImToken() {
        return imToken;
    }

    public void setImToken(Object imToken) {
        this.imToken = imToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
