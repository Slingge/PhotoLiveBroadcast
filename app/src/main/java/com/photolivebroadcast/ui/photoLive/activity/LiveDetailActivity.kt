package com.photolivebroadcast.ui.photoLive.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.MyApplication
import com.photolivebroadcast.ui.dialog.ProgressDialog
import com.photolivebroadcast.ui.mine.model.MySendModel
import com.photolivebroadcast.ui.photoLive.AlbumsClassificationModel
import com.photolivebroadcast.ui.photoLive.adapter.AlbumMenuAdapter
import com.photolivebroadcast.ui.photoLive.http.AlbumsClassificationHttp
import com.photolivebroadcast.util.RecyclerItemTouchListener
import kotlinx.android.synthetic.main.activity_live_detail.*
import kotlinx.android.synthetic.main.dialog_progress.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 相册详情
 * Created by zhf on 2018/9/11.
 */

class LiveDetailActivity : BaseActivity(), View.OnClickListener {

    private var pid = ""

    private var model: MySendModel.listalbumsModel? = null

    private var albumMenuAdapter: AlbumMenuAdapter? = null
    private var menuList = ArrayList<AlbumsClassificationModel.dataModel>()


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
        pid = model!!.id

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
                    ProgressDialog.showDialog(this@LiveDetailActivity)
                    AlbumsClassificationHttp.ClassificationAlbum(pid, menuList[i].id)
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

        ProgressDialog.showDialog(this)
        AlbumsClassificationHttp.Classification(pid)
    }


    @Subscribe
    fun onEvent(moddel: AlbumsClassificationModel) {
        menuList.addAll(moddel.data)
        albumMenuAdapter!!.notifyDataSetChanged()
        albumMenuAdapter!!.setFlag(0)
    }

    //分类下相册
    @Subscribe
    fun onEvent() {

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_fenlan -> {
                val bundle=Bundle()
                bundle.putString("id",pid)
                MyApplication.openActivity(this,ColumnActivity::class.java,bundle)
            }
            R.id.iv_phoneAlbum -> {
                val bundle=Bundle()
                bundle.putString("id",pid)
                MyApplication.openActivity(this,ColumnActivity::class.java,bundle)
            }
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}
