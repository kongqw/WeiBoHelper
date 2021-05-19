package com.kongqw.weibohelper.util

import com.kongqw.weibohelper.entity.WeiBoError
import com.kongqw.weibohelper.entity.WeiBoOauth2AccessToken
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.common.UiError

internal object TransferUtils {

    fun uiErrorToWeiBoError(uiError: UiError?): WeiBoError? {
        return try {
            val toJson = GsonUtils.toJson(uiError)
            GsonUtils.fromJson(toJson, WeiBoError::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun oauth2AccessTokenToWeiBoOauth2AccessToken(oauth2AccessToken: Oauth2AccessToken?): WeiBoOauth2AccessToken? {
        return try {
            val toJson = GsonUtils.toJson(oauth2AccessToken)
            GsonUtils.fromJson(toJson, WeiBoOauth2AccessToken::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}