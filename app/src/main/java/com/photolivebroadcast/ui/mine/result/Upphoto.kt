package com.photolivebroadcast.ui.mine.result

import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.photolivebroadcast.util.StatickUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject
import java.io.File
import java.io.InvalidObjectException

/**
 * 上传头像
 * Created by Slingge on 2018/9/27.
 */
object Upphoto {


    interface UpPhotoCallBack{
        fun upHeaderUrl(url:String)
    }

    fun upPhoto(imagelist: ArrayList<String>,upPhotoCallBack: UpPhotoCallBack) {

        val fileList = ArrayList<File>()
        for (i in 0 until imagelist.size) {
            fileList.add(File(imagelist[i]))
        }

        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/file/uploadimgurl?")
                .files("file", fileList)//文件数组
                .addParams("userid", StatickUtil.uid)//用户id
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        abLog.e("上传头像", response)
                        if (obj.getString("") == "") {
                            upPhotoCallBack.upHeaderUrl("")
                        }
                    }
                })

    }


}