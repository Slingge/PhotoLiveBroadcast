package com.photolivebroadcast.ui.photoLive.http

import android.app.Activity
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 *  拍摄活动信息
 * Created by Slingge on 2018/9/29
 */
object ShotImageHttp {

    fun shot(context: Activity, pid: String, event_remark: String, start_time: String, end_time: String, address: String, zb_wechat: String, event_liucheng: String) {

        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxediteventinfo")
                .addParams("pid", pid).addParams("start_time", start_time).addParams("end_time", end_time)
                .addParams("address", address).addParams("event_remark", event_remark)
                .addParams("event_liucheng", event_liucheng).addParams("zb_wechat", zb_wechat)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            ToastUtil.showToast("保存成功")
                            context.finish()
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })


    }


}