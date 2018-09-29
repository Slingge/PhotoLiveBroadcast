package com.photolivebroadcast.ui.photoLive.http

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.ui.photoLive.AlbumsClassificationModel
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.util.ArrayList

/**
 * Created by Slingge on 2018/9/28.
 */
object AlbumsClassificationHttp {


    //获取分类
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


    //分类下的图片
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

    interface DelClassificationCallBack{
        fun delClassification()
    }

    //删除分类
    fun DelClassification(mid: String, delClassificationCallBack: DelClassificationCallBack) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxdeletemenusbymid")
                .addParams("mid", mid)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                         val obj=JSONObject(response)
                        if(obj.getInt("code")==200){
                            delClassificationCallBack.delClassification()
                        }else{
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })
    }

    //编辑分类名称
    fun EditClassification(mid: String,menu_name:String, delClassificationCallBack: DelClassificationCallBack) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxeditmenusbymid")
                .addParams("mid", mid).addParams("menu_name", menu_name)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj=JSONObject(response)
                        if(obj.getInt("code")==200){
                            delClassificationCallBack.delClassification()
                        }else{
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })
    }

}