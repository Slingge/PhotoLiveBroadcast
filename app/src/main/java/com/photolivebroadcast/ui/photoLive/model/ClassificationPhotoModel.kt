package com.photolivebroadcast.ui.photoLive.model

/**
 * Created by Slingge on 2018/10/3.
 */
class ClassificationPhotoModel {

    var code = 0
    var count = -1
    var msg = ""
    var data = dataModel()

    class dataModel {
        var pageData = pageDataModel()
    }


    class pageDataModel {
        var offset = ""
        var id = -1
        var limit = ""

        var total = 1
        var size = ""
        var pages = ""

        var current = 0
        var searchCount = true
        var openSort = true
        var orderByField = ""

        var records = ArrayList<recordsModel>()
    }

    class recordsModel {
        var id = -1
        var pid = -1

        var mid = -1

        var imgurl = ""

        var isvalid = ""
        var createtime = ""
    }


}