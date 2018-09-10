package com.photolivebroadcast.ui.mine.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.PayMapingDialog
import com.photolivebroadcast.ui.dialog.PayTypeDialog
import com.photolivebroadcast.util.AbStrUtil
import kotlinx.android.synthetic.main.activity_recharge.*

/**
 * 充值
 * Created by Slingge on 2018/9/8.
 */
class RechargeActivity : BaseActivity(), TextWatcher {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge)
        init()
    }


    private fun init() {
        inittitle("充值")
        StatusBarWhiteColor()

        et_money.addTextChangedListener(this)

        tv_next.setOnClickListener { v ->
            if (!TextUtils.isEmpty(AbStrUtil.etTostr(et_money))) {
                PayTypeDialog.showDialog(this)
            }
        }

    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {

        val str = s.toString()

        if (TextUtils.isEmpty(s)) {
            return
        }
        if(str.toDouble()>0){
            tv_next.setBackgroundResource(R.drawable.bg_them5)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        PayTypeDialog.dissDialog()
    }



}