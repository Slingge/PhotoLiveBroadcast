package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import kotlinx.android.synthetic.main.activity_realname_authentication.*

/**
 * 实名认证
 * Created by Slingge on 2018/9/5.
 */
class RealnameAuthenticationActivity : BaseActivity(), View.OnClickListener {

    private var flag = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realname_authentication)
        init()
    }


    private fun init() {
        inittitle("实名认证")
        StatusBarWhiteColor()

        image1.setOnClickListener(this)
        image2.setOnClickListener(this)
        tv_next.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.image1 -> {//个人
                flag = 0
                tv_next.setBackgroundResource(R.drawable.bg_them5)
                image1.setImageResource(R.drawable.ic_geren_hl)
                image2.setImageResource(R.drawable.ic_qiye_nor)
            }
            R.id.image2 -> {
                flag = 1
                tv_next.setBackgroundResource(R.drawable.bg_them5)
                image1.setImageResource(R.drawable.ic_geren_nor)
                image2.setImageResource(R.drawable.ic_qiye_hl)
            }
            R.id.tv_next -> {
                if (flag == 0) {
                    MyApplication.openActivity(this, PersonalAuthenticationActivity1::class.java)
                } else {
                    MyApplication.openActivity(this, EnterpriseAuthenticationActivity1::class.java)
                }
            }
        }
    }


    fun destroy() {
        finish()
    }


}