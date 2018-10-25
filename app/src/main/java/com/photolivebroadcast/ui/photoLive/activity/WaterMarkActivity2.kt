package com.photolivebroadcast.ui.photoLive.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.photoLive.http.UpAlbumPhotoHttp
import com.photolivebroadcast.util.AppManager
import com.photolivebroadcast.util.StatickUtil.headerUrl
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_personal_info.*
import kotlinx.android.synthetic.main.include_basetop.*
import java.io.File

/**
 * Created by zhf on 2018/9/17.
 */

class WaterMarkActivity2 : BaseActivity() {

    private var path = ""//地址
    private var content = ""//内容
    private var pId = ""//id
    private var iv_img: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_mark2)
        path = intent!!.getStringExtra("image")
        content = intent!!.getStringExtra("content")
        pId = intent!!.getStringExtra("id")
        init()
    }

    private fun init() {
        inittitle("水印设置")
        StatusBarWhiteColor()

        tv_right.visibility = View.VISIBLE
        tv_right.text = "下一步"
        tv_right.setOnClickListener { v ->
            ProgressDialog.showDialog(this@WaterMarkActivity2)
            UpAlbumPhotoHttp.upWrite(pId, content, object : UpAlbumPhotoHttp.UpResultCallBack {
                override fun result(url: String) {
                    if (url != null && url.equals("200")) {
                        ToastUtil.showToast("设置成功")
                        AppManager.finishActivity(WaterMarkActivity::class.java)
                        finish()
                    }else{
                        ToastUtil.showToast("设置失败")
                    }
                }
            })
        }

        iv_img = findViewById(R.id.iv_img)
        Picasso.get().load(File(path)).into(iv_img)
    }

}
