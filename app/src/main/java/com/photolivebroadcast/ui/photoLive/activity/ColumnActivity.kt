package com.photolivebroadcast.ui.photoLive.activity

import android.os.Bundle
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R

/**
 * 分栏条
 * Created by Slingge on 2018/9/29
 */
class ColumnActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_column)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("分栏条")


    }

}