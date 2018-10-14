package com.photolivebroadcast.ui.mine.result

import com.google.gson.Gson
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.ui.mine.model.ServiceNumModel
import com.photolivebroadcast.util.StatickUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2018/10/13.
 */
object MappingServiceHttp {


    //获取修图服务次数
    fun getServiceNum() {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxcheckmyxiutuinuse")
                .addParams("uid",StatickUtil.uid)  .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val model = Gson().fromJson(response, ServiceNumModel::class.java)
                        if(model.code==200){
                            EventBus.getDefault().post(model)
                        }else{
                            ToastUtil.showToast(model.msg)
                        }
                    }
                })
    }


    fun SendMappingService(remark:String,endtime:String){
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxaddmyxiutu")
                .addParams("pid","").addParams("userid",StatickUtil.uid).addParams("remark",remark)
                .addParams("endtime",endtime).addParams("photonum","")
                .build().execute(object :StrCallback(){
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)

                    }
                })
    }

}