package com.kongqw.weibo

import android.app.Application
import com.kongqw.weibohelper.WeiBoHelper

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // init Library
        // WeChatHelper.getInstance(applicationContext).init(BuildConfig.DEBUG)

        WeiBoHelper.init(applicationContext, true)
    }
}