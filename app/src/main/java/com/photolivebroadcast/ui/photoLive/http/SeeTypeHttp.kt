package com.photolivebroadcast.ui.photoLive.http

import android.app.Activity
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * 相册观看方式 免费。收费
 * Created by Slingge on 2018/9/29
 */
object SeeTypeHttp {

    interface SeeTypeCallBack {
        fun seeType(type: String)
    }

    fun seeType(pid: String, seeTypeCallBack: SeeTypeCallBack) {

        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxgetlooktype")
                .addParams("pid", pid)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            val data = JSONObject(obj.getString("data"))
                            seeTypeCallBack.seeType(data.getString("photolook"))
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })
    }

    fun seeTypeUpdate(context: Activity, pid: String, looktype: String, lookprice: String, lookcode: String) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxsavelooktype")
                .addParams("pid", pid).addParams("looktype", looktype)
                .addParams("lookprice", lookprice) .addParams("lookcode", lookcode)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            context.finish()
                            ToastUtil.showToast("修改成功")
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })

    }


}