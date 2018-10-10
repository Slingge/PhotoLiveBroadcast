package com.photolivebroadcast.ui.dialog

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.photolivebroadcast.R
import com.photolivebroadcast.util.GetDateTimeUtil

import com.weigan.loopview.LoopView

import java.util.ArrayList

/**
 * 选择相册服务数量
 * Created by Slingge on 2018/1/17 0017.
 */

class NumPop(context: Context, var wheelViewCallBack: WheelViewCallBack2) : PopupWindow(context) {

    internal var position = 0


    private val numList = ArrayList<String>()

    interface WheelViewCallBack2 {
        fun position(position1: String)
    }


    init {
        val v = LayoutInflater.from(context).inflate(R.layout.popup_address, null)
        val loopview = v.findViewById<View>(R.id.loopView) as LoopView
        val loopview2 = v.findViewById<View>(R.id.loopView2) as LoopView
        loopview2.visibility = View.GONE
        val loopview3 = v.findViewById<View>(R.id.loopView3) as LoopView
        loopview3.visibility = View.GONE
        loopview.setTextSize(16f)
        loopview2.setTextSize(16f)
        loopview3.setTextSize(16f)

        v.findViewById<View>(R.id.year).visibility = View.GONE
        v.findViewById<View>(R.id.month).visibility = View.GONE
        v.findViewById<View>(R.id.day).visibility = View.GONE


        //设置是否循环播放
        loopview.setNotLoop();
        //滚动监听
        //设置原始数据

        if (numList.isEmpty()) {
            getYear()
        }

        loopview.setItems(numList)
        loopview.setInitPosition(0)

        wheelViewCallBack.position(numList[0])

        loopview.setListener { index ->
            wheelViewCallBack.position(numList[index])
        }


        val tv_enter = v.findViewById<View>(R.id.tv_enter) as TextView
        tv_enter.setOnClickListener { v1 -> this@NumPop.dismiss() }

        //设置初始位置


        this.contentView = v
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        // 设置SelectPicPopupWindow弹出窗体的高
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.animationStyle = android.R.style.Animation_InputMethod
        this.isFocusable = true
        //		this.setOutsideTouchable(true);
        this.setBackgroundDrawable(BitmapDrawable())
        this.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
    }


    private fun getYear() {
        numList.clear()

        numList.add("300以内")
        numList.add("300-600")
        numList.add("600-900")

    }


}
