package com.photolivebroadcast.ui.photoLive.activity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.media.browse.MediaBrowser
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.example.xrecyclerview.XRecyclerView
import com.google.gson.Gson

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.util.abLog
import com.luck.picture.lib.PictureSelector
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MainActivity
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

class LiveDetailActivity : BaseActivity(), View.OnClickListener, UpAlbumPhotoHttp.UpResultCallBack
        , AlbumsClassificationHttp.ClassificationCallBack, AlbumsClassificationHttp.ClassificationPhotoCallBack {


    private var pid = ""

    private var model: MySendModel.listalbumsModel? = null

    private var albumMenuAdapter: AlbumMenuAdapter? = null
    private var menuList = ArrayList<AlbumsClassificationModel.dataModel>()

    private var menuPhotoAdapter: ClassificationPhotoAdapter? = null
    private var menuPhotoList = ArrayList<ClassificationPhotoModel.recordsModel>()
    private var totalPage = 1//某分类总页数
    private var nowPage = 1//分类当前页数
    private var onRefresh = 0
    private var menuId = ""//分类id

    private var upimageList = ArrayList<String>()//相册选择要上传的图片


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_detail)
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
        ImageLoader.getInstance().displayImage(model!!.bgimage, bg_view)

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
                    menuPhotoList.clear()
                    albumMenuAdapter!!.notifyDataSetChanged()

                    nowPage = 1
                    menuId = menuList[i].id
                    ProgressDialog.showDialog(this@LiveDetailActivity)
                    AlbumsClassificationHttp.ClassificationAlbum(pid, menuId, this@LiveDetailActivity)
                    cv_1.visibility = View.GONE
                    cv_2.visibility = View.GONE
                    cv_3.visibility = View.GONE
                    rv_photo.visibility = View.VISIBLE
                    iv_fenlan.visibility = View.VISIBLE
                    iv_phoneAlbum.visibility = View.VISIBLE
                } else {
                    cv_1.visibility = View.VISIBLE
                    cv_2.visibility = View.VISIBLE
                    cv_3.visibility = View.VISIBLE
                    rv_photo.visibility = View.GONE
                    iv_fenlan.visibility = View.GONE
                    iv_phoneAlbum.visibility = View.GONE
                }
            }
        })

        val linearLayoutManager2 = GridLayoutManager(this, 3)
        rv_photo.layoutManager = linearLayoutManager2
        menuPhotoAdapter = ClassificationPhotoAdapter(this, menuPhotoList)
        rv_photo.adapter = menuPhotoAdapter
        rv_photo.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_photo) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                if (i < 0) {
                    return
                }
                val builder = android.support.v7.app.AlertDialog.Builder(this@LiveDetailActivity)
                builder.setItems(arrayOf("删除"/*, "排序"*/)) { dialog, which ->
                    dialog!!.dismiss()
                    if (which == 0) {
                        AlbumsClassificationHttp.delAlbumPhoto(this@LiveDetailActivity, menuPhotoList[i].id.toString(),
                                object : AlbumsClassificationHttp.DelAlbumPhotoCallBack {
                                    override fun del() {
                                        menuPhotoList.removeAt(i)
                                        menuPhotoAdapter!!.notifyDataSetChanged()
                                    }
                                })
                    }else if(which == 1){//排序

                    }
                }
                builder.show()
            }
        })
        /* rv_photo.setLoadingListener(object : XRecyclerView.LoadingListener {
             override fun onRefresh() {
                 if (menuPhotoList.isNotEmpty()) {
                     menuPhotoList.clear()
                     menuPhotoAdapter!!.notifyDataSetChanged()
                 }
                 nowPage = 1
                 onRefresh = 1
                 AlbumsClassificationHttp.ClassificationAlbum(pid, menuId,this@LiveDetailActivity)
             }

             override fun onLoadMore() {
                 nowPage++
                 if (totalPage >= menuPhotoList.size) {
                     return
                 }
                 onRefresh = 2
                 AlbumsClassificationHttp.ClassificationAlbum(pid, menuId,this@LiveDetailActivity)
             }
         })*/

        ProgressDialog.showDialog(this)
        AlbumsClassificationHttp.Classification2(pid, this)
    }


    override fun classifi(model: AlbumsClassificationModel) {
        if (menuList.isEmpty()) {
            val moddel = AlbumsClassificationModel.dataModel()
            moddel.id = ""
            moddel.menu_name = "活动概况"
            moddel.orderid = ""
            moddel.pid = pid
            menuList.add(moddel)
        }
        menuList.addAll(model.data)
        albumMenuAdapter!!.notifyDataSetChanged()
        albumMenuAdapter!!.setFlag(0)
    }

    //分类下相册
    override fun alnumPhoto(model: ClassificationPhotoModel.pageDataModel) {
        totalPage = model.total
        menuPhotoList.addAll(model.records)
        /*  if (onRefresh == 1) {
              rv_photo.refreshComplete()
          } else if (onRefresh == 2) {
              rv_photo.loadMoreComplete()
          }*/

        /*  if (totalPage >= menuPhotoList.size) {
              rv_photo.noMoreLoading()
          }*/

        menuPhotoAdapter!!.notifyDataSetChanged()
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
            abLog.e("路径...........", Gson().toJson(upimageList))
            val message = Message()
            message.what = 0
            hander.sendMessage(message)
        }
    }

    //上传相册图片
    override fun result(url: String) {
        if (!TextUtils.isEmpty(url)) {
            val model = ClassificationPhotoModel.recordsModel()
            model.imgurl = url
            menuPhotoList.add(model)
            menuPhotoAdapter!!.notifyDataSetChanged()

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


}
