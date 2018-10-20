package com.photolivebroadcast.ui.mine.result

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.alipay.sdk.app.PayTask
import com.google.gson.Gson
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.model.MappingServicePriceModel
import com.photolivebroadcast.ui.mine.model.MyMappingServiceModel
import com.photolivebroadcast.ui.mine.model.ServiceNumModel
import com.photolivebroadcast.ui.mine.pop.PopPay
import com.photolivebroadcast.ui.photoLive.model.WXpayBean
import com.photolivebroadcast.util.StatickUtil
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhy.http.okhttp.OkHttpUtils
import okhttp3.Call
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

@SuppressLint("StaticFieldLeak")
/**
 * Created by Slingge on 2018/10/13.
 */
object MappingServiceHttp {

    private var context: Activity? = null
    private var orderId = ""
    private var orderInfo = ""   // 订单信息

    //获取修图服务次数
    fun getServiceNum() {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxcheckmyxiutuinuse")
                .addParams("uid", StatickUtil.uid).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val model = Gson().fromJson(response, ServiceNumModel::class.java)
                        if (model.code == 200) {
                            EventBus.getDefault().post(model)
                        } else {
                            ToastUtil.showToast(model.msg)
                        }
                    }
                })
    }

    //我的修图服务
    fun MyMappingService() {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxlistmyxiutu")
                .addParams("userid", StatickUtil.uid)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val model = Gson().fromJson(response, MyMappingServiceModel::class.java)
                        if (model.code == 200) {
                            EventBus.getDefault().post(model.data.listxiutus)
                        } else {
                            ToastUtil.showToast(model.msg)
                        }
                    }
                })

    }


    //发布修图服务,获取订单号
    fun SendMappingService(context: Activity, tv: TextView, totalamount: String, remark: String, endtime: String) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxlistmyxiutu")
                .addParams("userid", StatickUtil.uid).addParams("remark", remark)
                .addParams("endtime", endtime).addParams("totalamount", totalamount)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            val data = JSONObject(obj.getString("data"))
                            val out_trade_no = data.getString("out_trade_no")
                            this@MappingServiceHttp.context = context
                            orderId = out_trade_no

                            if (popPay == null) {
                                msgApi.registerApp("wx0d0b4d8b589ef4e6")
                                popPay = PopPay(context, onclick)
                            }
                            popPay!!.showAtLocation(tv, Gravity.BOTTOM, 0, 0)
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })
    }


    var popPay: PopPay? = null
    //支付修改图服务
    /**
     * 支付宝支付
     */
    private fun alipay() {
        ProgressDialog.showDialog(context!!)
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/alipay/appPayRequestforxiutu")
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        orderInfo = response
                        // 必须异步调用
                        val payThread = Thread(payRunnable)
                        payThread.start()
                    }
                })
    }


    private val onclick = object : View.OnClickListener {
        override fun onClick(p0: View?) {
            when (p0!!.id) {
                R.id.rel_alipay -> {
                    alipay()
                }
                R.id.rel_wx_pay -> {
                    wxPay()
                }
                R.id.tv_submit -> {
                    popPay!!.dismiss()
                }
            }
        }
    }

    internal val msgApi = WXAPIFactory.createWXAPI(context, null)

    /**
     * 微信支付
     */
    private fun wxPay() {
        ProgressDialog.showDialog(context!!)
        OkHttpUtils.post().url("hhttp://112.74.169.87/videoCloud/tc/wxPrePayforxiutu")
                .addParams("orderid", "" + orderId).build().execute(object : StrCallback() {
                    override fun onError(call: Call, e: Exception, id: Int) {
                        super.onError(call, e, id)
                    }

                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val gson = Gson()
                        val bean = gson.fromJson(response, WXpayBean::class.java)
                        if (bean!!.code.equals("200")) {
                            val request = PayReq()
                            request.appId = bean!!.msg.appid
                            request.partnerId = bean!!.msg.partnerid
                            request.prepayId = bean!!.msg.prepayid
                            request.packageValue = bean!!.msg.packageX
                            request.nonceStr = bean!!.msg.noncestr
                            request.timeStamp = bean!!.msg.timestamp
                            request.sign = bean!!.msg.sign
                            msgApi.sendReq(request)
                        } else {
                            ToastUtil.showToast("请求失败")
                        }
                    }
                })
    }


    internal var payRunnable: Runnable = Runnable {
        val alipay = PayTask(context)

        Log.e("TAG", "orderInfo=" + orderInfo)
        val param = alipay.payV2(orderInfo, true)

        val msg = Message()
        msg.what = 1
        msg.obj = param["resultStatus"]
        mHandler.sendMessage(msg)
    }

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val result = msg.obj as String
            if (result == "9000") {
                context!!.finish()
                Toast.makeText(context, "购买成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "购买失败", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //获取修图服务价格、数量
    fun MappingServicePrice() {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxlistallxiutuprices")
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val model = Gson().fromJson(response, MappingServicePriceModel::class.java)
                        if (model.code == 200) {
                            EventBus.getDefault().post(model.data.listxiutuprices)
                        } else {
                            ToastUtil.showToast(model.msg)
                        }
                    }
                })
    }

}