package com.photolivebroadcast.ui.photoLive.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.InvitationDialog
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.dialog.WatchSettingDialog
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

class WatchSettingActivity : BaseActivity(), View.OnClickListener {


    private var pid = ""
    private var rbPublic: RadioButton? = null
    private var rbMoney: RadioButton? = null
    private var rbPwd: RadioButton? = null

    private var type = "0"//0公开免费，1收费，2密码观看
    private var lookprice = ""//观看价格
    private var lookcode = ""//观看密码


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
            if (type == "0") {
                SeeTypeHttp.seeTypeUpdate(this, pid, "lookremark", "", "")
            } else if (type == "1") {
                if (TextUtils.isEmpty(lookprice)) {
                    ToastUtil.showToast("请输入观看价格")
                    return@setOnClickListener
                }
                SeeTypeHttp.seeTypeUpdate(this, pid, "lookremark", lookprice, "")
            } else if (type == "2") {
                if (TextUtils.isEmpty(lookcode)) {
                    ToastUtil.showToast("请输入观看密码")
                    return@setOnClickListener
                }
                SeeTypeHttp.seeTypeUpdate(this, pid, "lookremark", "", lookcode)
            }
        }
        rbPublic = findViewById(R.id.rb_public)
        rbMoney = findViewById(R.id.rb_pay)
        rbPwd = findViewById(R.id.rb_pwd)

        rbPwd!!.setOnClickListener(this)
        rbMoney!!.setOnClickListener(this)
        rbPublic!!.setOnClickListener(this)

        pid = intent.getStringExtra("id")
        ProgressDialog.showDialog(this)
        SeeTypeHttp.seeType(pid, object : SeeTypeHttp.SeeTypeCallBack {
            override fun seeType(type: String) {
                val obj = JSONObject(type)
                if (obj.getString("lookremark") == "公开免费") {
                    rb_public.isChecked = true
                    this@WatchSettingActivity.type = "0"
                } else if (obj.getString("lookremark") == "密码观看") {
                    rb_pwd.isChecked = true
                    this@WatchSettingActivity.type = "1"
                } else {
                    rb_pay.isChecked = true
                    this@WatchSettingActivity.type = "2"
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rb_public -> {
                type = "0"
            }
            R.id.rb_pay -> {
                type = "1"
                WatchSettingDialog.dialogEducation(this, type, pid, object : WatchSettingDialog.WatchCallBack {
                    override fun watch(code: String) {
                        lookprice = code
                    }
                })
            }
            R.id.rb_pwd -> {
                type = "2"
                WatchSettingDialog.dialogEducation(this, type, pid, object : WatchSettingDialog.WatchCallBack {
                    override fun watch(code: String) {
                        lookcode = code
                    }
                })
            }
        }
    }


}
