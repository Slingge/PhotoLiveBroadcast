package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.constant.LoginConstant
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.establish.SginInActivity
import com.photolivebroadcast.util.*
import kotlinx.android.synthetic.main.activity_setup.*

/**
 * Created by Slingge on 2018/9/15.
 */
class SetUpActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
        init()
    }


    private fun init() {
        inittitle("设置")
        StatusBarWhiteColor()
        tv_anout.setOnClickListener(this)
        tv_updata.setOnClickListener(this)
        tv_sginout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_updata -> {
                MyApplication.openActivity(this, UpdataActivity::class.java)
            }
            R.id.tv_anout -> {
                MyApplication.openActivity(this, AboutActivity::class.java)
            }
            R.id.tv_sginout -> {
                SharedPreferencesUtil.putSharePre(this, "uid", "")
                StatickUtil.uid = ""
                AppManager.finishAllActivity()
                MyApplication.openActivity(this, SginInActivity::class.java)
            }
        }
    }

}