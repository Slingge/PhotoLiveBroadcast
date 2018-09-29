package com.photolivebroadcast.ui.photoLive.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.util.AbStrUtil
import kotlinx.android.synthetic.main.activity_album_title.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 编辑活动拍摄信息
 * Created by Slingge on 2018/9/29
 */
class EditShotImageActivity : BaseActivity() {

    private var flag = -1//0地址,1详情，2流程，3公众号

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_title)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        flag = intent.getIntExtra("flag", -1)
        if (flag == 0) {
            inittitle("地址")
            et_title.hint = "地址"
        } else if (flag == 1) {
            inittitle("详情")
            et_title.hint = "详情"
        } else if (flag == 2) {
            inittitle("流程")
            et_title.hint = "流程"
        } else if (flag == 3) {
            inittitle("公众号")
            et_title.hint = "公众号"
        }

        tv_right.visibility = View.VISIBLE
        tv_right.setOnClickListener { v ->
            val str = AbStrUtil.etTostr(et_title)
            if (TextUtils.isEmpty(str)) {
                return@setOnClickListener
            }
            val intent = Intent()
            intent.putExtra("title", str)
            setResult(0, intent)
            finish()
        }

    }

}