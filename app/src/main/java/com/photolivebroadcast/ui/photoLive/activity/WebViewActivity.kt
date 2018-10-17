package com.photolivebroadcast.ui.photoLive.activity

import android.os.Bundle
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.photolivebroadcast.R
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * Created by Slingge on 2018/10/17.
 */
class WebViewActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        init()
    }


    private fun init() {
        ToastUtil.showToast(intent.getStringExtra("url"))
        webView.loadUrl(intent.getStringExtra("url"))
    }


}