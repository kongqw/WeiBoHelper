package com.kongqw.weibohelper.listener

import com.kongqw.weibohelper.entity.WeiBoOauth2AccessToken

interface WeiBoAuthListener : WeiBoListener {

    fun onComplete(token: WeiBoOauth2AccessToken?)
}