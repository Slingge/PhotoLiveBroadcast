package com.photolivebroadcast.ui.establish.result

import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * 发送短信
 * Created by Slingge on 2018/9/17.
 */
object SendMSMrHttp {

    interface SendMsmCallBack{
        fun send()
    }


    fun regist(phone: String, code: String,sendMsmCallBack: SendMsmCallBack) {

        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/user/user/ajaxsendSMS")
                .addParams("phone", phone).addParams("code", code).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            ToastUtil.showToast("短信已发送，请注意查收")
                            sendMsmCallBack.send()
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })

    }


}