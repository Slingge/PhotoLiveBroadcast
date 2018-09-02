package com.photolivebroadcast.ui.establish.activity

import android.os.Bundle
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R

/**
 * 忘记密码
 * Created by Slingge on 2018/9/2.
 */
class ForgetPassActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgetpass)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()

    }


}