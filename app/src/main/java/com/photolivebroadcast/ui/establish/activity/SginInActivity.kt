package com.photolivebroadcast.ui.establish.activity

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import kotlinx.android.synthetic.main.activity_sginin.*

/**
 * Created by Slingge on 2018/9/2.
 */
class SginInActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sginin)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()

        tv_verification.setOnClickListener(this)
        tv_forgetPass.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_verification -> {
                MyApplication.openActivity(this, VerificationActivity::class.java)
            }
            R.id.tv_forgetPass -> {
                MyApplication.openActivity(this, ForgetPassActivity::class.java)
            }
        }
    }

}