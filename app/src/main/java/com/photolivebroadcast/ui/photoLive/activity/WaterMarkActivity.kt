package com.photolivebroadcast.ui.photoLive.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.PermissionsDialog
import com.photolivebroadcast.util.FileUtilsFeng
import com.photolivebroadcast.util.ImageFileUtil
import com.photolivebroadcast.view.BubbleTextView
import com.photolivebroadcast.view.StickerView
import kotlinx.android.synthetic.main.include_basetop.*
import java.util.ArrayList

/**
 * Created by zhf on 2018/9/17.
 */

class WaterMarkActivity : BaseActivity() {

    //当前处于编辑状态的贴纸
    private var mCurrentView: StickerView? = null
    //存储贴纸列表
    private var mViews: ArrayList<View>? = null
    //当前处于编辑状态的气泡
    private var mCurrentEditTextView: BubbleTextView? = null

    private var mContentRootView: RelativeLayout? = null

    private var ivAdd: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_mark)
        init()
    }

    private fun init() {
        inittitle("水印设置")
        StatusBarWhiteColor()
        mContentRootView = findViewById<View>(R.id.rl_content_root) as RelativeLayout

        tv_right.visibility = View.VISIBLE
        tv_right.text = "预览"
        tv_right.setOnClickListener { v ->
            mCurrentView!!.setInEdit(false)
            generateBitmap()
        }
        mViews = ArrayList()
        ivAdd = findViewById(R.id.iv_add)
        ivAdd!!.setOnClickListener(View.OnClickListener {
            SelectPictureUtil.selectPicture(this, 1, 0, true)
        })
    }

    //添加表情
    private fun addStickerView(bitmap: Bitmap) {
        val stickerView = StickerView(this)
        stickerView.setBitmap(bitmap)
        stickerView.setOperationListener(object : StickerView.OperationListener {
            override fun onDeleteClick() {
                mViews!!.remove(stickerView)
                mContentRootView!!.removeView(stickerView)
            }

            override fun onEdit(stickerView: StickerView) {
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView!!.setInEdit(false)
                }
                mCurrentView!!.setInEdit(false)
                mCurrentView = stickerView
                mCurrentView!!.setInEdit(true)
            }

            override fun onTop(stickerView: StickerView) {
                val position = mViews!!.indexOf(stickerView)
                if (position == mViews!!.size - 1) {
                    return
                }
                val stickerTemp = mViews!!.removeAt(position) as StickerView
                mViews!!.add(mViews!!.size, stickerTemp)
            }
        })
        val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        mContentRootView!!.addView(stickerView, lp)
        mViews!!.add(stickerView)
        setCurrentEdit(stickerView)
    }

    /**
     * 设置当前处于编辑模式的贴纸
     */
    private fun setCurrentEdit(stickerView: StickerView) {
        if (mCurrentView != null) {
            mCurrentView!!.setInEdit(false)
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView!!.setInEdit(false)
        }
        mCurrentView = stickerView
        stickerView.setInEdit(true)
    }

    private fun generateBitmap() {
        val bitmap = Bitmap.createBitmap(mContentRootView!!.getWidth(),
                mContentRootView!!.getHeight(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        mContentRootView!!.draw(canvas)

        val iamgePath = FileUtilsFeng.saveBitmapToLocal(bitmap, this)
        val intent = Intent(this, WaterMarkActivity2::class.java)
        intent.putExtra("image", "" + iamgePath)
        startActivity(intent)
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

    private var path = ""//水印路径

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            // 图片、视频、音频选择结果回调
            path = PictureSelector.obtainMultipleResult(data)[0].cutPath
            val bitmap = ImageFileUtil.getBitmapFromPath(path)//压缩的路径
            addStickerView(bitmap)
        }
    }

}
