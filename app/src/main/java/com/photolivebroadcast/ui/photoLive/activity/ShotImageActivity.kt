package com.photolivebroadcast.ui.photoLive.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.dialog.DatePop
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.model.MySendModel
import com.photolivebroadcast.ui.photoLive.http.ShotImageHttp
import com.photolivebroadcast.util.AbStrUtil
import kotlinx.android.synthetic.main.activity_shot_image.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 活动拍摄信息
 * Created by zhf on 2018/9/17.
 */

class ShotImageActivity : BaseActivity(), View.OnClickListener, DatePop.WheelViewCallBack2 {

    private var datePop: DatePop? = null
    private var flag = 0//0 开始时间，1结束时间

    private var pid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shot_image)
        EventBus.getDefault().register(this)
        init()
    }

    private fun init() {
        inittitle("活动拍摄信息")
        StatusBarWhiteColor()

        pid = intent.getStringExtra("id")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "完成"
        tv_right.setTextColor(resources.getColor(R.color.colorTheme))
        tv_right.setOnClickListener(this)

        tv_start_time.setOnClickListener(this)
        tv_end_time.setOnClickListener(this)

        tv_address.setOnClickListener(this)
        tv_detail.setOnClickListener(this)
        tv_process.setOnClickListener(this)
        tv_publicNum.setOnClickListener(this)
    }

    @Subscribe
    fun onEvent(model: MySendModel.listalbumsModel) {
        tv_start_time.text = model.start_time
        tv_end_time.text = model.end_time

        tv_address.text = model.address
        tv_detail.text = model.event_remark
        tv_process.text = model.event_liucheng
        tv_publicNum.text = model.zb_wechat
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_start_time -> {
                flag = 0
                if (datePop == null) {
                    datePop = DatePop(this, this)
                }
                if (!datePop!!.isShowing) {
                    datePop!!.showAtLocation(ll_main, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
                }
            }
            R.id.tv_end_time -> {
                flag = 1
                if (datePop == null) {
                    datePop = DatePop(this, this)
                }
                if (!datePop!!.isShowing) {
                    datePop!!.showAtLocation(ll_main, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
                }
            }
            R.id.tv_address -> {//地址
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                MyApplication.openActivityForResult(this, EditShotImageActivity::class.java, bundle, 0)
            }
            R.id.tv_detail -> {
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivityForResult(this, EditShotImageActivity::class.java, bundle, 1)
            }
            R.id.tv_process -> {
                val bundle = Bundle()
                bundle.putInt("flag", 2)
                MyApplication.openActivityForResult(this, EditShotImageActivity::class.java, bundle, 2)
            }
            R.id.tv_publicNum -> {
                val bundle = Bundle()
                bundle.putInt("flag", 3)
                MyApplication.openActivityForResult(this, EditShotImageActivity::class.java, bundle, 3)
            }
            R.id.tv_right -> {
                val startTime = AbStrUtil.tvTostr(tv_start_time)
                if (TextUtils.isEmpty(startTime)) {
                    ToastUtil.showToast("请选择开始时间")
                    return
                }
                val endTime = AbStrUtil.tvTostr(tv_end_time)
                if (TextUtils.isEmpty(endTime)) {
                    ToastUtil.showToast("请选择结束时间")
                    return
                }
                val address = AbStrUtil.tvTostr(tv_address)
                if (TextUtils.isEmpty(address)) {
                    ToastUtil.showToast("请输入地址")
                    return
                }
                val details = AbStrUtil.tvTostr(tv_detail)
                if (TextUtils.isEmpty(details)) {
                    ToastUtil.showToast("请输入活动详情")
                    return
                }
                val process = AbStrUtil.tvTostr(tv_process)
                if (TextUtils.isEmpty(process)) {
                    ToastUtil.showToast("请输入活动流程")
                    return
                }
                val publicNum = AbStrUtil.tvTostr(tv_publicNum)
                if (TextUtils.isEmpty(publicNum)) {
                    ToastUtil.showToast("请输入主办方公众号")
                    return
                }
                ProgressDialog.showDialog(this)
                ShotImageHttp.shot(this, pid, details, startTime, endTime, address, publicNum, process)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        val str = data.getStringExtra("title")
        if (requestCode == 0) {
            tv_address.text = str
        } else if (requestCode == 1) {
            tv_detail.text = str
        } else if (requestCode == 2) {
            tv_process.text = str
        } else if (requestCode == 3) {
            tv_publicNum.text = str
        }
    }

    override fun position(position1: String, position2: String, position3: String) {
        if (flag == 0) {
            tv_start_time.text = "$position1-$position2-$position3"
        } else {
            tv_end_time.text = "$position1-$position2-$position3"
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}
