package com.photolivebroadcast.ui.establish

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MainActivity
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.establish.result.SginHttp
import com.photolivebroadcast.util.AbStrUtil
import kotlinx.android.synthetic.main.activity_sginin.*
import kotlinx.android.synthetic.main.activity_verification.*

/**
 * Created by Slingge on 2018/9/2.
 */
class VerificationActivity : BaseActivity() {

    private var Vcode = ""
    private var phone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()

        Vcode = intent.getStringExtra("code")
        phone = intent.getStringExtra("phone")

        tv_sgin.setOnClickListener { v ->
            val code = AbStrUtil.etTostr(et_verification)
            if (TextUtils.isEmpty(code)) {
                ToastUtil.showToast("请输验证码")
                return@setOnClickListener
            }
            if (Vcode != code) {
                ToastUtil.showToast("验证码错误")
                return@setOnClickListener
            }
            SginHttp.sgin(phone, code, object : SginHttp.SginCallBack {
                override fun send() {
                    MyApplication.openActivity(this@VerificationActivity, MainActivity::class.java)
                }
            })

        }

        et_phone.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val str = s.toString()
                        val m = Message()
                        if (str.length == 11) {
                            m.what = 1
                        } else {
                            m.what = 0
                        }
                        handler.sendMessage(m)
                    }
                })
    }


    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    tv_sgin.setBackgroundResource(R.drawable.bg_thems5)
                }
                1 -> {
                    tv_sgin.setBackgroundResource(R.drawable.bg_them5)
                }
            }
        }


    }