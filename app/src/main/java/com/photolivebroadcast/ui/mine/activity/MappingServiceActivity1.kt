package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.dialog.PayMapingDialog
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.adapter.MappingServiceAdapter
import com.photolivebroadcast.ui.mine.callBack.PayMapingCallBack
import com.photolivebroadcast.ui.mine.model.MyMappingServiceModel
import com.photolivebroadcast.ui.mine.result.MappingServiceHttp
import kotlinx.android.synthetic.main.activity_mapping_service1.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 修图服务
 * Created by Slingge on 2018/9/8.
 */
class MappingServiceActivity1 : BaseActivity() {

    private var mappingAdapter: MappingServiceAdapter? = null
    private var listxiutus = ArrayList<MyMappingServiceModel.listxiutusModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapping_service1)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("预约修图")
        StatusBarWhiteColor()


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_service.layoutManager = linearLayoutManager

        mappingAdapter = MappingServiceAdapter(this, listxiutus)
        rv_service.adapter = mappingAdapter

        tv_next.setOnClickListener { v ->
            MyApplication.openActivity(this@MappingServiceActivity1, MappingServiceActivity2::class.java)
        }

        ProgressDialog.showDialog(this)
        MappingServiceHttp.MyMappingService()
    }


    @Subscribe
    fun onEvent(listxiutus: ArrayList<MyMappingServiceModel.listxiutusModel>) {
        if (listxiutus.isNotEmpty()) {
            listxiutus.clear()
            mappingAdapter!!.notifyDataSetChanged()
        }
        this.listxiutus.addAll(listxiutus)
        mappingAdapter!!.notifyDataSetChanged()
        if (this.listxiutus.isEmpty()) {
            rv_service.visibility = View.GONE
            imageView2.visibility = View.VISIBLE

            PayMapingDialog.showDialog(this, object : PayMapingCallBack {
                override fun payMapping() {
                    MyApplication.openActivity(this@MappingServiceActivity1, SetMealAllActivity::class.java)
                }
            })
        } else {
            imageView2.visibility = View.GONE
            rv_service.visibility = View.VISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        PayMapingDialog.dissDialog()
        EventBus.getDefault().unregister(this)
    }

}