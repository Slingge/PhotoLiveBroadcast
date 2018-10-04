package com.photolivebroadcast.ui.photoLive.http

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.ui.photoLive.AddClassificationAlbumsModel
import com.photolivebroadcast.ui.photoLive.AlbumsClassificationModel
import com.photolivebroadcast.ui.photoLive.model.ClassificationPhotoModel
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

    interface ClassificationCallBack{
        fun classifi(model: AlbumsClassificationModel)
    }

    //获取分类
    fun Classification2(pid: String,classificationCallBack: ClassificationCallBack) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxgetlistmens")
                .addParams("pid", pid).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val model = Gson().fromJson(response, AlbumsClassificationModel::class.java)
                        if (model.code == 200) {
                            classificationCallBack.classifi(model)
                        } else {
                            ToastUtil.showToast(model.msg)
                        }
                    }
                })
    }


    //分类下的图片
    fun ClassificationAlbum(pid: String, mid: String) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxgetlistimgs/1/$pid/$mid")
                .addParams("pid", pid).addParams("mid", mid).addParams("pageNumber", "1")
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        abLog.e("获取相册图片", response)
                        val model = Gson().fromJson(response, ClassificationPhotoModel::class.java)
                        if (model.code == 200) {
                            EventBus.getDefault().post(model.data.pageData)
                        } else {
                            ToastUtil.showToast(model.msg)
                        }
                    }
                })
    }

    interface DelClassificationCallBack {
        fun delClassification()
    }

    //删除分类
    fun DelClassification(mid: String, delClassificationCallBack: DelClassificationCallBack) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxdeletemenusbymid")
                .addParams("mid", mid)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            delClassificationCallBack.delClassification()
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })
    }

    //编辑分类名称
    fun EditClassification(mid: String, menu_name: String, delClassificationCallBack: DelClassificationCallBack) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxeditmenusbymid")
                .addParams("mid", mid).addParams("menu_name", menu_name)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            delClassificationCallBack.delClassification()
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })
    }


    interface AddClassificationCallBack {
        fun addClassification(classModel: AlbumsClassificationModel.dataModel)
    }


    //新增分类
    fun AddClassification(pid: String, menu_name: String, addClassification: AddClassificationCallBack) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxaddmenusbypid")
                .addParams("menu_name", menu_name).addParams("pid", pid)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        abLog.e("新增相册分类", response)
                        val model = Gson().fromJson(response, AddClassificationAlbumsModel::class.java)
                        if (model.code == 200) {
                            addClassification.addClassification(model.data)
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })
    }


}