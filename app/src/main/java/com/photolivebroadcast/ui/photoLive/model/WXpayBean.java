package com.photolivebroadcast.ui.photoLive.model;

import java.io.Serializable;

/**
 * Created by zhf on 2018/7/6.
 */

public class WXpayBean implements Serializable{

    /**
     * appId : wxd22294479b1ff72a
     * nonceStr : 1530862401491
     * packageValue : Sign=WXPay
     * partnerId : 1403625402
     * prepayId : wx06153321671241a33ec1e4892529605495
     * sign : 8A3635C204273FDCF0286BDD386311BD
     * timeStamp : 1530862401
     */

    private String appId;
    private String nonceStr;
    private String packageValue;
    private String partnerId;
    private String prepayId;
    private String sign;
    private String timeStamp;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
