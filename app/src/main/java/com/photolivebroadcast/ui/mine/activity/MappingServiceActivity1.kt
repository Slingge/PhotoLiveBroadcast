package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.dialog.PayMapingDialog
import com.photolivebroadcast.ui.mine.adapter.MappingServiceAdapter
import com.photolivebroadcast.ui.mine.callBack.PayMapingCallBack
import kotlinx.android.synthetic.main.activity_mapping_service1.*

/**
 * 修图服务
 * Created by Slingge on 2018/9/8.
 */
class MappingServiceActivity1 : BaseActivity() {

    private var mappingAdapter: MappingServiceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapping_service1)
        init()
    }


    private fun init() {
        inittitle("预约修图")
        StatusBarWhiteColor()


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_service.layoutManager = linearLayoutManager

        mappingAdapter = MappingServiceAdapter(this)
        rv_service.adapter = mappingAdapter

        tv_next.setOnClickListener { v ->
            PayMapingDialog.showDialog(this, object : PayMapingCallBack {
                override fun payMapping() {
                    MyApplication.openActivity(this@MappingServiceActivity1, MappingServiceActivity2::class.java)
                }
            })
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        PayMapingDialog.dissDialog()
    }

}