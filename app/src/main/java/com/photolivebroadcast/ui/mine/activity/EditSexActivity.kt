package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by Slingge on 2018/9/4.
 */
class EditSexActivity  : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editsex)
        init()
    }


    private fun init() {
        inittitle("编辑性别")
        StatusBarWhiteColor()

        tv_right.text="完成"
        tv_right.visibility= View.VISIBLE
        tv_right.setOnClickListener { v-> }
    }


}