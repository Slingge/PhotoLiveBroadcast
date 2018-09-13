package com.photolivebroadcast.ui.newAlbum

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import kotlinx.android.synthetic.main.activity_new_album.*

/**
 * Created by Slingge on 2018/9/11.
 */
class NewAlbumActivity : BaseActivity(), View.OnClickListener {

    private var path1 = ""//广告图
    private var path2 = ""//引导图

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_album)
        init()
    }


    private fun init() {
        inittitle("新建相册")
        StatusBarWhiteColor()

        tv_titlemain.setOnClickListener(this)
        tv_subtitle.setOnClickListener(this)

        bootstrap.setOnClickListener(this)
        advertisement.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_titlemain -> {
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                MyApplication.openActivityForResult(this, AlbumTitleActivity::class.java, bundle, 0)
            }
            R.id.tv_subtitle -> {
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivityForResult(this, AlbumTitleActivity::class.java, bundle, 1)
            }
            R.id.bootstrap -> {
                val bundle = Bundle()
                bundle.putInt("flag", 2)
                MyApplication.openActivityForResult(this, AlbumPhotoActivity::class.java, bundle, 2)
            }
            R.id.advertisement -> {
                val bundle = Bundle()
                bundle.putInt("flag", 3)
                MyApplication.openActivityForResult(this, AlbumPhotoActivity::class.java, bundle, 3)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }

        if (resultCode == 0) {//标题
            val title = data.getStringExtra("title")
            tv_titlemain.text = title
        } else if (resultCode == 1) {//副标题
            val title = data.getStringExtra("title")
            tv_subtitle.text = title
        } else if (resultCode == 2) {//副标题
            path1 = data.getStringExtra("path")

        } else if (resultCode == 3) {//副标题
            path2 = data.getStringExtra("path")
        }

    }


}