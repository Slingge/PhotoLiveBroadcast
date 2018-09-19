package com.photolivebroadcast.util

import java.util.*

/**
 * Created by Slingge on 2018/9/18.
 */
object GetCodeUtil {


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


}