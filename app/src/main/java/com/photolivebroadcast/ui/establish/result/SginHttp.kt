package com.photolivebroadcast.ui.establish.result

import android.content.Context
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.ui.establish.SginModel
import com.photolivebroadcast.util.SharedPreferencesUtil
import com.photolivebroadcast.util.StatickUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * Created by Slingge on 2018/9/17.
 */
object SginHttp {

    interface SginCallBack {
        fun send()
    }


    fun sgin(context: Context, phone: String, code: String, sginCallBack: SginCallBack) {

        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/user/user/ajaxuserlogin")
                .addParams("phone", phone).addParams("code", code).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val model = Gson().fromJson(response, SginModel::class.java)
                        if (model.code == 200) {
                            StatickUtil.uid = model.data.id
                            StatickUtil.sex=model.data.sex
                            StatickUtil.headerUrl=model.data.imgurl

                            StatickUtil.userModel=model.data

                            SharedPreferencesUtil.putSharePre(context, "uid", model.data.id)
                            SharedPreferencesUtil.putSharePre(context, "phone", model.data.phone)

                            abLog.e("登录......", response)
                            ToastUtil.showToast("登录成功")
                            sginCallBack.send()
                        } else {
                            ToastUtil.showToast(model.msg)
                        }
                    }
                })
    }


}