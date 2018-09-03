package com.photolivebroadcast.ui.establish

import android.os.Bundle
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R

/**
 * Created by Slingge on 2018/9/2.
 */
class BindPhoneActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bind_phone)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()

    }


}