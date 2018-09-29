package com.photolivebroadcast.ui

import android.os.Bundle
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R

/**
 * Created by Slingge on 2018/9/2.
 */
class TempActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }


    private fun init() {
        inittitle("")
        StatusBarWhiteColor()

    }


}