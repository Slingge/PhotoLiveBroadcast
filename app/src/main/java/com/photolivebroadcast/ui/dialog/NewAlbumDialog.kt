package com.photolivebroadcast.ui.dialog

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.view.*
import android.widget.ImageView
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.newAlbum.NewAlbumActivity


/**
 *
 * Created by Slingge on 2018/9/11.
 */
object NewAlbumDialog {

    private var builder: AlertDialog? = null

    fun dialogEducation(context: Activity) {
        if (builder == null) {
            builder = AlertDialog.Builder(context, R.style.Dialog).create() // 先得到构造器
        }
        builder!!.show()
        val factory = LayoutInflater.from(context)
        val view = factory.inflate(R.layout.dialog_new_album, null)
        builder!!.window.setContentView(view)

        val iv_newbuild = view.findViewById<ImageView>(R.id.iv_newbuild)
        iv_newbuild.setOnClickListener { v ->
            MyApplication.openActivity(context, NewAlbumActivity::class.java)
        }
        val iv_createLive = view.findViewById<ImageView>(R.id.iv_createLive)
        iv_createLive.setOnClickListener { v ->

        }

        val iv_upload = view.findViewById<ImageView>(R.id.iv_upload)
        iv_upload.setOnClickListener { v ->
            InvitationDialog.dialogEducation(context)
        }

        val iv_cancle = view.findViewById<ImageView>(R.id.iv_cancle)
        iv_cancle.setOnClickListener { v ->
            builder!!.dismiss()
        }


        val dialogWindow = builder!!.window
        dialogWindow.setGravity(Gravity.BOTTOM)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用
        val p = dialogWindow.attributes // 获取对话框当前的参数值
//        p.height = (d.getHeight() * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = d.width // 宽度设置为屏幕宽
        dialogWindow.attributes = p


    }


    fun dismiss() {
        if (builder != null) {
            builder!!.dismiss()
            builder = null
        }
    }


}