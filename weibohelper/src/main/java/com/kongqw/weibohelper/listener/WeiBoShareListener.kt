package com.kongqw.weibohelper.listener

import com.kongqw.weibohelper.entity.WeiBoError

interface WeiBoShareListener {

    fun onLoadingStart()

    fun onLoadingEnd()

    fun onComplete()

    fun onError(error: WeiBoError?)

    fun onCancel()
}