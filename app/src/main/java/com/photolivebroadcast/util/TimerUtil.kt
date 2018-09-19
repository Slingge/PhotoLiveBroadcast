package com.photolivebroadcast.util


import android.os.CountDownTimer
import android.widget.TextView

import java.util.Random

/**
 * 获取验证码倒计时
 * Created by Administrator on 2016/8/19 0019.
 */
class TimerUtil(tv: TextView) {


    var timer: CountDownTimer? = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            tv.isEnabled = false
            tv.text = (millisUntilFinished / 1000).toString() + "秒重新获取"
        }

        override fun onFinish() {
            tv.isEnabled = true
            tv.text = "获取验证码"
        }
    }





    fun timersStart() {
        timer!!.start()
    }

    fun cancelTimer() {
        if (timer != null) {
            timer!!.cancel()
        }
    }


}
