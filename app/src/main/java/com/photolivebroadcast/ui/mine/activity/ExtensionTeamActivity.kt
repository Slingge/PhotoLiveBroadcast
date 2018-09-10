package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.mine.adapter.ExtensionTeamAdapter
import kotlinx.android.synthetic.main.activity_extension_team.*

/**
 * Created by Slingge on 2018/9/9.
 */
class ExtensionTeamActivity:BaseActivity(){

private var teamAdaptation: ExtensionTeamAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extension_team)
        init()
    }


    private fun init() {
        inittitle("推广团队")
        StatusBarWhiteColor()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_team.layoutManager = linearLayoutManager

        teamAdaptation=ExtensionTeamAdapter(this)
        rv_team.adapter=teamAdaptation
    }



}