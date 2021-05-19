package com.kongqw.weibohelper.entity

class WeiBoError {
    var errorCode = 0
    var errorMessage: String? = null
    var errorDetail: String? = null

    override fun toString(): String {
        return "WeiBoError(errorCode=$errorCode, errorMessage=$errorMessage, errorDetail=$errorDetail)"
    }
}