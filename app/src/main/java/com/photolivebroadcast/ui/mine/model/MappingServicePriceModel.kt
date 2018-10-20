package com.photolivebroadcast.ui.mine.model

/**
 * 修改图服务价格、数量
 * Created by Slingge on 2018/10/20.
 */
class MappingServicePriceModel {

    var code = 0
    var count = -1
    var msg = ""

    var data=dataModel()

    class dataModel {
        var listxiutuprices = ArrayList<listxiutupricesModel>()
    }

    class listxiutupricesModel {

        var id = -1
        var userid = ""
        var tcname = ""
        var price = 0.0
        var remark = ""

        var createtime = ""
        var isvalid = ""
        var xiutunum = 0


    }

}