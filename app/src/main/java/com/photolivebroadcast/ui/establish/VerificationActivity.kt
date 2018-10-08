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
import com.photolivebroadcast.constant.LoginConstant
import com.photolivebroadcast.ui.MainActivity
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.establish.result.SendMSMrHttp
import com.photolivebroadcast.ui.establish.result.SginHttp
import com.photolivebroadcast.util.*
import kotlinx.android.synthetic.main.activity_verification.*

/**
 * Created by Slingge on 2018/9/2.
 */
class VerificationActivity : BaseActivity() {

    private var Vcode = ""
    private var phone = ""

    private var timerUtil: TimerUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()

        timerUtil = TimerUtil(tv_verification)
        timerUtil!!.timersStart()

        Vcode = intent.getStringExtra("code")
        phone = intent.getStringExtra("phone")
        tv_phone.text="验证码已通过短信发送至"+phone
        tv_verification.setOnClickListener { v ->
            timerUtil!!.timersStart()
            SendMSMrHttp.regist(phone, "", object : SendMSMrHttp.SendMsmCallBack {
                override fun send(code: String) {
                    Vcode = code
                }
            })
        }

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

            ProgressDialog.showDialog(this)
            SginHttp.sgin(this, phone, code, object : SginHttp.SginCallBack {
                override fun send() {
                    AppManager.finishAllActivity()
                    MyApplication.openActivity(this@VerificationActivity, MainActivity::class.java)
                    SharePreferencesTools.saveObjectToSharePreferences(this@VerificationActivity,LoginConstant.LOGIN_TAG_FILE_NAME,LoginConstant.LOGIN_TAG_FILE_KEY,SmartEncryTools.encodeMD5String(phone+Vcode+System.currentTimeMillis()))
                }
            })
        }

        et_verification.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val str = s.toString()
                        if (TextUtils.isEmpty(str)) {
                            return
                        }
                        val m = Message()
                        if (str.length == 4) {
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
}