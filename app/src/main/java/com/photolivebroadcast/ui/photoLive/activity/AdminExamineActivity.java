package com.photolivebroadcast.ui.photoLive.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.lixin.amuseadjacent.app.ui.base.BaseActivity;
import com.lxkj.huaihuatransit.app.util.StrCallback;
import com.photolivebroadcast.R;
import com.photolivebroadcast.ui.photoLive.model.WXpayBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhf on 2018/9/17.
 */

public class AdminExamineActivity extends BaseActivity implements View.OnClickListener {
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(AdminExamineActivity.this, null);

    private Button btnCopy;
    private Button btnAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_examine);
        StatusBarWhiteColor();
        inittitle("管理审核员");
        // 将该app注册到微信
        msgApi.registerApp("wx0d0b4d8b589ef4e6");

        btnAdd = findViewById(R.id.btn_add);
        btnCopy = findViewById(R.id.btn_copy);

        btnCopy.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

    }

    /**
     * 微信支付
     */
    private void wxPay() {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/tc/wxPrePay")
                .addParams("orderid", "123654799").build().execute(new StrCallback() {
            @Override
            public void onError(@NotNull Call call, @NotNull Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(@NotNull String response, int id) {
                super.onResponse(response, id);
                Gson gson = new Gson();
                WXpayBean bean = gson.fromJson(response, WXpayBean.class);
                PayReq request = new PayReq();
//                request.appId = bean.getAppId();
//                request.partnerId = bean.getPartnerId();
//                request.prepayId = bean.getPrepayId();
//                request.packageValue = bean.getPackageValue();
//                request.nonceStr = bean.getNonceStr();
//                request.timeStamp = bean.getTimeStamp();
//                request.sign = bean.getSign();
//                msgApi.sendReq(request);
            }
        });
    }


    /**
     * 支付宝支付
     */
    private void alipay() {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/alipay/appPayRequest")
                .addParams("orderid", "12345678999").build().execute(new StrCallback() {
            @Override
            public void onResponse(@NotNull String response, int id) {
                super.onResponse(response, id);
                orderInfo = response;
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
        });

    }

    String orderInfo = "";   // 订单信息

    Runnable payRunnable = new Runnable() {
        @Override
        public void run() {
            PayTask alipay = new PayTask(AdminExamineActivity.this);

            Log.e("TAG", "orderInfo=" + orderInfo);
            Map<String, String> param = alipay.payV2(orderInfo, true);

            Message msg = new Message();
            msg.what = 1;
            msg.obj = param.get("resultStatus");
            mHandler.sendMessage(msg);
        }
    };

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result.equals("9000")) {
                finish();
                Toast.makeText(AdminExamineActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AdminExamineActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
            }
        }
    };



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_copy:
//                alipay();
                break;
            case R.id.btn_add:
//                wxPay();
                break;
        }
    }
}
