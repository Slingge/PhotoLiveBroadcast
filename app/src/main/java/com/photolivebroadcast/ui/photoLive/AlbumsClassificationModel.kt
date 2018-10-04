package com.photolivebroadcast.ui.photoLive

import java.io.Serializable

/**
 * Created by Slingge on 2018/9/28.
 */
class AlbumsClassificationModel : Serializable {

    var code = 0
    var count = 0
    var msg = ""

    var data = ArrayList<dataModel>()

    var model=dataModel()

    class dataModel : Serializable {
        var id = ""
        var menu_name = ""
        var pid = ""
        var orderid = ""
    }


}