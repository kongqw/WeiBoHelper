package com.kongqw.weibohelper.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WeiBoError : Serializable {

    @SerializedName("errorCode")
    var errorCode = 0

    @SerializedName("errorMessage")
    var errorMessage: String? = null

    @SerializedName("errorDetail")
    var errorDetail: String? = null

    override fun toString(): String {
        return "WeiBoError(errorCode=$errorCode, errorMessage=$errorMessage, errorDetail=$errorDetail)"
    }
}