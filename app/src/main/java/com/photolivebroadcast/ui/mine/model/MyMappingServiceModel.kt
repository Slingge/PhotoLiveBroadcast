package com.photolivebroadcast.ui.mine.model

/**
 * Created by Slingge on 2018/10/20.
 */
class MyMappingServiceModel {


    var code = 0
    var count = -1
    var msg = ""

    var data = dataModel()

    class dataModel {
        var listxiutus = ArrayList<listxiutusModel>()
    }

    class listxiutusModel {
        var id = -1
        var pid = -1
        var userid = ""
        var remark = ""
        var status = ""

        var endtime = ""
        var photonum = 0
        var createtime = ""

        var isvalid = ""
        var inxiutunum = 0
    }


}