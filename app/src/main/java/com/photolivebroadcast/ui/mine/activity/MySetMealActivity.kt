package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R

/**
 * 我的套餐
 * Created by Slingge on 2018/9/8.
 */
class MySetMealActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setmeal_my)
        init()
    }


    private fun init() {
        inittitle("")
        StatusBarWhiteColor()

    }


}