package com.kongqw.weibohelper

import android.content.Context
import com.kongqw.weibohelper.entity.ShareWebpageEntity
import com.kongqw.weibohelper.listener.WeiBoAuthListener
import com.kongqw.weibohelper.listener.WeiBoShareListener
import com.kongqw.weibohelper.util.AppUtils
import com.kongqw.weibohelper.util.TransferUtils
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.common.UiError
import com.sina.weibo.sdk.share.WbShareCallback

object WeiBoHelper {

    lateinit var applicationContext: Context
    var isLoggable = false

    var isRegistered = false

    fun init(context: Context, isLoggable: Boolean): Boolean {
        this.applicationContext = context
        this.isLoggable = isLoggable
        return true
    }

    val mWbAuthListener = object : WbAuthListener {
        override fun onComplete(p0: Oauth2AccessToken?) {
            mWeiBoAuthListener?.onComplete(TransferUtils.oauth2AccessTokenToWeiBoOauth2AccessToken(p0))
        }

        override fun onError(p0: UiError?) {
            mWeiBoAuthListener?.onError(TransferUtils.uiErrorToWeiBoError(p0))
        }

        override fun onCancel() {
            mWeiBoAuthListener?.onCancel()
        }
    }

    val mWbShareCallback = object : WbShareCallback {
        override fun onComplete() {
            mWeiBoShareListener?.onComplete()
        }

        override fun onError(p0: UiError?) {
            mWeiBoShareListener?.onError(TransferUtils.uiErrorToWeiBoError(p0))
        }

        override fun onCancel() {
            mWeiBoShareListener?.onCancel()
        }

    }

    var mWeiBoAuthListener: WeiBoAuthListener? = null
    var mWeiBoShareListener: WeiBoShareListener? = null

    /**
     * 授权
     * @param listener 授权回调
     */
    fun startAuth(listener: WeiBoAuthListener? = null) {
        mWeiBoAuthListener = listener
        // 检查微博是否安装
        if (!AppUtils.isSinaInstalled()) {
            mWeiBoAuthListener?.onNotInstall()
            return
        }
        RequestWeiBoActivity.startAuth()
    }

    /**
     * 通过微博客户端授权操作
     * @param listener 授权回调
     */
    fun startClientAuth(listener: WeiBoAuthListener? = null) {
        mWeiBoAuthListener = listener
        // 检查微博是否安装
        if (!AppUtils.isSinaInstalled()) {
            mWeiBoAuthListener?.onNotInstall()
            return
        }
        RequestWeiBoActivity.startClientAuth()
    }

    /**
     * 通过网页（H5）授权操作
     * @param listener 授权回调
     */
    fun startWebAuth(listener: WeiBoAuthListener? = null) {
        mWeiBoAuthListener = listener
        // 检查微博是否安装
        if (!AppUtils.isSinaInstalled()) {
            mWeiBoAuthListener?.onNotInstall()
            return
        }
        RequestWeiBoActivity.startWebAuth()
    }

    /**
     * 分享文字
     * @param text 分享的文字
     * @param listener 分享回调
     */
    fun shareText(text: String, listener: WeiBoShareListener? = null) {
        mWeiBoShareListener = listener
        // 检查微博是否安装
        if (!AppUtils.isSinaInstalled()) {
            mWeiBoAuthListener?.onNotInstall()
            return
        }
        RequestWeiBoActivity.shareText(text)
    }

    /**
     * 分享单张图片
     * @param url 图片网络地址
     * @param listener 分享回调
     */
    fun shareImage(url: String, listener: WeiBoShareListener? = null) {
        mWeiBoShareListener = listener
        // 检查微博是否安装
        if (!AppUtils.isSinaInstalled()) {
            mWeiBoAuthListener?.onNotInstall()
            return
        }
        RequestWeiBoActivity.shareImage(url)
    }

    /**
     * 分享多张图片
     */
    @Deprecated("暂不支持")
    fun shareImages(url: String, listener: WeiBoShareListener? = null) {
        mWeiBoShareListener = listener
        // 检查微博是否安装
        if (!AppUtils.isSinaInstalled()) {
            mWeiBoAuthListener?.onNotInstall()
            return
        }
        RequestWeiBoActivity.shareImages(url)
    }

    /**
     * 分享视频
     */
    @Deprecated("暂不支持")
    fun shareVideo(listener: WeiBoShareListener? = null) {
        mWeiBoShareListener = listener
        // 检查微博是否安装
        if (!AppUtils.isSinaInstalled()) {
            mWeiBoAuthListener?.onNotInstall()
            return
        }
        RequestWeiBoActivity.shareVideo()
    }

    /**
     * 分享网页
     * @param title 分享网页的标题
     * @param description 分享网页的描述
     * @param thumb 分享网页的缩略图
     * @param actionUrl 分享网页的链接
     * @param listener 分享回调
     */
    fun shareWebpage(title: String, description: String, thumb: String, actionUrl: String, listener: WeiBoShareListener? = null) {
        mWeiBoShareListener = listener
        // 检查微博是否安装
        if (!AppUtils.isSinaInstalled()) {
            mWeiBoAuthListener?.onNotInstall()
            return
        }
        val shareWebpageEntity = ShareWebpageEntity(title, description, thumb, actionUrl)
        RequestWeiBoActivity.shareWebpage(shareWebpageEntity)
    }
}