package com.zhangwww.opengles_learning

import android.app.Application
import android.content.Context

class CommonApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {

        lateinit var appContext: Context

    }
}