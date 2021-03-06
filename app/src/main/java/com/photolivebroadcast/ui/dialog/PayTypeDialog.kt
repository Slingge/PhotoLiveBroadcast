package com.photolivebroadcast.ui.dialog

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import com.photolivebroadcast.R

/**
 * 支付
 * Created by Slingge on 2018/9/8.
 */
object PayTypeDialog {


    var builder: AlertDialog? = null

    fun showDialog(context: Activity) {

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_payment, null)
        if (builder == null) {
            builder = AlertDialog.Builder(context, R.style.Dialog).create() // 先得到构造器
        }
        builder!!.show()
        builder!!.window.setContentView(view)
        builder!!.setCanceledOnTouchOutside(false)

        val dialogWindow = builder!!.window
        dialogWindow.setGravity(Gravity.BOTTOM)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用
        val p = dialogWindow.attributes // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = d.width    // 宽度设置为屏幕宽
        dialogWindow.attributes = p

    }


    fun dissDialog() {
        if (builder != null) {
            builder!!.dismiss()
            builder = null
        }
    }


}