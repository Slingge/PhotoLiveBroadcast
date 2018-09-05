package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R

/**
 * 审核提交
 * Created by Slingge on 2018/9/5.
 */
class CertificationAuditActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification_audit)
        init()
    }


    private fun init() {
        inittitle("实名认证")
        StatusBarWhiteColor()

    }

}