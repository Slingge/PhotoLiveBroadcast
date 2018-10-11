package com.photolivebroadcast.ui.mine.model;

/**
 * Created by zhf on 2018/10/11.
 */

public class OrderNumModel {

    /**
     * code : 200
     * count : 0
     * data : {"id":26,"out_trade_no":"nSTafYPk0fc585acc143407489d034e30bb40e55","goodsname":"中级套餐","totalamount":"366","remark":"6个相册，不限照片数量相册6","userid":"0fc585acc143407489d034e30bb40e55","paystatus":"0","createtime":null}
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
         * id : 26
         * out_trade_no : nSTafYPk0fc585acc143407489d034e30bb40e55
         * goodsname : 中级套餐
         * totalamount : 366
         * remark : 6个相册，不限照片数量相册6
         * userid : 0fc585acc143407489d034e30bb40e55
         * paystatus : 0
         * createtime : null
         */

        private int id;
        private String out_trade_no;
        private String goodsname;
        private String totalamount;
        private String remark;
        private String userid;
        private String paystatus;
        private Object createtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getGoodsname() {
            return goodsname;
        }

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getTotalamount() {
            return totalamount;
        }

        public void setTotalamount(String totalamount) {
            this.totalamount = totalamount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getPaystatus() {
            return paystatus;
        }

        public void setPaystatus(String paystatus) {
            this.paystatus = paystatus;
        }

        public Object getCreatetime() {
            return createtime;
        }

        public void setCreatetime(Object createtime) {
            this.createtime = createtime;
        }
    }
}
