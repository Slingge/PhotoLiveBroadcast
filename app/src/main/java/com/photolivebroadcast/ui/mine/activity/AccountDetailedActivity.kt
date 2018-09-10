package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.mine.adapter.AccountDetailedAdapter
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 账户明细
 * Created by Slingge on 2018/9/8.
 */
class AccountDetailedActivity : BaseActivity() {

    private var detailsAdapter: AccountDetailedAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }


    private fun init() {
        inittitle("明细")
        StatusBarWhiteColor()

        val linearLayoutManager=LinearLayoutManager(this)
        linearLayoutManager.orientation=LinearLayoutManager.VERTICAL

        xrecyclerview.layoutManager=linearLayoutManager

        detailsAdapter=AccountDetailedAdapter(this)
        xrecyclerview.adapter=detailsAdapter

    }

}