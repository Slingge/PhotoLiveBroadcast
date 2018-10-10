package com.photolivebroadcast.ui.photoLive.model;

/**
 * Created by zhf on 2018/10/10.
 */

public class CameramanCodeBean {

    /**
     * code : 200
     * count : 0
     * data : {"incode":"qNknLDnK"}
     * msg : ok
     */

    private int code;
    private int count;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * incode : qNknLDnK
         */

        private String incode;

        public String getIncode() {
            return incode;
        }

        public void setIncode(String incode) {
            this.incode = incode;
        }
    }
}
