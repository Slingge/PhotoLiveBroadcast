package com.photolivebroadcast.ui.mine.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_extension.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by Slingge on 2018/9/8.
 */
class ExtensionActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extension)
        init()
    }


    private fun init() {
        inittitle("推广中心")
        StatusBarWhiteColor()

        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorTheme))
        }

        iv_right.visibility = View.VISIBLE
        iv_right.setOnClickListener(this)
        iv_right.setImageResource(R.drawable.icon01)

        include.setBackgroundColor(resources.getColor(R.color.colorTheme))
        tv_title.setTextColor(resources.getColor(R.color.white))
        iv_back.setImageResource(R.drawable.ic_back_w)


        iv_order.setOnClickListener(this)
        tv_order.setOnClickListener(this)
        order.setOnClickListener(this)

        iv_extension.setOnClickListener(this)
        tv_extension.setOnClickListener(this)
        extension.setOnClickListener(this)

        iv_member.setOnClickListener(this)
        member.setOnClickListener(this)
        tv_member.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_extension, R.id.tv_extension, R.id.extension -> {
                MyApplication.openActivity(this, ExtensionImmediatelyActivity::class.java)
            }
            R.id.iv_order, R.id.tv_order, R.id.order -> {
                MyApplication.openActivity(this, ExtensionOrderActivity::class.java)
            }
            R.id.iv_member, R.id.member, R.id.tv_member -> {
                MyApplication.openActivity(this, ExtensionTeamActivity::class.java)
            }
        }
    }


}