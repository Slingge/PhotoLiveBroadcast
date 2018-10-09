package com.photolivebroadcast.ui.photoLive.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.lixin.amuseadjacent.app.ui.base.BaseActivity;
import com.photolivebroadcast.R;
import com.photolivebroadcast.ui.photoLive.model.WXpayBean;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Created by zhf on 2018/9/17.
 */

public class AdminExamineActivity extends BaseActivity {
//    final IWXAPI msgApi = WXAPIFactory.createWXAPI(AdminExamineActivity.this, null);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_examine);
        StatusBarWhiteColor();
        inittitle("管理审核员");
        // 将该app注册到微信
//        msgApi.registerApp("wxd22294479b1ff72a");
    }

//    /**
//     * 微信支付
//     */
//    private void wxPay() {
//        WXpayBean bean = gson.fromJson(response, WXpayBean.class);
//        PayReq request = new PayReq();
//        request.appId = bean.getAppId();
//        request.partnerId = bean.getPartnerId();
//        request.prepayId = bean.getPrepayId();
//        request.packageValue = bean.getPackageValue();
//        request.nonceStr = bean.getNonceStr();
//        request.timeStamp = bean.getTimeStamp();
//        request.sign = bean.getSign();
//        msgApi.sendReq(request);
//    }
//
//    /**
//     * 支付宝支付
//     */
//    private void alipay() {
//        orderInfo = response;
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//
//    String orderInfo = "";   // 订单信息
//
//    Runnable payRunnable = new Runnable() {
//        @Override
//        public void run() {
//            PayTask alipay = new PayTask(AdminExamineActivity.this);
//
//            Log.e("TAG", "orderInfo=" + orderInfo);
//            Map<String, String> param = alipay.payV2(orderInfo.substring(1, orderInfo.length() - 1), true);
//
//            Message msg = new Message();
//            msg.what = 1;
//            msg.obj = param.get("resultStatus");
//            mHandler.sendMessage(msg);
//        }
//    };
//
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            String result = (String) msg.obj;
//            if (result.equals("9000")) {
//                finish();
//                Toast.makeText(UserOpenMemberActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(UserOpenMemberActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
}
