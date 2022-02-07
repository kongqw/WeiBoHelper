package com.kongqw.weibohelper.util

import android.util.Log
import com.kongqw.weibohelper.WeiBoHelper

internal object Logger {

    fun i(tag: String, message: String?) {
        if (WeiBoHelper.isLoggable) {
            Log.i(tag, message ?: "")
        }
    }
}