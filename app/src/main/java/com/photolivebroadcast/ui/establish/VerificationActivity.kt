package com.photolivebroadcast.ui.establish

import android.os.Bundle
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MainActivity
import com.photolivebroadcast.ui.MyApplication
import kotlinx.android.synthetic.main.activity_verification.*

/**
 * Created by Slingge on 2018/9/2.
 */
class VerificationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()

        tv_sgin.setOnClickListener { v ->
            MyApplication.openActivity(this, MainActivity::class.java)
        }
    }


}