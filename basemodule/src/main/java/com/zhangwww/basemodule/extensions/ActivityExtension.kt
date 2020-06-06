package com.zhangwww.basemodule.extensions

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes

fun Activity.toast(msg: String) {
    if (!isFinishing) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

fun Activity.toast(@StringRes msgId: Int) {
    if (!isFinishing) {
        Toast.makeText(this, getString(msgId), Toast.LENGTH_SHORT).show()
    }
}

fun Activity.toastLong(msg: String) {
    if (!isFinishing) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}

fun Activity.toastLong(@StringRes msgId: Int) {
    if (!isFinishing) {
        Toast.makeText(this, getString(msgId), Toast.LENGTH_LONG).show()
    }
}