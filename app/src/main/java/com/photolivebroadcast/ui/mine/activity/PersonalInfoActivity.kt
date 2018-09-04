package com.photolivebroadcast.ui.mine.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.dialog.PermissionsDialog
import com.photolivebroadcast.util.ImageFileUtil
import kotlinx.android.synthetic.main.activity_personal_info.*

/**
 * Created by Slingge on 2018/9/4.
 */
class PersonalInfoActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)
        init()
    }


    private fun init() {
        inittitle("编辑个人信息")
        StatusBarWhiteColor()

        iv_header.setOnClickListener(this)
        tv_name.setOnClickListener(this)
        tv_sex.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_header -> {
                SelectPictureUtil.selectPicture(this, 1, 0, true)
            }
            R.id.tv_name -> {
               MyApplication.openActivity(this,EditNameActivity::class.java)
            }
            R.id.tv_sex -> {
                MyApplication.openActivity(this,EditSexActivity::class.java)
            }
        }
    }


    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            SelectPictureUtil.selectPicture(this, 1, 0, true)
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要访问内存卡和拍照权限")
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
            iv_header.setImageBitmap(bitmap)
        }
    }


}