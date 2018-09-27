package com.photolivebroadcast.ui.mine.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import kotlinx.android.synthetic.main.activity_editsex.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by Slingge on 2018/9/4.
 */
class EditSexActivity : BaseActivity() {

    private var sex = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editsex)
        init()
    }


    private fun init() {
        inittitle("编辑性别")
        StatusBarWhiteColor()

        tv_right.text = "完成"
        tv_right.visibility = View.VISIBLE
        tv_right.setOnClickListener { v ->
            if (TextUtils.isEmpty(sex)) {
                ToastUtil.showToast("请选择性别")
                return@setOnClickListener
            }
            val intent = Intent()
            intent.putExtra("sex", sex)
            setResult(2, intent)
            finish()
        }

        radio.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_boy) {
                sex = "男"
            } else if (checkedId == R.id.rb_girl) {
                sex = "女"
            }
        }
    }


}