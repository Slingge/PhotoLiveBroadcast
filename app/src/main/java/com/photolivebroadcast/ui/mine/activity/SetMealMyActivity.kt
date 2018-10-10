package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.adapter.SetMealMyAdapter
import com.photolivebroadcast.ui.mine.model.SetMealaMyModel
import com.photolivebroadcast.ui.mine.result.SetMealHttp
import kotlinx.android.synthetic.main.activity_setmeal_all.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 我的套餐
 * Created by Slingge on 2018/9/8.
 */
class SetMealMyActivity : BaseActivity() {

    private var setMealList = ArrayList<SetMealaMyModel.listbuysModel>()
    private var setMealAdapter: SetMealMyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setmeal_my)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("我的套餐")
        StatusBarWhiteColor()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        ProgressDialog.showDialog(this)
        SetMealHttp.myMeal()
    }


    @Subscribe
    fun onEvent(list: ArrayList<SetMealaMyModel.listbuysModel>) {
        setMealList = list
        setMealAdapter = SetMealMyAdapter(this, setMealList)
        recyclerView.adapter = setMealAdapter
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}