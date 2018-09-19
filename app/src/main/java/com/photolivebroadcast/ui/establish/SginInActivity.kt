package com.photolivebroadcast.ui.establish

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.NotificationCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.establish.result.SendMSMrHttp
import com.photolivebroadcast.util.AbStrUtil
import com.photolivebroadcast.util.GetCodeUtil
import com.photolivebroadcast.util.ImageFileUtil
import com.photolivebroadcast.util.TimerUtil
import kotlinx.android.synthetic.main.activity_enterprise_authentication1.*
import kotlinx.android.synthetic.main.activity_sginin.*

/**
 * Created by Slingge on 2018/9/2.
 */
class SginInActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sginin)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        tv_verification.setOnClickListener(this)
        tv_forgetPass.setOnClickListener(this)

        et_phone.addTextChangedListener(object : TextWatcher {
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


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_verification -> {
                val phone = AbStrUtil.etTostr(et_phone)
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast("请输入手机号")
                    return
                }

                ProgressDialog.showDialog(this)
                val VCode = GetCodeUtil.getCode()
                abLog.e("验证码",VCode)
                SendMSMrHttp.regist(phone, VCode, object : SendMSMrHttp.SendMsmCallBack {
                    override fun send() {
                        val bundle = Bundle()
                        bundle.putString("code", VCode)
                        bundle.putString("phone", phone)
                        MyApplication.openActivity(this@SginInActivity, VerificationActivity::class.java, bundle)
                    }
                })
            }
            R.id.tv_forgetPass -> {
                MyApplication.openActivity(this, ForgetPassActivity::class.java)
            }
        }
    }

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    tv_verification.setBackgroundResource(R.drawable.bg_thems5)
                }
                1 -> {
                    tv_verification.setBackgroundResource(R.drawable.bg_them5)
                }
            }
        }
    }

}