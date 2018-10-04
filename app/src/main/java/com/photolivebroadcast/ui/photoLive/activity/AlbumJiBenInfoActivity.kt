package com.photolivebroadcast.ui.photoLive.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.model.MySendModel
import com.photolivebroadcast.ui.newAlbum.AlbumPhotoActivity
import com.photolivebroadcast.ui.newAlbum.AlbumTitleActivity
import com.photolivebroadcast.ui.newAlbum.result.UpPublicPhoto
import com.photolivebroadcast.ui.photoLive.http.AlbumInfoHttp
import kotlinx.android.synthetic.main.activity_albumjibeninfo.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2018/9/28.
 */
class AlbumJiBenInfoActivity : BaseActivity(), View.OnClickListener {

    private var pid = ""

    private var path1 = ""//引导图
    private var path2 = ""//广告图
    private var path3 = ""//logo

    private var title = ""
    private var subtitle = ""

    private var isxiutu = "N"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albumjibeninfo)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("新建相册")
        StatusBarWhiteColor()

        tv_enter.visibility = View.GONE

        tv_right.visibility = View.VISIBLE
        tv_right.text = "完成"
        tv_right.setTextColor(resources.getColor(R.color.colorTheme))
        tv_right.setOnClickListener(this)

        tv_titlemain.setOnClickListener(this)
        tv_subtitle.setOnClickListener(this)

        bootstrap.setOnClickListener(this)
        advertisement.setOnClickListener(this)
        logo.setOnClickListener(this)

        radio.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_y) {
                isxiutu = "Y"
            } else if (checkedId == R.id.rb_n) {
                isxiutu = "N"
            }
        }
        pid=intent.getStringExtra("id")
        ProgressDialog.showDialog(this)
        AlbumInfoHttp.albumInfo(pid)
    }

    @Subscribe
    fun onEvent(model: MySendModel.listalbumsModel) {
        title=model.title
        subtitle=model.remark

        path1=model.bgimage
        path2=model.share_img
        path3=model.share_img

        tv_titlemain.text = model.title
        tv_subtitle.text = model.remark
        if (model.isxiutu == "N") {
            rb_n.isChecked = true
        } else {
            rb_y.isChecked = true
        }

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_titlemain -> {
                val bundle = Bundle()
                bundle.putInt("flag", 303)
                MyApplication.openActivityForResult(this, AlbumTitleActivity::class.java, bundle, 303)
            }
            R.id.tv_subtitle -> {
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivityForResult(this, AlbumTitleActivity::class.java, bundle, 1)
            }
            R.id.bootstrap -> {//引导图
                val bundle = Bundle()
                bundle.putInt("flag", 2)
                MyApplication.openActivityForResult(this, AlbumPhotoActivity::class.java, bundle, 2)
            }
            R.id.advertisement -> {//广告图
                val bundle = Bundle()
                bundle.putInt("flag", 3)
                MyApplication.openActivityForResult(this, AlbumPhotoActivity::class.java, bundle, 3)
            }
            R.id.logo -> {
                SelectPictureUtil.selectPicture(this, 1, 0, false)
            }
            R.id.tv_right -> {
                if (TextUtils.isEmpty(title)) {
                    ToastUtil.showToast("请输入相册标题")
                    return
                }
                if (TextUtils.isEmpty(subtitle)) {
                    ToastUtil.showToast("请输入相册副标题")
                    return
                }
                if (TextUtils.isEmpty(path1)) {
                    ToastUtil.showToast("请选择相册引导图")
                    return
                }
                if (TextUtils.isEmpty(path2)) {
                    ToastUtil.showToast("请输入相册广告图")
                    return
                }
                if (TextUtils.isEmpty(path3)) {
                    ToastUtil.showToast("请输入相册logo")
                    return
                }
                ProgressDialog.showDialog(this)
                AlbumInfoHttp.editAlbum(this, pid, title, subtitle, path1, path2, path3, isxiutu)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }

        if (requestCode == 303) {//标题
            title = data.getStringExtra("title")
            tv_titlemain.text = title
        } else if (requestCode == 1) {//副标题
            subtitle = data.getStringExtra("title")
            tv_subtitle.text = subtitle
        } else if (requestCode == 2) {// 引导图
            path1 = data.getStringExtra("path")
            tv_bootstrap.visibility = View.VISIBLE
        } else if (requestCode == 3) {// 广告图
            path2 = data.getStringExtra("path")
            tv_advertisement.visibility = View.VISIBLE
        } else if (requestCode == 0) {// logo
            val path=PictureSelector.obtainMultipleResult(data)[0].path
            UpPublicPhoto.upPhoto(path,object : UpPublicPhoto.UpPhotoCallBack{
                override fun upHeaderUrl(url: String) {
                    path3=url
                    tv_logo.visibility = View.VISIBLE
                }
            })
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}