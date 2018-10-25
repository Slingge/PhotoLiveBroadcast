package com.photolivebroadcast.ui.photoLive.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.constant.USBReceiverConstant.READ_USB_DEVICE_PERMISSION
import com.photolivebroadcast.ui.dialog.PermissionsDialog
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.photoLive.AlbumsClassificationModel
import com.photolivebroadcast.ui.photoLive.adapter.PhoneAlbumAdapter
import com.photolivebroadcast.ui.photoLive.http.AlbumsClassificationHttp
import com.photolivebroadcast.ui.photoLive.http.UpAlbumPhotoHttp
import com.photolivebroadcast.ui.photoLive.model.UpAlbunmModel
import com.photolivebroadcast.ui.photoLive.mtp.CompressUtils
import com.photolivebroadcast.ui.photoLive.mtp.Constant
import com.photolivebroadcast.ui.photoLive.mtp.PicInfo
import com.photolivebroadcast.ui.photoLive.mtp.USBMTPReceiver
import com.photolivebroadcast.util.ImageFileUtil
import com.photolivebroadcast.util.RecyclerItemTouchListener
import kotlinx.android.synthetic.main.activity_phone_album.*
import io.reactivex.functions.Consumer
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2018/9/29.
 */
class CloudSeedingActivity : BaseActivity(), Consumer<List<*>>, UpAlbumPhotoHttp.UpResultCallBack
        , View.OnClickListener {

    private var phoneAlbumAdapter: PhoneAlbumAdapter? = null
    private var phoneList = ArrayList<UpAlbunmModel>()
    private var linearLayoutManager: LinearLayoutManager? = null

//    private var mService: MTPService? = null

    //USB MTP 设备监控广播
    private var usbmtpReceiver: USBMTPReceiver? = null;

    private var upSuccessNum = 0//上传成功数量
    private var upfailNum = 0//上传失败数量
    private var subscript = 0//上传到数量的下标

    private var pid = ""
    private var classificationId = ""//相册分类id
    private var classificationList = ArrayList<AlbumsClassificationModel.dataModel>()

    private var upType = ""//上传方式
    private var upSize = "原图"//上传大小

    private var isUp = false//是否在上传

    private var adoptNum = 0//设置通过、不通过的照片下标

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_album)
        //注册USB设备广播
        registerUSBReceiver()

        sendUSBBroadcast()

        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("云直播")
        StatusBarWhiteColor()
        pid = intent.getStringExtra("id")
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager!!.orientation = LinearLayoutManager.HORIZONTAL

        rv_album.layoutManager = linearLayoutManager
        phoneAlbumAdapter = PhoneAlbumAdapter(this, phoneList)
        rv_album.adapter = phoneAlbumAdapter

        rv_album.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_album) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {

                adoptNum = vh!!.adapterPosition

                bg.setImageBitmap(ImageFileUtil.getBitmapFromPath(phoneList[adoptNum].path))
                linearLayoutManager!!.scrollToPositionWithOffset(adoptNum, 0)
                linearLayoutManager!!.stackFromEnd = true
            }
        })

        sp_classification.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                classificationId = classificationList[position].id
            }
        }

