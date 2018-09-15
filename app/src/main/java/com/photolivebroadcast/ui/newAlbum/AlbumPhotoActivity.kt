package com.photolivebroadcast.ui.newAlbum

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.photolivebroadcast.R
import kotlinx.android.synthetic.main.activity_album_photo.*

/**
 * 相册广告图和引导图
 * Created by Slingge on 2018/9/13.
 */
class AlbumPhotoActivity : BaseActivity(), View.OnClickListener {


    private var flag = -1//2广告图，3引导图


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_photo)
        init()
    }


    private fun init() {
        flag = intent.getIntExtra("flag", -1)
        if (flag == 2) {
            inittitle("上传广告图")
        } else {
            inittitle("上传引导图")
            image.setImageResource(R.drawable.imag_01)
        }
        StatusBarWhiteColor()

        tv_enter.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_enter -> {
                SelectPictureUtil.selectPicture(this, 1, 0, false)
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
            val path = PictureSelector.obtainMultipleResult(data)[0].path//压缩的路径
            val bundle = Bundle()
            bundle.putString("path", path)
            val intent = Intent()
            intent.putExtras(bundle)
            if (flag == 0) {
                setResult(2, intent)
            } else {
                setResult(3, intent)
            }
        }
    }

}