package com.zhangwww.basemodule.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(msg: String) {
    if (isVisible) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.toast(@StringRes msgId: Int) {
    if (isVisible) {
        Toast.makeText(requireActivity(), getString(msgId), Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.toastLong(msg: String) {
    if (isVisible) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()
    }
}

fun Fragment.toastLong(@StringRes msgId: Int) {
    if (isVisible) {
        Toast.makeText(requireActivity(), getString(msgId), Toast.LENGTH_LONG).show()
    }
}