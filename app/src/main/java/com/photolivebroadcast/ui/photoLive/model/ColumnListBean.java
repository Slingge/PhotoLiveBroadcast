package com.photolivebroadcast.ui.photoLive.model;

import java.util.List;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class ColumnListBean {

    /**
     * code : 200
     * count : 0
     * data : [{"id":3,"pid":17,"title":"ACL","createtime":"2018-10-10 17:32","isvalid":"Y"}]
     * msg : ok
     */

    private int code;
    private int count;
    private String msg;
    private List<DataBean> data;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * pid : 17
         * title : ACL
         * createtime : 2018-10-10 17:32
         * isvalid : Y
         */

        private int id;
        private int pid;
        private String title;
        private String createtime;
        private String isvalid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getIsvalid() {
            return isvalid;
        }

        public void setIsvalid(String isvalid) {
            this.isvalid = isvalid;
        }
    }
}
