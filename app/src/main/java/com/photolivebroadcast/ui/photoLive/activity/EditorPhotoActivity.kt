package com.photolivebroadcast.ui.photoLive.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.model.MySendModel
import com.photolivebroadcast.ui.photoLive.adapter.ClassifySettingAdapter
import com.photolivebroadcast.ui.photoLive.http.AlbumInfoHttp

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 编辑相册
 * Created by zhf on 2018/9/11.
 */

class EditorPhotoActivity : BaseActivity(), View.OnClickListener {

    private var model: MySendModel.listalbumsModel? = null
    private var pid: String? = null

    private var tvInformation: TextView? = null
    private var tvShot: TextView? = null
    private var tvPhotoType: TextView? = null
    private var tvAdvertise: TextView? = null
    private var tvWatermark: TextView? = null
    private var tvInteractSetting: TextView? = null
    private var tvAdminCameraman: TextView? = null
    private var tvAdminExamine: TextView? = null
    private var tvAdminTrim: TextView? = null
    private var tvWatchSetting: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor_photo)
        EventBus.getDefault().register(this)
        StatusBarWhiteColor()
        inittitle("编辑相册")
        initView()
        initData()
        initEvent()
    }

    private fun initView() {
        tvInformation = findViewById<View>(R.id.tv_information) as TextView
        tvShot = findViewById<View>(R.id.tv_shot) as TextView
        tvPhotoType = findViewById<View>(R.id.tv_photo_type) as TextView
        tvAdvertise = findViewById<View>(R.id.tv_advertise) as TextView
        tvWatermark = findViewById<View>(R.id.tv_watermark) as TextView
        tvInteractSetting = findViewById<View>(R.id.tv_interact_setting) as TextView
        tvAdminCameraman = findViewById<View>(R.id.tv_admin_cameraman) as TextView
        tvAdminExamine = findViewById<View>(R.id.tv_admin_examine) as TextView
        tvAdminTrim = findViewById<View>(R.id.tv_admin_trim) as TextView
        tvWatchSetting = findViewById<View>(R.id.tv_watch_setting) as TextView
        pid = intent.getStringExtra("id")
    }

    override fun onStart() {
        super.onStart()
        ProgressDialog.showDialog(this)
        AlbumInfoHttp.albumInfo(pid!!)
    }

    @Subscribe
    fun onEvent(model: MySendModel.listalbumsModel) {
        this.model = model
    }

    private fun initData() {

    }

    private fun initEvent() {
        tvInformation!!.setOnClickListener(this)
        tvShot!!.setOnClickListener(this)
        tvPhotoType!!.setOnClickListener(this)
        tvAdvertise!!.setOnClickListener(this)
        tvWatermark!!.setOnClickListener(this)
        tvInteractSetting!!.setOnClickListener(this)
        tvAdminCameraman!!.setOnClickListener(this)
        tvAdminExamine!!.setOnClickListener(this)
        tvAdminTrim!!.setOnClickListener(this)
        tvWatchSetting!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            //基本信息
            R.id.tv_information -> {
                intent!!.putExtra("id", pid)
                intent = Intent(this, AlbumJiBenInfoActivity::class.java)
            }
            //拍摄活动信息
            R.id.tv_shot -> {
                intent!!.putExtra("id", pid)
                intent = Intent(this, ShotImageActivity::class.java)
            }
            //相册分类
            R.id.tv_photo_type -> {
                intent!!.putExtra("id", pid)
                intent = Intent(this, ClassifySettingActivity::class.java)
            }
            //下方自定义广告
            R.id.tv_advertise -> {
                intent!!.putExtra("id", pid)
                intent = Intent(this, CustomAdverActivity::class.java)
            }

            //水印功能
            R.id.tv_watermark -> {
                intent!!.putExtra("id", pid)
                intent = Intent(this, WaterMarkActivity::class.java)
            }
            //互动设置
            R.id.tv_interact_setting -> {
            }
            //管理摄影师
            R.id.tv_admin_cameraman -> intent = Intent(this, AdminCameramanActivity::class.java)
            //管理审核员
            R.id.tv_admin_examine -> {
                intent!!.putExtra("id", pid)
                intent = Intent(this, AdminExamineActivity::class.java)
            }
            //管理修图师
            R.id.tv_admin_trim -> {
                intent!!.putExtra("id", pid)
                intent = Intent(this, AdminTrimActivity::class.java)
            }
            //观看设置
            R.id.tv_watch_setting -> {
                intent!!.putExtra("id", pid)
                intent = Intent(this, WatchSettingActivity::class.java)
            }
        }
        if (intent != null) {
            startActivity(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
