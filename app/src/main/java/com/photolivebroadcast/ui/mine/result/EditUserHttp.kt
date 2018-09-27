package com.photolivebroadcast.ui.mine.result

import android.app.Activity
import android.content.Context
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.util.StatickUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * Created by Slingge on 2018/9/27.
 */
object EditUserHttp {


    fun edit(context: Activity, nickname: String, sex: String) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/user/user/ajaxedituserinfo?")
                .addParams("userid", StatickUtil.uid).addParams("nickname", nickname)
                .addParams("sex", sex).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            StatickUtil.userModel.nickname = nickname
                            StatickUtil.userModel.sex = sex
                            context.finish()
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })

    }


}