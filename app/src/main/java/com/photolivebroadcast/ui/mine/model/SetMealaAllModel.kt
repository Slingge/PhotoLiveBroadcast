package com.photolivebroadcast.ui.mine.model

import java.io.Serializable

/**
 * 套餐
 * Created by Slingge on 2018/9/27.
 */
class SetMealaAllModel : Serializable {


    var code = 0
    var count = ""
    var msg = ""
    var data = dataModel()

    class dataModel {
        var listtcs = ArrayList<listtcsModel>()
    }


    class listtcsModel : Serializable {
        var id = 0
        var tcname = ""//套餐名称
        var album_number = 0//对应的相册数量

        var video_number =0//视频直播数量
        var price = 0.0//对应的价格
        var isvalid = ""//是否可用 Y：可用，N：不可用

        var remark = ""//套餐简介
        var userid = ""//创建者
        var createtime = ""//创建时间

        var type = ""//套餐类别
    }

}