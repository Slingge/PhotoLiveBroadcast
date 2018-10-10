package com.photolivebroadcast.ui.photoLive.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.mine.model.MySendModel
import com.photolivebroadcast.ui.photoLive.model.ColumnListBean
import com.photolivebroadcast.view.MyRecyclerView
import com.umeng.socialize.utils.DeviceConfig.context
import com.zhy.http.okhttp.OkHttpUtils

/**
 * 分栏条
 * Created by Slingge on 2018/9/29
 */
class ColumnActivity : BaseActivity() {

    var pid: String? = null

    var tvAdd: TextView? = null

    var rvColumn: MyRecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_column)

        pid = intent!!.getStringExtra("id")

        init()
        initData()
    }

    private fun init() {
        StatusBarWhiteColor()
        inittitle("分栏条")
        tvAdd = findViewById(R.id.tv_addfenlei)
        rvColumn = findViewById(R.id.rv_column)
        var linearmanager: LinearLayoutManager? = LinearLayoutManager(this)
        rvColumn!!.layoutManager = linearmanager

        tvAdd!!.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("id", pid)
            MyApplication.openActivity(this, AddColumnActivity::class.java, bundle)
        })
    }

    private fun initData() {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxgetallfenlan")
                .addParams("pid", pid)
                .build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, ColumnListBean::class.java)
                if (model.code == 200) {

                } else {
                    ToastUtil.showToast(model.msg)
                }
            }
        })
    }

}