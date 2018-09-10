package com.photolivebroadcast.ui.mine.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.mine.adapter.ExtensionOrderAdapter
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * Created by Slingge on 2018/9/9.
 */
class ExtensionOrderFragment : BaseFragment() {

    private var flag = -1//0全部推广，1我的推广，2二级推广

    private var orderAdapter: ExtensionOrderAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.xrecyclerview, container, false)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        include.visibility = View.GONE

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecyclerview.layoutManager = linearLayoutManager

        orderAdapter = ExtensionOrderAdapter(activity!!)
        xrecyclerview.adapter = orderAdapter
    }

    private fun init() {


    }


    override fun loadData() {

    }


}