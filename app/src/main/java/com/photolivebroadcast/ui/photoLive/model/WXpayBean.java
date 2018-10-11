package com.photolivebroadcast.ui.photoLive.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zhf on 2018/7/6.
 */

public class WXpayBean implements Serializable{

    /**
     * msg : {"appid":"wx0d0b4d8b589ef4e6","noncestr":"yZnDZXY3IXEl8xGy","package":"Sign=WXPay","partnerid":"1516429591","prepayid":null,"sign":"19322ECB5A3CC92B9987C08580FBA74E","timestamp":1539226351}
     * code : 200
     */

    private MsgBean msg;
    private String code;

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class MsgBean {
        /**
         * appid : wx0d0b4d8b589ef4e6
         * noncestr : yZnDZXY3IXEl8xGy
         * package : Sign=WXPay
         * partnerid : 1516429591
         * prepayid : null
         * sign : 19322ECB5A3CC92B9987C08580FBA74E
         * timestamp : 1539226351
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
