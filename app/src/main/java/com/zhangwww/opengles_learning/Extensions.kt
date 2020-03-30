package com.zhangwww.opengles_learning

import android.app.Activity
import android.widget.Toast

fun Activity.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, length).show()
}