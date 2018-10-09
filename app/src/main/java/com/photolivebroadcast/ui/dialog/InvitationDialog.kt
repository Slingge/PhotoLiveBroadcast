package com.photolivebroadcast.ui.dialog

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.util.AbStrUtil
import com.photolivebroadcast.util.StatickUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject


/**
 * 参与上传邀请码
 * Created by Slingge on 2018/9/11.
 */
object InvitationDialog {

    private var builder: AlertDialog? = null

    fun dialogEducation(context: Activity) {
        if (builder == null) {
            builder = AlertDialog.Builder(context, R.style.Dialog).create() // 先得到构造器
        }
        builder!!.show()
        val factory = LayoutInflater.from(context)
        val view = factory.inflate(R.layout.dialog_upload_invitation, null)
        builder!!.window.setContentView(view)
        val et_invitation = view.findViewById<EditText>(R.id.et_invitation)

        val tv_enter = view.findViewById<TextView>(R.id.tv_enter)
        tv_enter.setOnClickListener { v ->
            val code = AbStrUtil.etTostr(et_invitation)
            if (TextUtils.isEmpty(code)) {
                return@setOnClickListener
            }
            ProgressDialog.showDialog(context)
            ParticipateUploading(code)
        }


        et_invitation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s.toString())) {
                    tv_enter.setBackgroundResource(R.drawable.bg_gray5)
                } else {
                    tv_enter.setBackgroundResource(R.drawable.bg_them5)
                }
            }

        })

        val iv_cancel = view.findViewById<ImageView>(R.id.iv_cancel)
        iv_cancel.setOnClickListener { v ->
            builder!!.dismiss()
        }


        val dialogWindow = builder!!.window
        dialogWindow.setGravity(Gravity.CENTER)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用
        val p = dialogWindow.attributes // 获取对话框当前的参数值
//        p.height = (d.getHeight() * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = (d.width * 0.8).toInt()// 宽度设置为屏幕宽
        dialogWindow.attributes = p

        builder!!.window.clearFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }


    fun dismiss() {
        if (builder != null) {
            builder!!.dismiss()
            builder = null
        }
    }

//参与上传
    private fun ParticipateUploading(incode: String) {
        OkHttpUtils.post().url("http://112.74.169.87/videoCloud/photolive/ajaxcheckincode")
                .addParams("incode", incode).addParams("userid", StatickUtil.uid)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getInt("code") == 200) {
                            ToastUtil.showToast("加入成功")
                            dismiss()
                        } else {
                            ToastUtil.showToast(obj.getString("msg"))
                        }
                    }
                })
    }


}