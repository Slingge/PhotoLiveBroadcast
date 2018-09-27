package com.photolivebroadcast.ui.mine.result

import com.google.gson.Gson
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.ui.mine.model.MySendModel
import com.photolivebroadcast.util.StatickUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * 我的套餐
 * Created by Slingge on 2018/9/27.
 */
object MyMealHttp {

    fun meal() {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/user/user/ajaxlistmymenus")
                .addParams("userid", StatickUtil.uid)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
//                        val model = Gson().fromJson(response, MySendModel::class.java) as MySendModel
//                        if (model.code == 200) {
//                            EventBus.getDefault().post(model.data)
//                        } else {
//                            ToastUtil.showToast(model.msg)
//                        }
                    }
                })

    }


}