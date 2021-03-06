package com.photolivebroadcast.ui.photoLive.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.model.MySendModel
import com.photolivebroadcast.ui.photoLive.http.AlbumInfoHttp
import com.umeng.socialize.utils.DeviceConfig.context
import com.zhy.http.okhttp.OkHttpUtils

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 编辑相册
 * Created by zhf on 2018/9/11.
 */

class EditorPhotoActivity : BaseActivity(), View.OnClickListener {

    private var model: MySendModel.listalbumsModel? = null
    private var pid = ""

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
    private var ivBegin: ImageView? = null

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
        ivBegin = findViewById<ImageView>(R.id.iv_begin) as ImageView
        pid = intent.getStringExtra("id")
    }

    override fun onStart() {
        super.onStart()
        ProgressDialog.showDialog(this)
        AlbumInfoHttp.albumInfo(pid)
    }

    @Subscribe
    fun onEvent(model: MySendModel.listalbumsModel) {
        this.model = model
        if (model.isopen != null && model.isopen.equals("Y")) {
            ivBegin!!.setImageResource(R.mipmap.icon_fun_pen)
        } else {
            ivBegin!!.setImageResource(R.mipmap.icon_fun_close)
        }
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
        ivBegin!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
        //基本信息
            R.id.tv_information -> {
                val bundle = Bundle()
                bundle.putString("id", pid)
                MyApplication.openActivity(this, AlbumJiBenInfoActivity::class.java, bundle)
            }
        //拍摄活动信息
            R.id.tv_shot -> {
                val bundle = Bundle()
                bundle.putString("id", pid)
                MyApplication.openActivity(this, ShotImageActivity::class.java, bundle)
            }
        //相册分类
            R.id.tv_photo_type -> {
                val bundle = Bundle()
                bundle.putString("id", pid)
                MyApplication.openActivity(this, ClassifySettingActivity::class.java, bundle)
            }
        //下方自定义广告
            R.id.tv_advertise -> {
                val bundle = Bundle()
                bundle.putString("id", pid)
                MyApplication.openActivity(this, CustomAdverActivity::class.java, bundle)
            }

        //水印功能
            R.id.tv_watermark -> {
                val bundle = Bundle()
                bundle.putString("id", pid)
                MyApplication.openActivity(this, WaterMarkActivity::class.java, bundle)
            }
        //互动设置
            R.id.tv_interact_setting -> {
            }
        //管理摄影师
            R.id.tv_admin_cameraman -> {
                val bundle = Bundle()
                bundle.putString("id", pid)
                MyApplication.openActivity(this, AdminCameramanActivity::class.java, bundle)
            }
        //管理审核员
            R.id.tv_admin_examine -> {
                val bundle = Bundle()
                bundle.putString("id", pid)
                MyApplication.openActivity(this, AdminExamineActivity::class.java, bundle)
            }
        //管理修图师
            R.id.tv_admin_trim -> {
                val bundle = Bundle()
                bundle.putString("id", pid)
                MyApplication.openActivity(this, AdminTrimActivity::class.java, bundle)
            }
        //观看设置
            R.id.tv_watch_setting -> {
                val bundle = Bundle()
                bundle.putString("id", pid)
                MyApplication.openActivity(this, WatchSettingActivity::class.java, bundle)
            }
        //开播按钮
            R.id.iv_begin -> {
                if (model == null) {
                    ToastUtil.showToast("数据获取失败")
                    return
                }
                if(model!!.isopen!=null&&model!!.isopen.equals("Y")){
                    open("N")
                }else{
                    open("Y")
                }
            }
        }
    }

    /**
     * 开播
     */
    private fun open(open:String){
        ProgressDialog.showDialog(this)
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxeditphotoopen")
                .addParams("pid", pid)
                .addParams("isopen", open).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model2 = Gson().fromJson(response, MySendModel::class.java)
                if (model2.code == 200) {
                    if(open.equals("Y")){
                        ivBegin!!.setImageResource(R.mipmap.icon_fun_pen)
                        model!!.isopen="Y"
                    }else{
                        ivBegin!!.setImageResource(R.mipmap.icon_fun_close)
                        model!!.isopen="N"
                    }
                } else {
                    ToastUtil.showToast(model2.msg)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
