package com.photolivebroadcast.ui.photoLive.http

import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/**
 * Created by Slingge on 2018/10/3.
 */
object UpAlbumPhotoHttp {

    interface UpResultCallBack {
        fun result(url: String)
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
                        abLog.e("上传相册图片", response)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            val array = JSONArray(obj.getString("data"))
                            upResultCallBack.result(array[0].toString())
                        } else {
                            upResultCallBack.result("")
                        }
                    }
                })
    }


}