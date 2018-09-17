package com.photolivebroadcast.ui.establish.result

import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * Created by Slingge on 2018/9/17.
 */
object SginHttp{

    interface SginCallBack{
        fun send()
    }


    fun sgin(phone: String, code: String,sginCallBack: SginCallBack) {

        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/user/user/ajaxuserlogin")
                .addParams("phone", phone).addParams("code", code).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            ToastUtil.showToast("登录成功")
                            sginCallBack.send()
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })
    }


}