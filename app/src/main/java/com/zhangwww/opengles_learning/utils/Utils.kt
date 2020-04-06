package com.zhangwww.opengles_learning.utils

import android.app.ActivityManager
import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.opengl.GLES20.*
import android.opengl.GLUtils.texImage2D
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

private const val TAG = "Utils"

const val BYTES_PER_FLOAT = 4

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

/**
 * 加载Resources图像文件到纹理中
 */
fun loadTexture(context: Context, resourceId: Int): Int {
    val textureObjectId = IntArray(1)
    glGenTextures(1, textureObjectId, 0)
    if (textureObjectId[0] == 0) {
        Log.w(TAG, "Could not generate a new OpenGL texture object")
        return 0
    }
    val options = BitmapFactory.Options()
    options.inScaled = false
    val bitmap = BitmapFactory.decodeResource(context.resources, resourceId, options)
    if (bitmap == null) {
        Log.w(TAG, "Resource ID $resourceId could not be decoded")
        glDeleteTextures(1, textureObjectId, 0)
        return 0
    }
    glBindTexture(GL_TEXTURE_2D, textureObjectId[0])
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
    texImage2D(GL_TEXTURE_2D, 0, bitmap, 0)
    bitmap.recycle()
    glGenerateMipmap(GL_TEXTURE_2D)
    glBindTexture(GL_TEXTURE_2D, 0)
    return textureObjectId[0]
}

