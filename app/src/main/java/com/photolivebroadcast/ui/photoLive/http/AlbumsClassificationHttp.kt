package com.photolivebroadcast.ui.photoLive.http

import com.google.gson.Gson
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.ui.photoLive.AlbumsClassificationModel
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2018/9/28.
 */
object AlbumsClassificationHttp {


    fun Classification(pid: String) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxgetlistmens")
                .addParams("pid", pid).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val model = Gson().fromJson(response, AlbumsClassificationModel::class.java)
                        if (model.code == 200) {
                            EventBus.getDefault().post(model)
                        } else {
                            ToastUtil.showToast(model.msg)
                        }
                    }
                })
    }


    //分类下的相册
    fun ClassificationAlbum(pid: String, mid: String) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxgetlistimgs")
                .addParams("pid", pid).addParams("mid", mid).addParams("pageNumber", "1")
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        /*val model = Gson().fromJson(response, AlbumsClassificationModel::class.java)
                        if (model.code == 200) {
                            EventBus.getDefault().post(model)
                        } else {
                            ToastUtil.showToast(model.msg)
                        }*/
                    }
                })
    }


}