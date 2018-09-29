package com.photolivebroadcast.ui.dialog

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.util.AbStrUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus


/**
 * 选择社区
 * Created by Slingge on 2018/8/15
 */
object EditDialog {

    private var builder: AlertDialog? = null

    interface EditTextCallBack {
        fun editText(name: String)
    }

    fun communityDialog(context: Activity, editTextCallBack: EditTextCallBack) {


        val view = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null)
        if (builder == null) {
            builder = AlertDialog.Builder(context, R.style.Dialog).create() // 先得到构造器
        }
        builder!!.show()
        builder!!.window.setContentView(view)


        val et_num = view.findViewById<EditText>(R.id.et_num)
        val tc_next = view.findViewById<TextView>(R.id.tc_next)
        tc_next.setOnClickListener { v ->
            val name = AbStrUtil.tvTostr(et_num)
            if (TextUtils.isEmpty(name)) {
                return@setOnClickListener
            }
            editTextCallBack.editText(name)
            builder!!.dismiss()
            builder = null
        }

        val dialogWindow = builder!!.window
        dialogWindow.setGravity(Gravity.CENTER)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用
        val p = dialogWindow.attributes // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = (d.width * 0.85).toInt() // 宽度设置为屏幕宽
        dialogWindow.attributes = p


        builder!!.window.clearFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    }


    fun dissCommunity() {
        if (builder != null) {
            builder!!.dismiss()
            builder = null
        }

    }

}