package com.zhangwww.opengles_learning.utils

import android.app.ActivityManager
import android.content.Context
import android.content.res.Resources
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.RuntimeException

/**
 * 判断是否支持OpenGLES2.0
 */
fun isSupportOpenGLes20(context: Context): Boolean {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val configurationInfo = activityManager.deviceConfigurationInfo
    return configurationInfo.reqGlEsVersion >= 0x20000
}

/**
 * 从raw目录读取shader文件
 */
fun readShaderFromResource(context: Context, resourceId: Int): String {
    val body = StringBuilder()
    try {
        val inputStream = context.resources.openRawResource(resourceId)
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var nextLine: String? = ""
        while (nextLine != null) {
            body.append(nextLine).append('\n')
            nextLine = bufferedReader.readLine()
        }
    } catch (e: IOException) {
        throw RuntimeException("Could not open resource: $resourceId", e)
    } catch (e: Resources.NotFoundException) {
        throw RuntimeException("Resource not found: $resourceId", e)
    }
    Log.e("zhang", body.toString())
    return body.toString()
}

