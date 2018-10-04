package com.photolivebroadcast.ui.newAlbum.result

import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.photolivebroadcast.util.StatickUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileDescriptor
import java.io.InvalidObjectException

/**
 * 上传所有图片
 * Created by Slingge on 2018/9/27.
 */
object UpPublicPhoto {


    interface UpPhotoCallBack {
        fun upHeaderUrl(url: String)
    }

    fun upPhoto(imagelist: String, upPhotoCallBack: UpPhotoCallBack) {
        val file = File(imagelist)
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/file/publicuploadimg")
                .addFile("file", file.name,file)//文件数组
                .addParams("userid", StatickUtil.uid)//用户id
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        abLog.e("上传图片", response)
                        if (obj.getInt("code") ==200) {
                            val array = JSONArray(obj.getString("data"))
                            for (i in 0 until array.length()) {
                                upPhotoCallBack.upHeaderUrl(array[i].toString())
                            }
                        }
                    }
                })
    }


}