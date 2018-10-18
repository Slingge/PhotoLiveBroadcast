package com.photolivebroadcast.ui.photoLive.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.nostra13.universalimageloader.core.ImageLoader
import com.photolivebroadcast.R
import com.photolivebroadcast.util.StatickUtil.headerUrl
import kotlinx.android.synthetic.main.activity_personal_info.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by zhf on 2018/9/17.
 */

class WaterMarkActivity2 : BaseActivity() {

    private var path = ""//地址
    private var iv_img: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_mark2)
        path = intent!!.getStringExtra("image")
        init()
    }

    private fun init() {
        inittitle("水印设置")
        StatusBarWhiteColor()

        tv_right.visibility = View.VISIBLE
        tv_right.text = "下一步"
        tv_right.setOnClickListener { v ->

        }

        iv_img = findViewById(R.id.iv_img)
        ImageLoader.getInstance().displayImage(path, iv_img)
    }

}
