package com.photolivebroadcast.ui.photoLive.activity

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.photoLive.model.CameramanCodeBean
import com.zhy.http.okhttp.OkHttpUtils

/**
 * 管理摄影师
 * Created by zhf on 2018/9/17.
 */

class AdminCameramanActivity : BaseActivity(), View.OnClickListener {
    private var tvUpdateCode: TextView? = null
    private var tvCopy: TextView? = null
    private var tvCode: TextView? = null
    private var btnSubmit: Button? = null

    private var pId = ""

    private var bean: CameramanCodeBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_cameraman)

        pId = intent.getStringExtra("id")

        StatusBarWhiteColor()
        inittitle("管理摄影师")
        initView()
        initData()
        initData2()
        initEvent()
    }

    private fun initView() {
        tvUpdateCode = findViewById<View>(R.id.tv_update_code) as TextView
        tvCopy = findViewById<View>(R.id.tv_copy) as TextView
        tvCode = findViewById<View>(R.id.tv_code) as TextView
        btnSubmit = findViewById<View>(R.id.btn_submit) as Button
    }

    private fun initData() {
        ProgressDialog.showDialog(this)
        OkHttpUtils
                .post()
                .url("http://112.74.169.87/videoCloud/photolive/ajaxgetalbumincode")
                .addParams("pid", pId)
                .build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val gson = Gson()
                bean = gson.fromJson(response, CameramanCodeBean::class.java)
                if (bean!!.code == 200) {
                    tvCode!!.text = "" + bean!!.data.incode
                } else {
                    ToastUtil.showToast(bean!!.msg)
                }
            }
        })
    }

    private fun initData2() {
        ProgressDialog.showDialog(this)
        OkHttpUtils
                .post()
                .url("http://112.74.169.87/videoCloud/photolive/ajaxgetallincodeuser")
                .addParams("pid", pId)
                .build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
//                val gson = Gson()
//                bean = gson.fromJson(response, CameramanCodeBean::class.java)
//                if (bean!!.code == 200) {
//                    tvCode!!.text = "" + bean!!.data.incode
//                } else {
//                    ToastUtil.showToast(bean!!.msg)
//                }
            }
        })
    }

    private fun initEvent() {
        tvCopy!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_copy -> {
                if (bean == null) {
                    Toast.makeText(this, "数据获取失败", Toast.LENGTH_SHORT).show()
                    initData()
                    return
                }
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                // 将文本内容放到系统剪贴板里。
                cm.text = "" + bean!!.data.incode
                Toast.makeText(this, "复制成功", Toast.LENGTH_LONG).show()
            }
            R.id.btn_submit -> {

            }
        }
    }
}
