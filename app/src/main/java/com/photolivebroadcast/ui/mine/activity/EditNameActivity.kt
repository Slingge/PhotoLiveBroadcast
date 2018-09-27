package com.photolivebroadcast.ui.mine.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.util.AbStrUtil
import kotlinx.android.synthetic.main.activity_editname.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by Slingge on 2018/9/4.
 */
class EditNameActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editname)
        init()
    }


    private fun init() {
        inittitle("编辑用户名")
        StatusBarWhiteColor()

        tv_right.text = "完成"
        tv_right.visibility = View.VISIBLE
        tv_right.setOnClickListener { v ->
            val name = AbStrUtil.etTostr(et_name)
            if (TextUtils.isEmpty(name)) {
                ToastUtil.showToast("请输入昵称")
                return@setOnClickListener
            }
            val intent = Intent()
            intent.putExtra("name", name)
            setResult(1, intent)
            finish()
        }
    }


}