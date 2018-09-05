package com.photolivebroadcast.ui.mine.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Path
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.util.ImageFileUtil
import kotlinx.android.synthetic.main.activity_personal_authentication2.*

/**
 *个人认证1
 * Created by Slingge on 2018/9/5.
 */
class PersonalAuthenticationActivity2 : BaseActivity(), View.OnClickListener {

    private var flag = -1//0id1，正面；0id2，反面

    private var path1: String? = null//1，正面
    private var path2: String? = null//，反面


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_authentication2)
        init()
    }


    private fun init() {
        inittitle("实名认证")
        StatusBarWhiteColor()

        tv_next.setOnClickListener(this)
        iv_id1.setOnClickListener(this)
        iv_id2.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_id1 -> {
                flag = 0
                SelectPictureUtil.selectPicture(this, 1, 0, false)
            }
            R.id.iv_id2 -> {
                flag = 1
                SelectPictureUtil.selectPicture(this, 1, 0, false)
            }
            R.id.iv_del1 -> {
                path1 = ""
                iv_del1.visibility = View.GONE
                iv_fd1.visibility = View.GONE
            }
            R.id.iv_del2 -> {
                path2 = ""
                iv_del2.visibility = View.GONE
                iv_fd2.visibility = View.GONE
            }
            R.id.iv_fd1 -> {

            }
            R.id.iv_fd2 -> {

            }
            R.id.iv_id2 -> {
                flag = 1
                SelectPictureUtil.selectPicture(this, 1, 0, false)
            }


            R.id.tv_next -> {
                if (TextUtils.isEmpty(path1)) {
                    ToastUtil.showToast("请选择人像面")
                    return
                }
                if (TextUtils.isEmpty(path2)) {
                    ToastUtil.showToast("请选择国徽像")
                    return
                }
                MyApplication.openActivity(this, CertificationAuditActivity::class.java)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            // 图片、视频、音频选择结果回调
            val bitmap = ImageFileUtil.getBitmapFromPath(PictureSelector.obtainMultipleResult(data)[0].compressPath)//压缩的路径
            if (flag == 0) {
                path1 = PictureSelector.obtainMultipleResult(data)[0].path
                iv_id1.setImageBitmap(bitmap)
                iv_del1.visibility = View.VISIBLE
                iv_fd1.visibility = View.VISIBLE
            } else {
                path2 = PictureSelector.obtainMultipleResult(data)[0].path
                iv_id2.setImageBitmap(bitmap)
                iv_del2.visibility = View.VISIBLE
                iv_fd2.visibility = View.VISIBLE
            }

            if (TextUtils.isEmpty(path1) && TextUtils.isEmpty(path2)) {
                tv_next.setBackgroundResource(R.drawable.bg_them5)
            }
        }
    }

}