package com.photolivebroadcast.ui.mine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.photolivebroadcast.R

/**
 * 我的
 * Created by Slingge on 2018/8/31.
 */
class MineFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mine, container, false)
        return view
    }

    override fun loadData() {

    }

}