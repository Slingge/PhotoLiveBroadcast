package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R

/**
 * Created by Slingge on 2018/9/15.
 */
class UpdataActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updata)
    }


    private fun init() {
        inittitle("检查版本")
        StatusBarWhiteColor()

    }

}