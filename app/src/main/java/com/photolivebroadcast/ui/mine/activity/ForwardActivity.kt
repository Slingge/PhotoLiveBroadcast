package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import kotlinx.android.synthetic.main.activity_forward.*

/**
 * 提现
 * Created by Slingge on 2018/9/8.
 */
class ForwardActivity : BaseActivity(), TextWatcher {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forward)
        init()
    }


    private fun init() {
        inittitle("提现")
        StatusBarWhiteColor()

        et_money.addTextChangedListener(this)


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
        if (str.toDouble() > 0) {
            tv_pay.setBackgroundResource(R.drawable.bg_them5)
        }
    }


}