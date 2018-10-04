package com.photolivebroadcast.ui.mine.result

import android.app.Activity
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.ui.mine.model.MySendModel
import com.photolivebroadcast.util.StatickUtil
import com.zhy.http.okhttp.OkHttpUtils
import kotlinx.android.synthetic.main.activity_enterprise_authentication1.view.*
import org.greenrobot.eventbus.EventBus

/**
 * 我发出的
 * Created by Slingge on 2018/9/27.
 */
object MySendHttp {

    fun mySend() {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/user/user/ajaxlistalbums")
                .addParams("userid", StatickUtil.uid)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        abLog.e("我发出的.....",response)
                        val model = Gson().fromJson(response, MySendModel::class.java) as MySendModel
                        if (model.code == 200) {
                            if(model.data!=null){
                                EventBus.getDefault().post(model.data)
                            }
                        } else {
                            ToastUtil.showToast(model.msg)
                        }
                    }
                })

    }


}