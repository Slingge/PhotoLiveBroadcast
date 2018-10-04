package com.photolivebroadcast.ui.photoLive.activity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.dialog.PermissionsDialog
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.photoLive.AlbumsClassificationModel
import com.photolivebroadcast.ui.photoLive.adapter.PhoneAlbumAdapter
import com.photolivebroadcast.ui.photoLive.http.AlbumsClassificationHttp
import com.photolivebroadcast.ui.photoLive.http.UpAlbumPhotoHttp
import com.photolivebroadcast.ui.photoLive.model.UpAlbunmModel
import com.photolivebroadcast.ui.photoLive.mtp.MTPService
import com.photolivebroadcast.util.PermissionUtil
import kotlinx.android.synthetic.main.activity_phone_album.*
import io.reactivex.functions.Consumer

/**
 * Created by Slingge on 2018/9/29.
 */
class CloudSeedingActivity : BaseActivity(), Consumer<List<*>>, UpAlbumPhotoHttp.UpResultCallBack
,View.OnClickListener{

    private var phoneAlbumAdapter: PhoneAlbumAdapter? = null
    private var phoneList = ArrayList<UpAlbunmModel>()

    var mService: MTPService? = null

    private var upSuccessNum = 0//上传成功数量
    private var upfailNum = 0//上传失败数量
    private var subscript = 0//上传到数量的下标

    private var pid = ""
    private var classificationId = ""//相册分类id
    private var classificationList = ArrayList<AlbumsClassificationModel.dataModel>()

    private var upType = ""//上传方式
    private var upSize = ""//上传大小

    private var isUp = false//是否在上传

    private var adoptNum=0//设置通过、不通过的照片下标

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_album)
        init()
    }


    private fun init() {
        inittitle("云直播")
        StatusBarWhiteColor()
        pid = intent.getStringExtra("id")
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rv_album.layoutManager = linearLayoutManager
        phoneAlbumAdapter = PhoneAlbumAdapter(this, phoneList)
        rv_album.adapter = phoneAlbumAdapter

        sp_classification.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                classificationId = classificationList[position].id
                if (!isUp) {
                    val message = Message()
                    message.what = 0
                    hander.sendMessage(message)
                }
            }
        }


        if (PermissionUtil.ApplyPermissionAlbum(this, 0)) {
            mService = MTPService(this)
        }

        ProgressDialog.showDialog(this)
        AlbumsClassificationHttp.Classification2(pid, object : AlbumsClassificationHttp.ClassificationCallBack {
            override fun classifi(model: AlbumsClassificationModel) {
                val strList1 = ArrayList<String>()
                for (i in 0 until model.data.size) {
                    strList1.add(model.data[i].menu_name)
                }
                classificationList = model.data
                val spinnerAdapter = ArrayAdapter<String>(this@CloudSeedingActivity,
                        R.layout.item_spinner_text, strList1)
                sp_classification.adapter = spinnerAdapter
                classificationId = classificationList[0].id
            }
        })


        //上传方式
        val upTypeLIst = ArrayList<String>()
        upTypeLIst.add("手动上传")
        upTypeLIst.add("边拍边传")
        val typeAdapter = ArrayAdapter<String>(this@CloudSeedingActivity,
                R.layout.item_spinner_text, upTypeLIst)
        sp_upType.adapter = typeAdapter
        sp_upType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                upType = upTypeLIst[position]
            }
        }
        //图片大小
        val upSizeLIst = ArrayList<String>()
        upSizeLIst.add("原图")
        upSizeLIst.add("3-5M")
        upSizeLIst.add("1M")
        val sizeAdapter = ArrayAdapter<String>(this@CloudSeedingActivity,
                R.layout.item_spinner_text, upSizeLIst)
        sp_size.adapter = sizeAdapter
        sp_size.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                upSize = upSizeLIst[position]
            }
        }

        tv_adopt.setOnClickListener(this)
        tv_through.setOnClickListener(this)
    }


    @Throws(Exception::class)
    override fun accept(list: List<*>) {
        imageView4.visibility = View.VISIBLE
        for (i in 0 until list.size) {
            if (!phoneList.contains(list[i])) {
                val album = UpAlbunmModel(list[i] as String, -1)
                phoneList.add(album)
            }
        }
        phoneAlbumAdapter!!.notifyDataSetChanged()
        tv_upSpeed.text = upSuccessNum.toString() + "/" + phoneList.size
        tv_upFail.text = upfailNum.toString() + "/" + phoneList.size

        val message = Message()
        message.what = 0
        hander.sendMessage(message)
    }


    private val hander = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg!!.what == 0) {//上传开始
                if (TextUtils.isEmpty(classificationId)) {
                    ToastUtil.showToast("请选择相册分类")
                    return
                }
                if (!isUp) {
                    isUp = true
                }
                if (subscript >= phoneList.size) {//上传完成
                    ToastUtil.showToast("上传完成")
                    return
                }
                if (phoneList.isNotEmpty()) {
                    ProgressDialog.showDialog(this@CloudSeedingActivity)
                    UpAlbumPhotoHttp.upPhoto(pid, classificationId, phoneList[subscript].path, this@CloudSeedingActivity)
                }
            }
        }
    }


    override fun result(url: String) {
        if (!TextUtils.isEmpty(url)) {
            upSuccessNum++
        } else {
            upfailNum++
        }
        subscript++

        tv_upSpeed.text = upSuccessNum.toString() + "/" + phoneList.size
        tv_upFail.text = upfailNum.toString() + "/" + phoneList.size

        val message = Message()
        message.what = 0
        hander.sendMessage(message)
    }


    override fun onClick(v: View?) {

    }



    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            mService = MTPService(this)
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要访问内存卡权限")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mService!!.close()
    }


}