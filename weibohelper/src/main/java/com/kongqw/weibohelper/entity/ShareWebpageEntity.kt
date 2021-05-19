package com.kongqw.weibohelper.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ShareWebpageEntity(
    /**
     * 分享网页的标题
     */
    @SerializedName("title")
    val title: String? = null,

    /**
     * 分享网页的描述
     */
    @SerializedName("description")
    val description: String? = null,

    /**
     * 分享网页的预览图
     */
    @SerializedName("thumb")
    val thumb: String? = null,

    /**
     * 分享网页的链接
     */
    @SerializedName("actionUrl")
    val actionUrl: String? = null
) : Serializable {
    override fun toString(): String {
        return "ShareWebpageEntity(title=$title, description=$description, thumb=$thumb, actionUrl=$actionUrl)"
    }
}