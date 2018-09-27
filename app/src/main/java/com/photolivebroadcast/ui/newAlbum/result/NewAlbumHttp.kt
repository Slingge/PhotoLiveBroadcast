package com.photolivebroadcast.ui.newAlbum.result

import android.app.Activity
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.util.StatickUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * Created by Slingge on 2018/9/27.
 */
object NewAlbumHttp {


    fun newAlbum(context: Activity, title: String, remark: String, yindao_img: String, bgimage: String, share_img: String,
                 isxiutu: String) {

        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxaddphotoablums?")
                .addParams("userid", StatickUtil.uid).addParams("title", title).addParams("remark", remark)
                .addParams("yindao_img", yindao_img).addParams("bgimage", bgimage).addParams("share_img", share_img)
                .addParams("isxiutu", isxiutu).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            ToastUtil.showToast("创建成功")
                            context.finish()
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })


    }


}