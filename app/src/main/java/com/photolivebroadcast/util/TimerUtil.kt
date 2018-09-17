package com.photolivebroadcast.util


import android.os.CountDownTimer
import android.widget.TextView

import java.util.Random

/**
 * 获取验证码倒计时
 * Created by Administrator on 2016/8/19 0019.
 */
object TimerUtil {


    var timer: CountDownTimer? = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
//            tv!!.isEnabled = false
//            tv.text = (millisUntilFinished / 1000).toString() + "秒重新获取"
        }

        override fun onFinish() {
//            tv!!.isEnabled = true
//            tv.text = "获取验证码"
        }
    }


    /**
     * 获取随机验证码
     */
    //随机生成6位数  发送到聚合
    fun getCode(): String {
        val sb = StringBuilder()
        val random = Random()
        for (i in 0..3) {
            val a = random.nextInt(10)
            sb.append(a)
        }
        return sb.toString()
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
