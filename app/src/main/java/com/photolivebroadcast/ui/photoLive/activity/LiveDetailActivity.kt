package com.photolivebroadcast.ui.photoLive.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.example.xrecyclerview.XRecyclerView

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.model.MySendModel
import com.photolivebroadcast.ui.photoLive.AlbumsClassificationModel
import com.photolivebroadcast.ui.photoLive.adapter.AlbumMenuAdapter
import com.photolivebroadcast.ui.photoLive.adapter.ClassificationPhotoAdapter
import com.photolivebroadcast.ui.photoLive.http.AlbumsClassificationHttp
import com.photolivebroadcast.ui.photoLive.http.UpAlbumPhotoHttp
import com.photolivebroadcast.ui.photoLive.model.ClassificationPhotoModel
import com.photolivebroadcast.util.ImageFileUtil
import com.photolivebroadcast.util.RecyclerItemTouchListener
import kotlinx.android.synthetic.main.activity_live_detail.*
import kotlinx.android.synthetic.main.activity_personal_info.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 相册详情
 * Created by zhf on 2018/9/11.
 */

class LiveDetailActivity : BaseActivity(), View.OnClickListener, UpAlbumPhotoHttp.UpResultCallBack {

    private var pid = ""

    private var model: MySendModel.listalbumsModel? = null

    private var albumMenuAdapter: AlbumMenuAdapter? = null
    private var menuList = ArrayList<AlbumsClassificationModel.dataModel>()

    private var menuPhoto: ClassificationPhotoAdapter? = null
    private var menuPhotoList = ArrayList<ClassificationPhotoModel.recordsModel>()
    private var totalPage = 1//某分类总页数
    private var nowPage = 1//分类当前页数
    private var onRefresh = 0
    private var menuId = ""//分类id

    private var upimageList = ArrayList<String>()//相册选择要上传的图片


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_detail)
        EventBus.getDefault().register(this)
        init()
    }

    private fun init() {
        StatusBarWhiteColor()
        if (intent == null) {
            return
        }
        model = intent.getSerializableExtra("model") as MySendModel.listalbumsModel
        inittitle(model!!.title)
        pid = model!!.id.toString()

        tv_time.text = model!!.start_time + "-" + model!!.end_time
        tv_address.text = model!!.address

        tv_details.text = model!!.remark
        tv_process.text = model!!.event_liucheng

        iv_fenlan.setOnClickListener(this)
        iv_phoneAlbum.setOnClickListener(this)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_menu.layoutManager = linearLayoutManager
        albumMenuAdapter = AlbumMenuAdapter(this, menuList)
        rv_menu.adapter = albumMenuAdapter

        rv_menu.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_menu) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                if (i < 0 || i >= menuList.size) {
                    return
                }
                albumMenuAdapter!!.setFlag(i)
                if (i != 0) {
                    if (menuPhotoList.isNotEmpty()) {
                        menuPhotoList.clear()
                        albumMenuAdapter!!.notifyDataSetChanged()
                    }
                    nowPage = 1
                    menuId = menuList[i].id
                    ProgressDialog.showDialog(this@LiveDetailActivity)
                    AlbumsClassificationHttp.ClassificationAlbum(pid, menuId)
                    cv_1.visibility = View.GONE
                    cv_2.visibility = View.GONE
                    cv_3.visibility = View.GONE
                    iv_fenlan.visibility = View.VISIBLE
                    iv_phoneAlbum.visibility = View.VISIBLE
                } else {
                    cv_1.visibility = View.VISIBLE
                    cv_2.visibility = View.VISIBLE
                    cv_3.visibility = View.VISIBLE
                    iv_fenlan.visibility = View.GONE
                    iv_phoneAlbum.visibility = View.GONE
                }
            }
        })

        val linearLayoutManager2 = LinearLayoutManager(this)
        linearLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        rv_photo.layoutManager = linearLayoutManager2
        menuPhoto = ClassificationPhotoAdapter(this, menuPhotoList)
        rv_photo.adapter = albumMenuAdapter
        rv_photo.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                if (menuPhotoList.isNotEmpty()) {
                    menuPhotoList.clear()
                    albumMenuAdapter!!.notifyDataSetChanged()
                }
                nowPage = 1
                onRefresh = 1
                AlbumsClassificationHttp.ClassificationAlbum(pid, menuId)
            }

            override fun onLoadMore() {
                nowPage++
                if (totalPage >= nowPage) {
                    rv_photo.noMoreLoading()
                    return
                }
                onRefresh = 2
                AlbumsClassificationHttp.ClassificationAlbum(pid, menuId)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        if (menuList.isEmpty()) {
            ProgressDialog.showDialog(this)
            AlbumsClassificationHttp.Classification(pid)
        }
    }


    @Subscribe
    fun onEvent(moddel: AlbumsClassificationModel) {
        if (menuList.isEmpty()) {
            val moddel = AlbumsClassificationModel.dataModel()
            moddel.id = ""
            moddel.menu_name = "活动概况"
            moddel.orderid = ""
            moddel.pid = pid
            menuList.add(moddel)
        }
        menuList.addAll(moddel.data)
        albumMenuAdapter!!.notifyDataSetChanged()
        albumMenuAdapter!!.setFlag(0)
    }

    //分类下相册
    @Subscribe
    fun onEvent(moddel: ClassificationPhotoModel.pageDataModel) {
        totalPage = moddel.total
        menuPhotoList.addAll(moddel.records)
        if (onRefresh == 1) {
            rv_photo.refreshComplete()
        } else if (onRefresh == 2) {
            rv_photo.loadMoreComplete()
        }
        rv_photo.noMoreLoading()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_fenlan -> {
                val bundle = Bundle()
                bundle.putString("id", pid)
                MyApplication.openActivity(this, ColumnActivity::class.java, bundle)
            }
            R.id.iv_phoneAlbum -> {
                SelectPictureUtil.selectPicture(this, 999, 0, false)
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
            for (i in 0 until PictureSelector.obtainMultipleResult(data).size) {
                upimageList.add((PictureSelector.obtainMultipleResult(data)[i].path))
            }

            val message = Message()
            message.what = 0
            hander.sendMessage(message)
        }
    }

    override fun result(result: Boolean) {
        if (result) {
            upNow++
            val message = Message()
            message.what = 0
            hander.sendMessage(message)
        }
    }

    private var upNow = 0//上传到哪张图片
    private val hander = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg!!.what == 0) {//上传开始
                if (upNow >= upimageList.size) {
                    ToastUtil.showToast("上传完成")
                    return
                }
                if (upNow >= upimageList.size) {
                    return
                }
                ProgressDialog.showDialog(this@LiveDetailActivity)
                UpAlbumPhotoHttp.upPhoto(pid, menuId, upimageList[upNow], this@LiveDetailActivity)
            }
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}
