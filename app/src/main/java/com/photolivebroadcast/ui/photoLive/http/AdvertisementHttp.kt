package com.photolivebroadcast.ui.photoLive.http

import android.app.Activity
import android.content.Context
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * 添加广告
 * Created by Slingge on 2018/10/20.
 */
object AdvertisementHttp {


    /**
     *
     * words 文字提示
     * type 参数值为汉字：图片，跳转，预约
     * */
    fun add(context: Activity, pid: String, words: String, type: String, imgurl: String, hrefurl: String) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxtsaveadvertisements")
                .addParams("pid", pid).addParams("words", words).addParams("type", type)
                .addParams("imgurl", imgurl).addParams("hrefurl", hrefurl)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            ToastUtil.showToast("广告设置成功")
                            context.finish()
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })
    }


}