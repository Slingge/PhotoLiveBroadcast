package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.mine.adapter.FragmentPagerAdapter
import com.photolivebroadcast.ui.mine.fragment.ExtensionOrderFragment
import kotlinx.android.synthetic.main.activity_extension_order.*
import kotlinx.android.synthetic.main.include_tablayout.*
import java.util.ArrayList

/**
 * Created by Slingge on 2018/9/9.
 */
class ExtensionOrderActivity:BaseActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extension_order)
        init()
    }


    private fun init() {
        inittitle("推广订单")
        StatusBarWhiteColor()


        val tabList = ArrayList<String>()
        tabList.add("全部推广")
        tabList.add("我的推广")
        tabList.add("二级推广")

        val list = ArrayList<Fragment>()
        for (i in 0..2) {
            val fragment = ExtensionOrderFragment()
            val bundle = Bundle()
            bundle.putInt("flag", i)
            fragment.arguments = bundle
            list.add(fragment)
        }

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tab.setupWithViewPager(viewPager)


    }


}