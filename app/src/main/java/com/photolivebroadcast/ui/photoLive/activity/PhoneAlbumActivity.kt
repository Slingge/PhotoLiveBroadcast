package com.photolivebroadcast.ui.photoLive.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.photolivebroadcast.R
import com.photolivebroadcast.ui.photoLive.adapter.PhoneAlbumAdapter
import com.photolivebroadcast.ui.photoLive.mtp.MTPService
import kotlinx.android.synthetic.main.activity_phone_album.*
import io.reactivex.functions.Consumer

/**
 * Created by Slingge on 2018/9/29.
 */
class PhoneAlbumActivity : BaseActivity(), Consumer<List<*>> {

    private var phoneAlbumAdapter: PhoneAlbumAdapter? = null
    private var phoneList = ArrayList<String>()

    var mService: MTPService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_album)
        init()
    }


    private fun init() {
        inittitle("云直播")

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rv_album.layoutManager = linearLayoutManager
        phoneAlbumAdapter = PhoneAlbumAdapter(this, phoneList)
        rv_album.adapter = phoneAlbumAdapter


        mService = MTPService(this)


    }


    @Throws(Exception::class)
    override fun accept(list: List<*>) {
        for (i in 0 until list.size) {
            if (!phoneList.contains(list[i])) {
                phoneList.add(list[i] as String)
            }
        }

    }


    private fun permissioncheck() {
        if (!checkPermissionAllGranted(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
            )
        }
    }

    private fun checkPermissionAllGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false
            }
        }
        return true
    }


    override fun onDestroy() {
        super.onDestroy()
        mService!!.close()
    }


}