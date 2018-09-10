package com.photolivebroadcast.ui.mine.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_extension.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 立即推广
 * Created by Slingge on 2018/9/9.
 */
class ExtensionImmediatelyActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_immediately_extension)
        init()
    }


    private fun init() {
        inittitle("立即推广")
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


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_right -> {

            }
        }
    }


}