package com.photolivebroadcast.ui.mine.model

/**
 * Created by Slingge on 2018/9/27.
 */
class MySendModel {


    var code = 0
    var count = ""
    var msg = ""
    var data = dataModel()

    class dataModel {
        var listalbums = ArrayList<listalbumsModel>()
    }

    class listalbumsModel {
        var id = ""
        var title = ""
        var country = ""

        var city = ""
        var remark = ""
        var type = ""

        var share_title = ""
        var share_remark = ""
        var readnum = ""

        var createtime = ""
        var isvalid = ""
        var userid = ""

        var isopen = ""
        var bgimage = ""
        var share_img = ""

        var yindao_img = ""
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