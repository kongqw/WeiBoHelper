package com.kongqw.weibohelper.listener

import com.kongqw.weibohelper.entity.WeiBoError

interface WeiBoListener {

    fun onLoadingStart()

    fun onLoadingEnd()

    fun onNotInstall()

    fun onCancel()

    fun onError(error: WeiBoError?)
}