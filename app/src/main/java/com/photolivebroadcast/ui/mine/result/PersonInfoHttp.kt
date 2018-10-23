package com.photolivebroadcast.ui.mine.result

import com.google.gson.Gson
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.ui.establish.SginModel
import com.photolivebroadcast.util.StatickUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * Created by Slingge on 2018/10/21.
 */
object PersonInfoHttp {


    fun Person() {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/user/user/ajaxgetuserinfo")
                .addParams("userid", StatickUtil.uid).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            StatickUtil.userModel = Gson().fromJson(obj.getString("data"), SginModel.dataModel::class.java)
                            StatickUtil.sex = StatickUtil.userModel.sex
                            StatickUtil.headerUrl=StatickUtil.userModel.imgurl
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })
    }


}