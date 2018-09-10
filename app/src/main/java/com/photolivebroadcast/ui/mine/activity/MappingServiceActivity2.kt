package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.PayMapingDialog
import com.photolivebroadcast.ui.mine.callBack.PayMapingCallBack
import kotlinx.android.synthetic.main.activity_mapping_service1.*

/**
 * 修图服务
 * Created by Slingge on 2018/9/8.
 */
class MappingServiceActivity2 : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapping_service2)
        init()
    }


    private fun init() {
        inittitle("预约修图")
        StatusBarWhiteColor()

        tv_next.setOnClickListener { v ->

        }
    }


}