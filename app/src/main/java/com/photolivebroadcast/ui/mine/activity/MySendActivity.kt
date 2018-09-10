package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.mine.adapter.AccountDetailedAdapter
import com.photolivebroadcast.ui.mine.adapter.MySendAdapter
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 我发出的
 * Created by Slingge on 2018/9/8.
 */
class MySendActivity : BaseActivity() {

    private var sendAdapter: MySendAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }


    private fun init() {
        inittitle("我发出的")
        StatusBarWhiteColor()

        val linearLayoutManager=LinearLayoutManager(this)
        linearLayoutManager.orientation=LinearLayoutManager.VERTICAL

        xrecyclerview.layoutManager=linearLayoutManager

        sendAdapter=MySendAdapter(this)
        xrecyclerview.adapter=sendAdapter

    }

}