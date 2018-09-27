package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.adapter.MySendAdapter
import com.photolivebroadcast.ui.mine.model.MySendModel
import com.photolivebroadcast.ui.mine.result.MySendHttp
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 我发出的
 * Created by Slingge on 2018/9/8.
 */
class MySendActivity : BaseActivity() {

    private var sendAdapter: MySendAdapter? = null
    private var list = ArrayList<MySendModel.listalbumsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("我发出的")
        StatusBarWhiteColor()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        xrecyclerview.layoutManager = linearLayoutManager

        sendAdapter = MySendAdapter(this, list)
        xrecyclerview.adapter = sendAdapter

        ProgressDialog.showDialog(this)
        MySendHttp.mySend()
    }


    @Subscribe
    fun onEvent(moddel: MySendModel.dataModel) {
        list.addAll(moddel.listalbums)
        sendAdapter!!.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}