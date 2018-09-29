package com.photolivebroadcast.ui.photoLive.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.photoLive.AlbumsClassificationModel
import com.photolivebroadcast.ui.photoLive.adapter.ClassifySettingAdapter
import com.photolivebroadcast.ui.photoLive.http.AlbumsClassificationHttp
import kotlinx.android.synthetic.main.activity_classify_setting.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 相册分类
 * Created by zhf on 2018/9/18.
 */

class ClassifySettingActivity : BaseActivity(), View.OnClickListener {

    private var pid = ""//相册id

    private var adapter: ClassifySettingAdapter? = null
    private var menuList = ArrayList<AlbumsClassificationModel.dataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classify_setting)
        EventBus.getDefault().register(this)
        initView()
    }

    private fun initView() {
        inittitle("设置相册分类")
        StatusBarWhiteColor()
        btn_submit.setOnClickListener(this)

        pid = intent.getStringExtra("id")

        val linearLayoutManager = LinearLayoutManager(this)
        rv_classify!!.layoutManager = linearLayoutManager
        adapter = ClassifySettingAdapter(this, menuList)
        rv_classify!!.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        if (menuList.isNotEmpty()) {
            menuList.clear()
            adapter!!.notifyDataSetChanged()
        }
        ProgressDialog.showDialog(this)
        AlbumsClassificationHttp.Classification(pid)
    }

    @Subscribe
    fun onEvent(moddel: AlbumsClassificationModel) {
        menuList.addAll(moddel.data)
        adapter!!.notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_submit -> {
//                val intent = Intent(this, AddColumnActivity::class.java)
//                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}
