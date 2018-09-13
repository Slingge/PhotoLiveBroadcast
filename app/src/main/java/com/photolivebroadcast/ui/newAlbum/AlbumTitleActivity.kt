package com.photolivebroadcast.ui.newAlbum

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.util.AbStrUtil
import kotlinx.android.synthetic.main.activity_album_title.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 相册标题副标题
 * Created by Slingge on 2018/9/13.
 */
class AlbumTitleActivity : BaseActivity() {

    private var flag = -1//0标题，1副标题


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_title)
        init()
    }


    private fun init() {

        flag = intent.getIntExtra("flag", -1)

        if (flag == 0) {
            inittitle("相册标题")
        } else if (flag == 1) {
            inittitle("相册副标题")
            et_title.setSingleLine(false)
            et_title.minLines = 3
            et_title.hint = "输入相册副标题（不多于20个字）"
            et_title.gravity = Gravity.LEFT and Gravity.TOP
        }

        StatusBarWhiteColor()

        tv_right.text = " 完成"
        tv_right.visibility = View.VISIBLE
        tv_right.setOnClickListener { v ->
            val title = AbStrUtil.etTostr(et_title)
            if (TextUtils.isEmpty(title)) {
                ToastUtil.showToast("请输入标题")
                return@setOnClickListener
            }

            val bundle = Bundle()
            bundle.putString("title", title)
            val intent = Intent()
            intent.putExtras(bundle)
            if (flag == 0) {
                setResult(0, intent)
            } else {
                setResult(1, intent)
            }
            finish()
        }

    }


}