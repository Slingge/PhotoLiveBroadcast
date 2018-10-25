package com.photolivebroadcast.ui.photoLive.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.PermissionsDialog
import com.photolivebroadcast.util.FileUtilsFeng
import com.photolivebroadcast.util.ImageFileUtil
import com.photolivebroadcast.util.PermissionUtil
import com.photolivebroadcast.view.BubbleInputDialog
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

    //气泡输入框
    private var mBubbleInputDialog: BubbleInputDialog? = null

    private var content = ""
    private var pId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_mark)

        pId = intent!!.getStringExtra("id")

        init()
    }

    private fun init() {
        inittitle("水印设置")
        StatusBarWhiteColor()
        mContentRootView = findViewById<View>(R.id.rl_content_root) as RelativeLayout
        mBubbleInputDialog = BubbleInputDialog(this)
        mBubbleInputDialog!!.setCompleteCallBack(object : BubbleInputDialog.CompleteCallBack {
            override fun onComplete(bubbleTextView: View, str: String) {
                (bubbleTextView as BubbleTextView).setText(str)
                content = str
            }
        })
        tv_right.visibility = View.VISIBLE
        tv_right.text = "预览"
        tv_right.setOnClickListener { v ->
            if (mCurrentView != null) {
                mCurrentView!!.setInEdit(false)
            }
            generateBitmap()
        }
        mViews = ArrayList()
        ivAdd = findViewById(R.id.iv_add)
        ivAdd!!.setOnClickListener {
            addBubble()
            ivAdd!!.visibility = View.GONE
        }
    }

    //添加气泡
    private fun addBubble() {
        val bubbleTextView = BubbleTextView(this,
                Color.WHITE, 0)
        bubbleTextView.setImageResource(R.mipmap.bubble_7_rb)
        bubbleTextView.setOperationListener(object : BubbleTextView.OperationListener {
            override fun onDeleteClick() {
                mViews!!.remove(bubbleTextView)
                mContentRootView!!.removeView(bubbleTextView)
            }

            override fun onEdit(bubbleTextView: BubbleTextView) {
                if (mCurrentView != null) {
                    mCurrentView!!.setInEdit(false)
                }
                mCurrentEditTextView!!.setInEdit(false)
                mCurrentEditTextView = bubbleTextView
                mCurrentEditTextView!!.setInEdit(true)
            }

            override fun onClick(bubbleTextView: BubbleTextView) {
                mBubbleInputDialog!!.setBubbleTextView(bubbleTextView)
                mBubbleInputDialog!!.show()
            }

            override fun onTop(bubbleTextView: BubbleTextView) {
                val position = mViews!!.indexOf(bubbleTextView)
                if (position == mViews!!.size - 1) {
                    return
                }
                val textView = mViews!!.removeAt(position) as BubbleTextView
                mViews!!.add(mViews!!.size, textView)
            }
        })
        val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        mContentRootView!!.addView(bubbleTextView, lp)
        mViews!!.add(bubbleTextView)
        setCurrentEdit(bubbleTextView)
    }

    /**
     * 设置当前处于编辑模式的气泡
     */
    private fun setCurrentEdit(bubbleTextView: BubbleTextView) {
        if (mCurrentView != null) {
            mCurrentView!!.setInEdit(false)
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView!!.setInEdit(false)
        }
        mCurrentEditTextView = bubbleTextView
        mCurrentEditTextView!!.setInEdit(true)
    }

    private fun generateBitmap() {

        if (content.equals("")) {
            ToastUtil.showToast("请输入内容")
            return
        }

        val bitmap = Bitmap.createBitmap(mContentRootView!!.getWidth(),
                mContentRootView!!.getHeight(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        mContentRootView!!.draw(canvas)

        val iamgePath = FileUtilsFeng.saveBitmapToLocal(bitmap, this)
        val intent = Intent(this, WaterMarkActivity2::class.java)
        intent.putExtra("image", "" + iamgePath)
        intent.putExtra("content", "" + content)
        intent.putExtra("id", "" + pId)
        startActivity(intent)
    }

    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            SelectPictureUtil.selectPicture(this, 1, 0, false)
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要访问内存卡和拍照权限")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
//        if (requestCode == 0) {
//            // 图片、视频、音频选择结果回调
//            path = PictureSelector.obtainMultipleResult(data)[0].path
//            val bitmap = ImageFileUtil.getBitmapFromPath(PictureSelector.obtainMultipleResult(data)[0].compressPath)//压缩的路径
//            addStickerView(bitmap)
//        }
    }

}
