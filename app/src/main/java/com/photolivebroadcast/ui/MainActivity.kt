package com.photolivebroadcast.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.NewAlbumDialog
import com.photolivebroadcast.ui.mine.fragment.MineFragment
import com.photolivebroadcast.ui.photoLive.fragment.LiveFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var mFragment = Fragment()
    private var bFragment1: LiveFragment? = null
    private var bFragment2: MineFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        tab_1.isChecked = true
        bFragment1 = LiveFragment()
        switchFragment(bFragment1!!)

        rl_bottom.setOnCheckedChangeListener { _, i -> selectStyle(i) }

        establish.setOnClickListener { v ->
            NewAlbumDialog.dialogEducation(this)
        }

    }


    //根据具体点击切换显示对应fragment
    private fun selectStyle(ID: Int) {
        when (ID) {
            R.id.tab_1 -> {
                bFragment1 = LiveFragment()
                switchFragment(bFragment1!!)
            }
            R.id.tab_2 -> {
                if (bFragment2 == null) {
                    bFragment2 = MineFragment()
                }
                switchFragment(bFragment2!!)
            }
        }
    }


    /**
     * 选择加载的Fragment
     */
    private fun switchFragment(fragment: Fragment) {
        if (fragment !== mFragment) {
            val transaction = supportFragmentManager
                    .beginTransaction()
            if (!fragment.isAdded) { // 先判断是否被add过
                transaction.hide(mFragment).add(R.id.fragment, fragment).commit() // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mFragment).show(fragment).commit() // 隐藏当前的fragment，显示下一个
            }
            mFragment = fragment
        }
    }

}
