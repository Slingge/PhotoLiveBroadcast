package com.photolivebroadcast.ui.establish

/**
 * Created by Slingge on 2018/9/27.
 */
class SginModel {

    var code = 0
    var count = ""
    var msg = ""

    var data=dataModel()

    class dataModel {
        var id = ""
        var status = ""
        var iscompany = ""//N：个人 / Y：企业

        var sex = ""
        var address = ""

        var nickname = ""
        var imgurl = ""

        var phone = ""
        var password = ""
    }

}