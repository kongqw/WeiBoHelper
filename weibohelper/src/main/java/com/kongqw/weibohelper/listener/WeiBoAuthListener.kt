package com.kongqw.weibohelper.listener

import com.kongqw.weibohelper.entity.WeiBoError
import com.kongqw.weibohelper.entity.WeiBoOauth2AccessToken

interface WeiBoAuthListener {

    fun onLoadingStart()

    fun onLoadingEnd()

    fun onComplete(token: WeiBoOauth2AccessToken?)

    fun onError(error: WeiBoError?)

    fun onCancel()
}