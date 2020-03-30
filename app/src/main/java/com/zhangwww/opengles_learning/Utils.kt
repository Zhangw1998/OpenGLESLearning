package com.zhangwww.opengles_learning

import android.app.ActivityManager
import android.content.Context

/**
 * 判断是否支持OpenGLES2.0
 */
private fun isSupportOpenGLes20(context: Context): Boolean {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val configurationInfo = activityManager.deviceConfigurationInfo
    return configurationInfo.reqGlEsVersion >= 0x20000
}