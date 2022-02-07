package com.kongqw.weibohelper.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WeiBoOauth2AccessToken : Serializable {

    @SerializedName("mUid")
    val mUid: String? = null

    @SerializedName("mScreenName")
    val mScreenName: String? = null

    @SerializedName("mAccessToken")
    val mAccessToken: String? = null

    @SerializedName("mRefreshToken")
    val mRefreshToken: String? = null

    @SerializedName("mExpiresTime")
    val mExpiresTime: Long = 0

    override fun toString(): String {
        return "WeiBoOauth2AccessToken(mUid=$mUid, mScreenName=$mScreenName, mAccessToken=$mAccessToken, mRefreshToken=$mRefreshToken, mExpiresTime=$mExpiresTime)"
    }
}