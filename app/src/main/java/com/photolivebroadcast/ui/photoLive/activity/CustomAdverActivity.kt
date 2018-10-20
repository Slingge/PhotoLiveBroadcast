package com.photolivebroadcast.ui.photoLive.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.gson.Gson

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.PermissionsDialog
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.newAlbum.result.UpPublicPhoto
import com.photolivebroadcast.ui.photoLive.http.AdvertisementHttp
import com.photolivebroadcast.util.AbStrUtil
import com.photolivebroadcast.util.ImageFileUtil
import com.photolivebroadcast.util.PermissionUtil
import kotlinx.android.synthetic.main.activity_custom_adver.*

/**
 * Created by zhf on 2018/9/18.
 */

class CustomAdverActivity : BaseActivity(), View.OnClickListener {

    private var imageUrl = ""
    private var type = -1//0显示,-1不显示广告
    private var ImageBitmap: Bitmap? = null
    private var flag = -1//0图片，1跳转，2预约
    private var typeFlag = ""//文字类型

    private var pid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_adver)
        inittitle("自定义广告设置")
        initView()

    }

    private fun initView() {
        StatusBarWhiteColor()
        pid = intent.getStringExtra("id")
        ic_checkbox.setOnClickListener(this)
        iv_image.setOnClickListener(this)
        btn_add.setOnClickListener(this)

        radio.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_photo -> {
                    rl_image.visibility = View.VISIBLE
                    et_link.visibility = View.GONE
                    flag = 0
                    typeFlag = "图片"
                }
                R.id.rb_return -> {
                    rl_image.visibility = View.GONE
                    et_link.visibility = View.VISIBLE
                    flag = 1
                    typeFlag = "跳转"
                }
                R.id.rb_dial -> {
                    rl_image.visibility = View.GONE
                    et_link.visibility = View.GONE
                    flag = 2
                    typeFlag = "预约"
                }
            }
        }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ic_checkbox -> {
                if (type == 0) {
                    type = -1
                    ic_checkbox.setImageResource(R.mipmap.icon_fun_close)
                } else {
                    type = 0
                    ic_checkbox.setImageResource(R.mipmap.icon_fun_pen)
                }

            }
            R.id.iv_image -> {
                if (PermissionUtil.ApplyPermissionAlbum(this, 0)) {
                    SelectPictureUtil.selectPicture(this, 1, 0, false)
                }
            }
            R.id.btn_add -> {
                if (type == -1) {
                    ToastUtil.showToast("请开启广告")
                    return
                }
                var info = AbStrUtil.etTostr(et_title)
                var jumpUrl = ""
                if (TextUtils.isEmpty(info)) {
                    ToastUtil.showToast("请选择广告文字提示")
                    return
                }
                if (flag == 0) {
                    if (TextUtils.isEmpty(imageUrl)) {
                        ToastUtil.showToast("请选择广告图")
                        return
                    }
                } else if (flag == 1) {
                    jumpUrl = AbStrUtil.etTostr(et_link)
                    if (TextUtils.isEmpty(jumpUrl)) {
                        ToastUtil.showToast("请输入跳转链接")
                        return
                    }
                }
                ProgressDialog.showDialog(this)
                AdvertisementHttp.add(this, pid, info, typeFlag, imageUrl, jumpUrl)
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
        Log.e("图片....................", Gson().toJson(PictureSelector.obtainMultipleResult(data)))
        if (requestCode == 0) {
            // 图片、视频、音频选择结果回调
            if (!TextUtils.isEmpty(PictureSelector.obtainMultipleResult(data)[0].path)) {
                val imagePath = PictureSelector.obtainMultipleResult(data)[0].path
                ImageBitmap = ImageFileUtil.getBitmapFromPath(PictureSelector.obtainMultipleResult(data)[0].compressPath)//压缩的路径
                iv_image.setImageBitmap(ImageBitmap)
                UpPublicPhoto.upPhoto(imagePath, object : UpPublicPhoto.UpPhotoCallBack {
                    override fun upHeaderUrl(url: String) {
                        imageUrl = url
                    }
                })
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (ImageBitmap != null) {
            ImageBitmap!!.recycle()
            ImageBitmap = null
        }
    }

}