//        mService = MTPService(this)

        ProgressDialog.showDialog(this)
        AlbumsClassificationHttp.Classification2(pid, object : AlbumsClassificationHttp.ClassificationCallBack {
            override fun classifi(model: AlbumsClassificationModel) {
                val strList1 = ArrayList<String>()
                for (i in 0 until model.data.size) {
                    strList1.add(model.data[i].menu_name)
                }
                classificationList = model.data
                if (classificationList == null || classificationList.size == 0) {
                    return
                }
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


    @Subscribe
    fun onEvent(list: List<PicInfo>) {

    }

    @Throws(Exception::class)
    override fun accept(t: List<*>) {
        var list = t as ArrayList<PicInfo>
        imageView4.visibility = View.VISIBLE
        tv_deviceName.text = Constant.usbDeviceName
        bg.setImageBitmap(ImageFileUtil.getBitmapFromPath(list[0].getmThumbnailPath()))
        for (i in 0 until list.size) {
            var thumpPicPath = list[i].getmThumbnailPath();
//            // 指定拍照意图
//            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            // 加载路径图片路径
//            Uri mUri = Uri.fromFile(new File(thumpPicPath));
//            // 指定存储路径，这样就可以保存原图了
//            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
//            startActivityForResult(openCameraIntent, TAKE_PICTURE);

            val album = UpAlbunmModel(thumpPicPath, -1)
            phoneList.add(album)
        }

        tv_upSpeed.text = "上传进度：" + upSuccessNum.toString() + "/" + phoneList.size
        tv_upFail.text = "上传失败：" + upfailNum.toString() + "/" + phoneList.size

//        val message = Message()
//        message.what = 0
//        hander.sendMessage(message)
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

        tv_upSpeed.text = "上传进度：" + upSuccessNum.toString() + "/" + phoneList.size
        tv_upFail.text = "上传失败：" + upfailNum.toString() + "/" + phoneList.size
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_adopt -> {
                if (phoneList.isEmpty()) {
                    return
                }
                if (phoneList[adoptNum].isAdopt == 1) {
                    ToastUtil.showToast("已经不通过")
                    return
                }
                if (phoneList[adoptNum].isAdopt == 0) {
                    ToastUtil.showToast("已通过")
                    return
                }
                phoneList[adoptNum].isAdopt = 0
                phoneAlbumAdapter!!.notifyDataSetChanged()
                ProgressDialog.showDialog(this@CloudSeedingActivity)
                var img = ""
                if (upSize.equals("原图")) {
                    img = phoneList[subscript].path
                } else if (upSize.equals("3-5M")) {
                    img = CompressUtils.compressImage(this@CloudSeedingActivity, ImageFileUtil.getBitmapFromPath(phoneList[subscript].path), 4000)
                } else if (upSize.equals("1M")) {
                    img = CompressUtils.compressImage(this@CloudSeedingActivity, ImageFileUtil.getBitmapFromPath(phoneList[subscript].path), 1000)
                }
                UpAlbumPhotoHttp.upPhoto(pid, classificationId, img, this@CloudSeedingActivity)
            }
            R.id.tv_through -> {
                if (phoneList.isEmpty()) {
                    return
                }
                if (phoneList[adoptNum].isAdopt == 1) {
                    ToastUtil.showToast("已经不通过")
                    return
                }
                if (phoneList[adoptNum].isAdopt == 0) {
                    ToastUtil.showToast("已通过")
                    return
                }
                phoneList[adoptNum].isAdopt = 1
                phoneAlbumAdapter!!.notifyDataSetChanged()
                adoptNum++
                linearLayoutManager!!.scrollToPositionWithOffset(adoptNum, 0)
                linearLayoutManager!!.stackFromEnd = true
            }
        }
    }


    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
//            mService = MTPService(this)
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要访问内存卡权限")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
//        mService!!.close()
        EventBus.getDefault().unregister(this)
        if (usbmtpReceiver != null) {
            //取消注册USB设备广播
            unregisterReceiver(usbmtpReceiver)
        }
    }

    //发送USB广播
    private fun sendUSBBroadcast() {
        //发送广播
        val intent = Intent(READ_USB_DEVICE_PERMISSION)
        //发送标准广播
        sendBroadcast(intent)
    }

    /*****
     * 动态注册USB 设备监听
     */
    private fun registerUSBReceiver() {
        val intentFilter = IntentFilter()
        //自定义USB设备读取照片
        intentFilter.addAction(READ_USB_DEVICE_PERMISSION)
        //USB连接状态发生变化时产生的广播
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        val usbmtpReceiver = USBMTPReceiver()
        registerReceiver(usbmtpReceiver, intentFilter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            // 图片、视频、音频选择结果回调
            for (i in 0 until PictureSelector.obtainMultipleResult(data).size) {
                val album = UpAlbunmModel((PictureSelector.obtainMultipleResult(data)[i].path), -1)
                phoneList.add(album)
            }
            phoneAlbumAdapter!!.notifyDataSetChanged()
        }
    }


}