package com.photolivebroadcast.ui.mine.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.DatePop
import com.photolivebroadcast.ui.dialog.NumPop
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.model.MappingServicePriceModel
import com.photolivebroadcast.ui.mine.result.MappingServiceHttp
import com.photolivebroadcast.util.AbStrUtil
import kotlinx.android.synthetic.main.activity_mapping_service2.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 修图服务
 * Created by Slingge on 2018/9/8.
 */
class MappingServiceActivity2 : BaseActivity(), View.OnClickListener, DatePop.WheelViewCallBack2
        , NumPop.WheelViewCallBack2 {

    private var photoNumList = ArrayList<String>()
    private var listxiutuprices = ArrayList<MappingServicePriceModel.listxiutupricesModel>()


    private var datePop: DatePop? = null
    private var numPop: NumPop? = null
    private var photoNumPrice = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapping_service2)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("预约修图")
        StatusBarWhiteColor()

        tv_time.setOnClickListener(this)
        tv_num.setOnClickListener(this)
        tv_next.setOnClickListener { v ->
            val content = AbStrUtil.etTostr(et_info)
            if (TextUtils.isEmpty(content)) {
                ToastUtil.showToast("请输入活动介绍")
                return@setOnClickListener
            }
            val time = AbStrUtil.tvTostr(tv_time)
            if (TextUtils.isEmpty(time)) {
                ToastUtil.showToast("请选择修图时间")
                return@setOnClickListener
            }
            ProgressDialog.showDialog(this)
            MappingServiceHttp.SendMappingService(this, tv_next, photoNumPrice, content, time)
        }

        ProgressDialog.showDialog(this)
        MappingServiceHttp.getServiceNum()
        MappingServiceHttp.MappingServicePrice()
    }


    @Subscribe
    fun onEvent(listxiutuprices: ArrayList<MappingServicePriceModel.listxiutupricesModel>) {
        this.listxiutuprices = listxiutuprices
        for (model in listxiutuprices) {
            photoNumList.add(model.tcname + " 价格：" + model.price)
        }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_time -> {
                if (datePop == null) {
                    datePop = DatePop(this, this)
                }
                if (!datePop!!.isShowing) {
                    datePop!!.showAtLocation(cl_main, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
                }
            }
            R.id.tv_num -> {
                if (numPop == null) {
                    numPop = NumPop(this, photoNumList, this)
                }
                if (!numPop!!.isShowing) {
                    numPop!!.showAtLocation(cl_main, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
                }
            }
        }
    }

    override fun position(position1: String, position2: String, position3: String) {
        tv_time.text = "$position1-$position2-$position3"
    }

    override fun position(position1: Int) {
        tv_num.text = listxiutuprices[position1].tcname
        photoNumPrice = listxiutuprices[position1].price.toString()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}