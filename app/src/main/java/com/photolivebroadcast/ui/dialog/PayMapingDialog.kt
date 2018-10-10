package com.photolivebroadcast.ui.dialog

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.mine.callBack.PayMapingCallBack

/**
 * 支付
 * Created by Slingge on 2018/9/8.
 */
object PayMapingDialog {


    var builder: AlertDialog? = null

    fun showDialog(context: Activity, payMapingCallBack: PayMapingCallBack) {

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_pay_mapping, null)
        if (builder == null) {
            builder = AlertDialog.Builder(context, R.style.Dialog).create() // 先得到构造器
        }
        builder!!.show()
        builder!!.window.setContentView(view)
        builder!!.setCanceledOnTouchOutside(false)

        val dialogWindow = builder!!.window
        dialogWindow.setGravity(Gravity.CENTER)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用
        val p = dialogWindow.attributes // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = (d.width * 0.85).toInt()  // 宽度设置为屏幕宽
        dialogWindow.attributes = p

        val tv_pay = view.findViewById<TextView>(R.id.tv_pay)
        tv_pay.setOnClickListener { v ->
            payMapingCallBack.payMapping()
            builder!!.dismiss()
        }
        val tv_cancel2 = view.findViewById<TextView>(R.id.tv_cancel2)
        tv_cancel2.setOnClickListener { v ->
            builder!!.dismiss()
        }
    }


    fun dissDialog() {
        if (builder != null) {
            builder!!.dismiss()
            builder = null
        }
    }


}