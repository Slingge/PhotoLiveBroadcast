package com.photolivebroadcast.ui.establish

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.establish.result.SendMSMrHttp
import com.photolivebroadcast.util.AbStrUtil
import com.photolivebroadcast.util.GetCodeUtil
import kotlinx.android.synthetic.main.activity_forgetpass.*

/**
 * 忘记密码
 * Created by Slingge on 2018/9/2.
 */
class ForgetPassActivity : BaseActivity() ,View.OnClickListener{

    private var phone=""
    private var VCode=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgetpass)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        tv_verification.setOnClickListener(this)
        tv_enter.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_verification->{
                  phone = AbStrUtil.etTostr(et_phone)
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast("请输入手机号")
                    return
                }

                ProgressDialog.showDialog(this)
                  VCode = GetCodeUtil.getCode()
                abLog.e("验证码",VCode)
                SendMSMrHttp.regist(phone, VCode, object : SendMSMrHttp.SendMsmCallBack {
                    override fun send() {
                        val bundle = Bundle()
                        bundle.putString("code", VCode)
                        bundle.putString("phone", phone)
                        MyApplication.openActivity(this@ForgetPassActivity, VerificationActivity::class.java, bundle)
                    }
                })
            }
            R.id.tv_enter->{
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast("请输入手机号")
                    return
                }
                val code=AbStrUtil.etTostr(et_verification)
                if(TextUtils.isEmpty(code)){
                    ToastUtil.showToast("请输入验证码")
                    return
                }
                if(code!=VCode){
                    ToastUtil.showToast("验证码错误")
                    return
                }
                val pass1=AbStrUtil.etTostr(et_pass1)
                if(TextUtils.isEmpty(pass1)){
                    ToastUtil.showToast("请输入密码")
                    return
                }
                val pass2=AbStrUtil.etTostr(et_pass2)
                if(TextUtils.isEmpty(pass2)){
                    ToastUtil.showToast("请输确认密码")
                    return
                }
                if(pass1!=pass2){
                    ToastUtil.showToast("密码不一致")
                    return
                }

            }
        }
    }



}