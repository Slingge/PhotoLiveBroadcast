package com.photolivebroadcast.ui.mine.model

import java.io.Serializable

/**
 * 套餐
 * Created by Slingge on 2018/9/27.
 */
class SetMealaMyModel : Serializable {


    var code = 0
    var count = ""
    var msg = ""
    var data = dataModel()

    class dataModel {
        var listbuys = ArrayList<listbuysModel>()
    }


    class listbuysModel : Serializable {
        var id = 0
        var tcid = ""//
        var userid = 0//

        var createtime =0//
        var ordernumber = 0.0//
    }

}