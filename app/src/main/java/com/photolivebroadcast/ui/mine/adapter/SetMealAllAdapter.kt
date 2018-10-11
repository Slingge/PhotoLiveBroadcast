package com.photolivebroadcast.ui.mine.adapter

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Message
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.alipay.sdk.app.PayTask
import com.google.gson.Gson
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.model.MySendModel
import com.photolivebroadcast.ui.mine.model.OrderNumModel
import com.photolivebroadcast.ui.mine.model.SetMealaAllModel
import com.photolivebroadcast.ui.mine.pop.PopPay
import com.photolivebroadcast.ui.photoLive.model.WXpayBean
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhy.http.okhttp.OkHttpUtils
import okhttp3.Call

/**
 * Created by Slingge on 2018/10/9.
 */
class SetMealAllAdapter(val context: Activity, val setMealList: ArrayList<SetMealaAllModel.listtcsModel>) : RecyclerView.Adapter<SetMealAllAdapter.ViewHolder>() {

    var popPay: PopPay? = null

    var orderId: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_setmeal, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        ToastUtil.showToast(setMealList.size.toString())
        return setMealList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = setMealList[position]

        if (model.type == "相册") {
            holder.image.setImageResource(R.drawable.icon_toucan01)
        } else if (model.type == "视频") {
            holder.image.setImageResource(R.drawable.icon_taocan04)
        }

        holder.tv_album5.text = model.remark
        holder.tv_money5.text = "￥" + model.price

        holder!!.tv_pay5.setOnClickListener {
            ProgressDialog.showDialog(context)
            // 将该app注册到微信
            msgApi.registerApp("wx0d0b4d8b589ef4e6")
            OkHttpUtils.post().url("http://112.74.169.87/videoCloud/tc/ajaxtoinorder")
                    .addParams("userid", "" + setMealList!!.get(position).userid)
                    .addParams("tid", "" + setMealList!!.get(position).id)
                    .build().execute(object : StrCallback() {
                override fun onResponse(response: String, id: Int) {
                    super.onResponse(response, id)
                    val gson = Gson()
                    val model = gson!!.fromJson(response, OrderNumModel::class.java)
                    if (model!!.code == 200) {
                        orderId = ""+model!!.data.id
                        popPay = PopPay(context, onclick)
                        popPay!!.showAtLocation(holder!!.tv_pay5, Gravity.BOTTOM, 0, 0)
                    } else {

                    }
                }
            })
        }
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

    /**
     * 支付宝支付
     */
    private fun alipay() {
        ProgressDialog.showDialog(context)
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/alipay/appPayRequest")
                .addParams("orderid", orderId).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                orderInfo = response
                // 必须异步调用
                val payThread = Thread(payRunnable)
                payThread.start()
            }
        })
    }

    internal var orderInfo = ""   // 订单信息

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
                context.finish()
                Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "充值失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    internal val msgApi = WXAPIFactory.createWXAPI(context, null)

    /**
     * 微信支付
     */
    private fun wxPay() {
        ProgressDialog.showDialog(context)
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/tc/wxPrePay")
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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val tv_album5 = view.findViewById<TextView>(R.id.tv_album5)
        val tv_money5 = view.findViewById<TextView>(R.id.tv_money5)
        val tv_pay5 = view.findViewById<TextView>(R.id.tv_pay5)
    }


}