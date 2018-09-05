package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import kotlinx.android.synthetic.main.activity_personal_authentication1.*

/**
 *个人认证1
 * Created by Slingge on 2018/9/5.
 */
class PersonalAuthenticationActivity1 : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_authentication1)
        init()
    }


    private fun init() {
        inittitle("实名认证")
        StatusBarWhiteColor()

        tv_next.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_next -> {
                MyApplication.openActivity(this, PersonalAuthenticationActivity2::class.java)
            }
        }
    }


}