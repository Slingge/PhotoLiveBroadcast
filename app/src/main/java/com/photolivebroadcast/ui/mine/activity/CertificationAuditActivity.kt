package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.util.AppManager
import kotlinx.android.synthetic.main.activity_certification_audit.*

/**
 * 审核提交
 * Created by Slingge on 2018/9/5.
 */
class CertificationAuditActivity : BaseActivity() {

    private var type = -1//0个人，1企业

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification_audit)
        init()
    }


    private fun init() {
        inittitle("实名认证")
        StatusBarWhiteColor()

        type = intent.getIntExtra("type", -1)
        tv_next.setOnClickListener { v ->
            if (type == 0) {
                AppManager.finishActivity(PersonalAuthenticationActivity1::class.java)
                AppManager.finishActivity(PersonalAuthenticationActivity2::class.java)
            } else {
                AppManager.finishActivity(EnterpriseAuthenticationActivity1::class.java)
                AppManager.finishActivity(EnterpriseAuthenticationActivity2::class.java)
            }
            AppManager.finishActivity(RealnameAuthenticationActivity::class.java)
            finish()
        }
    }

}