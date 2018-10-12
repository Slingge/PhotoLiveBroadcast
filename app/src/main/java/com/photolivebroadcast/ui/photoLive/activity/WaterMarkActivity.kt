package com.photolivebroadcast.ui.photoLive.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.util.FileUtils
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by zhf on 2018/9/17.
 */

class WaterMarkActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_mark)
        init()
    }

    private fun init() {
        inittitle("水印设置")
        StatusBarWhiteColor()

        tv_right.visibility = View.VISIBLE
        tv_right.text = "预览"
        tv_right.setOnClickListener { v ->
            MyApplication.openActivity(this, WaterMarkActivity2::class.java)
        }
    }

    private var mContentRootView: RelativeLayout? = null

    private fun generateBitmap() {

        val bitmap = Bitmap.createBitmap(mContentRootView!!.getWidth(),
                mContentRootView!!.getHeight(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        mContentRootView!!.draw(canvas)

        val iamgePath = FileUtils.saveBitmapToLocal(bitmap, this)
//        val intent = Intent(this, DisplayActivity::class.java)
        intent.putExtra("image", iamgePath)
        startActivity(intent)
    }

}
