package com.photolivebroadcast.ui.photoLive.http

import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject
import java.io.File

/**
 * Created by Slingge on 2018/10/3.
 */
object UpAlbumPhotoHttp {

    interface UpResultCallBack {
        fun result(result: Boolean)
    }

    fun upPhoto(pid: String, mid: String, path: String, upResultCallBack: UpResultCallBack) {
        val file = File(path)
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/file/ajaxaddimgappmore")
                .addFile("file", file.name, file)
                .addParams("pid", pid)
                .addParams("mid", mid)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        abLog.e("上传相册图片",response)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            upResultCallBack.result(true)
                        } else {
                            upResultCallBack.result(false)
                        }
                    }
                })
    }


}