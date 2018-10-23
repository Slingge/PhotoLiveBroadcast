package com.photolivebroadcast.ui.mine.fragment

import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.nostra13.universalimageloader.core.ImageLoader
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.mine.activity.*
import com.photolivebroadcast.util.StatickUtil
import com.photolivebroadcast.util.StatusBarBlackWordUtil
import com.photolivebroadcast.util.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_mine.*
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.ui.mine.result.PersonInfoHttp


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
            StatusBarUtil.setColorNoTranslucent(activity, resources.getColor(R.color.white))
            StatusBarBlackWordUtil.StatusBarLightMode(activity)
        }

        iv_right.setOnClickListener(this)
        tv_real.setOnClickListener(this)
        tv_balance.setOnClickListener(this)
        tv_send.setOnClickListener(this)

        tv_setmeal.setOnClickListener(this)
        tv_trimming.setOnClickListener(this)
        tv_invitation.setOnClickListener(this)
        tv_setting.setOnClickListener(this)
        tv_copy.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        ImageLoader.getInstance().displayImage(StatickUtil.headerUrl, iv_header)
        tv_name.text = StatickUtil.userModel.nickname
        tv_id.text = StatickUtil.uid
        if (StatickUtil.userModel.iscompany == "N") {
            tv_authentication.text = "未认证"
        } else {
            tv_authentication.text = "已认证"
        }
        if (StatickUtil.sex == "男") {
            iv_sex.setImageResource(R.drawable.ic_boy)
            iv_sex.setBackgroundColor(resources.getColor(R.color.boy))
        } else {
            iv_sex.setImageResource(R.drawable.ic_girl)
            iv_sex.setBackgroundColor(resources.getColor(R.color.girl))
        }

    }

    private fun init() {

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_right -> {//个人信息
                MyApplication.openActivity(activity!!, PersonalInfoActivity::class.java)
            }
            R.id.tv_real -> {//实名认证
                MyApplication.openActivity(activity!!, RealnameAuthenticationActivity::class.java)
            }
            R.id.tv_balance -> {//我的账户
                MyApplication.openActivity(activity!!, MyAccountActivity::class.java)
            }
            R.id.tv_send -> {//我发出的
                return
                MyApplication.openActivity(activity!!, MySendActivity::class.java)
            }
            R.id.tv_setmeal -> {//我的套餐
                MyApplication.openActivity(activity!!, SetMealAllActivity::class.java)
            }
            R.id.tv_trimming -> {//修图服务
                MyApplication.openActivity(activity!!, MappingServiceActivity1::class.java)
            }
            R.id.tv_invitation -> {//邀请好友
                MyApplication.openActivity(activity!!, ExtensionActivity::class.java)
            }
            R.id.tv_setting -> {//设置
                MyApplication.openActivity(activity!!, SetUpActivity::class.java)
            }
            R.id.tv_copy -> {//复制id
                val cm = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                // 将文本内容放到系统剪贴板里。
                cm.text = StatickUtil.uid
                ToastUtil.showToast("已复制")
            }
        }
    }


    override fun loadData() {

    }

}