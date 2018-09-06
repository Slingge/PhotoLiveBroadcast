package com.photolivebroadcast.ui.mine.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.util.ImageFileUtil
import kotlinx.android.synthetic.main.activity_enterprise_authentication2.*

/**
 * 企业认证1
 * Created by Slingge on 2018/9/6.
 */
class EnterpriseAuthenticationActivity2 : BaseActivity(), View.OnClickListener {

    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enterprise_authentication2)
        init()
    }


    private fun init() {
        inittitle("实名认证")
        StatusBarWhiteColor()

        image.setOnClickListener(this)
        tv_next.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.image -> {
                SelectPictureUtil.selectPicture(this, 1, 0, false)
            }
            R.id.tv_next -> {
                if (bitmap == null) {
                    ToastUtil.showToast("请选择营业执照")
                    return
                }
                val bundle = Bundle()
                bundle.putInt("type", intent.getIntExtra("type", 0))
                MyApplication.openActivity(this, CertificationAuditActivity::class.java, bundle)
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
            bitmap = ImageFileUtil.getBitmapFromPath(PictureSelector.obtainMultipleResult(data)[0].compressPath)//压缩的路径
            image.setImageBitmap(bitmap)
            image1.visibility=View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bitmap != null) {
            bitmap!!.recycle()
            bitmap = null
        }
    }


}