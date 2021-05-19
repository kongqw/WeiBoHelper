package com.kongqw.weibohelper.entity

class WeiBoOauth2AccessToken {

    val mUid: String? = null
    val mScreenName: String? = null
    val mAccessToken: String? = null
    val mRefreshToken: String? = null
    val mExpiresTime: Long = 0

    override fun toString(): String {
        return "WeiBoOauth2AccessToken(mUid=$mUid, mScreenName=$mScreenName, mAccessToken=$mAccessToken, mRefreshToken=$mRefreshToken, mExpiresTime=$mExpiresTime)"
    }
}