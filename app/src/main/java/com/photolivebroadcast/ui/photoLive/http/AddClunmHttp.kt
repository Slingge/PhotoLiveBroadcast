package com.photolivebroadcast.ui.photoLive.http

import android.app.Activity
import com.google.gson.Gson
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.ui.mine.model.MySendModel
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * 添加分栏条
 * Created by Slingge on 2018/9/28.
 */
object AddClunmHttp {
    fun addClunm(context: Activity, pid: String, content: String) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxsavefenlan")
                .addParams("pid", pid)
                .addParams("title", content).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, MySendModel::class.java)
                if (model.code == 200) {
                    ToastUtil.showToast("保存成功")
                    context.finish()
                } else {
                    ToastUtil.showToast(model.msg)
                }
            }
        })
    }


    //修改相册基本信息
    fun editAlbum(context: Activity, pid: String, title: String, remark: String, yindao_img: String, bgimage: String, share_img: String,
                  isxiutu: String) {

        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxeditalbuminfo")
                .addParams("pid", pid).addParams("title", title).addParams("remark", remark)
                .addParams("yindao_img", yindao_img).addParams("bgimage", bgimage).addParams("share_img", share_img)
                .addParams("isxiutu", isxiutu).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getInt("code") == 200) {
                    ToastUtil.showToast("修改成功")
                    context.finish()
                } else {
                    ToastUtil.showToast(obj.getString("msg"))
                }
            }
        })


    }


}