package com.zhangwww.opengles_learning.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.zhangwww.opengles_learning.CommonApplication

fun Activity.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, length).show()
}

val Any.appContext: Context
    get() = CommonApplication.appContext