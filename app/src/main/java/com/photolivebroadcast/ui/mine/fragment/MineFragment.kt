package com.photolivebroadcast.ui.mine.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.mine.activity.PersonalInfoActivity
import com.photolivebroadcast.util.StatusBarBlackWordUtil
import com.photolivebroadcast.util.StatusBarUtil
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 我的
 * Created by Slingge on 2018/8/31.
 */
class MineFragment : BaseFragment(), View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mine, container, false)
        init()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (Build.VERSION.SDK_INT > 19) {
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setStutaViewHeight(activity, view_staus)
            StatusBarUtil.transparentStatusBar(activity)
            StatusBarBlackWordUtil.StatusBarLightMode(activity)
        }

        iv_right.setOnClickListener(this)

    }

    private fun init() {
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_right -> {//个人信息
                MyApplication.openActivity(activity!!, PersonalInfoActivity::class.java)
            }
        }
    }


    override fun loadData() {

    }

}