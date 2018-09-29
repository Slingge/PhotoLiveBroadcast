package com.photolivebroadcast.ui.photoLive.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.photoLive.AlbumsClassificationModel
import com.photolivebroadcast.ui.photoLive.http.SeeTypeHttp
import kotlinx.android.synthetic.main.activity_watch_setting.*
import kotlinx.android.synthetic.main.include_basetop.*

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.json.JSONObject

/**
 * Created by zhf on 2018/9/17.
 */

class WatchSettingActivity : BaseActivity() {


    private var pid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_setting)
        inittitle("观看设置")
        StatusBarWhiteColor()
        initView()
    }

    private fun initView() {
        tv_right.visibility = View.VISIBLE
        tv_right.text = "提交"
        tv_right.setOnClickListener { v ->

        }
        pid = intent.getStringExtra("id")
        ProgressDialog.showDialog(this)
        SeeTypeHttp.seeType(pid, object : SeeTypeHttp.SeeTypeCallBack {
            override fun seeType(type: String) {
                val obj = JSONObject(type)
                if (obj.getString("lookremark") == "公开免费") {
                    rb_public.isChecked = true
                } else if (!TextUtils.isEmpty(obj.getString("password"))) {
                    rb_pwd.isChecked = true
                } else {
                    rb_pay.isChecked = true
                }
            }
        })
    }


}
