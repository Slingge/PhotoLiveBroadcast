package com.photolivebroadcast.ui.mine.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.dialog.PermissionsDialog
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.result.EditUserHttp
import com.photolivebroadcast.ui.mine.result.Upphoto
import com.photolivebroadcast.util.ImageFileUtil
import com.photolivebroadcast.util.StatickUtil
import kotlinx.android.synthetic.main.activity_personal_info.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by Slingge on 2018/9/4.
 */
class PersonalInfoActivity : BaseActivity(), View.OnClickListener {

    private var name = ""
    private var sex = ""
    private var headerUrl = ""
    private var headerPath = ""//头像路径

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)
        init()
    }


    private fun init() {
        inittitle("编辑个人信息")
        StatusBarWhiteColor()

        tv_right.text = "保存"
        tv_right.visibility = View.VISIBLE
        tv_right.setOnClickListener(this)

        tv_id.text = StatickUtil.uid

        name = StatickUtil.userModel.nickname
        sex = StatickUtil.userModel.sex
        headerUrl = StatickUtil.userModel.imgurl

        ImageLoader.getInstance().displayImage(headerUrl, iv_header)

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
                MyApplication.openActivityForResult(this, EditNameActivity::class.java, 1)
            }
            R.id.tv_sex -> {
                MyApplication.openActivityForResult(this, EditSexActivity::class.java, 2)
            }
            R.id.tv_right -> {
                if (TextUtils.isEmpty(headerUrl) && TextUtils.isEmpty(headerPath)) {
                    ToastUtil.showToast("请选择头像")
                    return
                }
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入昵称")
                    return
                }
                if (TextUtils.isEmpty(sex)) {
                    ToastUtil.showToast("请选择性别")
                    return
                }
                if (!TextUtils.isEmpty(headerPath)) {
                    ProgressDialog.showDialog(this)
                    val list = ArrayList<String>()
                    Upphoto.upPhoto(list, object : Upphoto.UpPhotoCallBack {
                        override fun upHeaderUrl(url: String) {
                            EditUser(name, sex)
                        }
                    })
                } else {
                    EditUser(name, sex)
                }
            }
        }
    }


    private fun EditUser(name: String, sex: String) {
        EditUserHttp.edit(this, name, sex)
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
            headerPath = PictureSelector.obtainMultipleResult(data)[0].cutPath
            val bitmap = ImageFileUtil.getBitmapFromPath(headerPath)//压缩的路径
            iv_header.setImageBitmap(bitmap)
        } else if (requestCode == 1) {//昵称
            name = data.getStringExtra("name")
            tv_name.text = name
        } else if (requestCode == 2) {//性别
            sex = data.getStringExtra("sex")
            tv_sex.text = sex
        }
    }


}