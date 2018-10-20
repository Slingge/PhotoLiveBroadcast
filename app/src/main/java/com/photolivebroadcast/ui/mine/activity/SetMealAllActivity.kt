package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.nostra13.universalimageloader.core.ImageLoader
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.adapter.SetMealAllAdapter
import com.photolivebroadcast.ui.mine.model.ServiceNumModel
import com.photolivebroadcast.ui.mine.model.SetMealaAllModel
import com.photolivebroadcast.ui.mine.result.MappingServiceHttp
import com.photolivebroadcast.ui.mine.result.SetMealHttp
import com.photolivebroadcast.util.StatickUtil
import kotlinx.android.synthetic.main.activity_setmeal_all.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 我的套餐
 * Created by Slingge on 2018/9/8.
 */
class SetMealAllActivity : BaseActivity() {

    private var setMealList = ArrayList<SetMealaAllModel.listtcsModel>()
    private var setMealAdapter: SetMealAllAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setmeal_all)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("我的套餐")
        StatusBarWhiteColor()

        ImageLoader.getInstance().displayImage(StatickUtil.headerUrl, iv_header)
        tv_name.text = StatickUtil.userModel.nickname
        if(StatickUtil.userModel.sex=="男"){
            iv_sex.setImageResource(R.drawable.ic_boy)
            iv_sex.setBackgroundColor(resources.getColor(R.color.boy))
        }else{
            iv_sex.setImageResource(R.drawable.ic_girl)
            iv_sex.setBackgroundColor(resources.getColor(R.color.girl))
        }


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        view_bg.setOnClickListener { v ->
            MyApplication.openActivity(this, SetMealMyActivity::class.java)
        }

        ProgressDialog.showDialog(this)
        SetMealHttp.SetMealAll()
        MappingServiceHttp.getServiceNum()

    }


    @Subscribe
    fun onEvent(list: ArrayList<SetMealaAllModel.listtcsModel>) {
        setMealList = list
        setMealAdapter = SetMealAllAdapter(this, setMealList)
        recyclerView.adapter = setMealAdapter
    }

    @Subscribe
    fun onEvent(model: ServiceNumModel) {
        tv_album.text = "相册数量：" + model.data.usenum + "/" + model.data.allnumber
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}