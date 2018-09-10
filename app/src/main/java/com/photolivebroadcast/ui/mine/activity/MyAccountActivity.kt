package com.photolivebroadcast.ui.mine.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.util.StatusBarBlackWordUtil
import com.photolivebroadcast.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_my_account.*
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 我的账户
 * Created by Slingge on 2018/9/8.
 */
class MyAccountActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
        init()
    }


    private fun init() {
        inittitle("账户余额")
        StatusBarWhiteColor()

        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorTheme))
        }

        tv_right.visibility = View.VISIBLE
        tv_right.text = "明细"
        tv_right.setTextColor(resources.getColor(R.color.white))
        tv_right.setOnClickListener(this)

        include.setBackgroundColor(resources.getColor(R.color.colorTheme))
        tv_title.setTextColor(resources.getColor(R.color.white))
        iv_back.setImageResource(R.drawable.ic_back_w)


        tv_recharge.setOnClickListener(this)
        tv_forward.setOnClickListener(this)
        tv_right.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_recharge -> {
                MyApplication.openActivity(this, RechargeActivity::class.java)
            }

            R.id.tv_forward -> {
                MyApplication.openActivity(this, ForwardActivity::class.java)
            }
            R.id.tv_right -> {
                MyApplication.openActivity(this, AccountDetailedActivity::class.java)
            }
        }
    }


}