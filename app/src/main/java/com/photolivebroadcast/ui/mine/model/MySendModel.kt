package com.photolivebroadcast.ui.mine.model

import java.io.Serializable

/**
 * Created by Slingge on 2018/9/27.
 */
class MySendModel : Serializable {


    var code = 0
    var count = ""
    var msg = ""
    var data = dataModel()

    class dataModel {
        var listalbums = ArrayList<listalbumsModel>()

        val album=listalbumsModel()
    }


    class listalbumsModel : Serializable {
        var id = 0
        var title = ""
        var country = ""

        var city = ""
        var remark = ""
        var type = ""

        var share_title = ""
        var share_remark = ""
        var readnum = 0

        var createtime = ""
        var isvalid = ""
        var userid = ""

        var isopen = ""
        var bgimage = ""//广告图
        var share_img = ""//logo，分享图

        var yindao_img = ""//引导图
        var isxiutu = ""
        var event_title = ""

        var event_remark = ""
        var start_time = ""
        var end_time = ""

        var address = ""
        var zb_wechat = ""
        var event_liucheng = ""
    }

}