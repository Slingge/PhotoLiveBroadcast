package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import kotlinx.android.synthetic.main.activity_enterprise_authentication1.*

/**
 * 企业认证1
 * Created by Slingge on 2018/9/6.
 */
 class EnterpriseAuthenticationActivity1:BaseActivity(),View.OnClickListener{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enterprise_authentication1)
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
                val bundle=Bundle()
                bundle.putInt("type",1)
                MyApplication.openActivity(this, EnterpriseAuthenticationActivity2::class.java,bundle)
            }
        }
    }


}