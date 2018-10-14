package com.photolivebroadcast.ui.mine.result

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.ui.mine.model.SetMealaAllModel
import com.photolivebroadcast.ui.mine.model.SetMealaMyModel
import com.photolivebroadcast.util.StatickUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * 套餐
 * Created by Slingge on 2018/10/9.
 */
object SetMealHttp {

    //所有的套餐
    fun SetMealAll() {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/user/user/ajaxlistallmenus")
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val model = Gson().fromJson(response, SetMealaAllModel::class.java)
                        if (model.code == 200) {
                            EventBus.getDefault().post(model.data.listtcs)
                        } else {
                            ToastUtil.showToast(model.msg)
                        }
                    }
                })
    }


    fun myMeal() {
        abLog.e("uid",StatickUtil.uid)
        OkHttpUtils.post().url("http://www.suxianglive.com/videoCloud/user/user/ajaxlistmymenus")
                .addParams("userid", StatickUtil.uid)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val model = Gson().fromJson(response, SetMealaMyModel::class.java)
                        if (model.code == 200) {
                            if (model.data != null) {
                                EventBus.getDefault().post(model.data.listbuys)
                            }
                        } else {
                            ToastUtil.showToast(model.msg)
                        }
                    }
                })
    }


}