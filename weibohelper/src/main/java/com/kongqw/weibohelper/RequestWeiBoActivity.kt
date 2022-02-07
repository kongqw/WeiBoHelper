package com.kongqw.weibohelper

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.kongqw.weibohelper.entity.ShareWebpageEntity
import com.kongqw.weibohelper.util.Logger
import com.kongqw.weibohelper.util.MetaUtil
import com.sina.weibo.sdk.api.*
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.openapi.IWBAPI
import com.sina.weibo.sdk.openapi.SdkListener
import com.sina.weibo.sdk.openapi.WBAPIFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

internal class RequestWeiBoActivity : AppCompatActivity() {

    companion object {

        private const val TAG = "RequestWeiBoActivity"

        /** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY  */
        // const val APP_KEY = "97830691"

        /**
         * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
         * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
         */
        // const val REDIRECT_URL = "http://www.sina.com"
        // const val REDIRECT_URL = "http://www.liulishuo.com"
        const val REDIRECT_URL = "https://api.weibo.com/oauth2/default.html"

        /**
         * WeiboSDKDemo 应用对应的权限，第三方开发者一般不需要这么多，可直接设置成空即可。
         * 详情请查看 Demo 中对应的注释。
         */
        const val SCOPE =
            ("email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write")


        private const val EXTRA_WEIBO_ACTION = "EXTRA_WEIBO_ACTION"
        private const val EXTRA_SHARE_TEXT = "EXTRA_SHARE_TEXT"
        private const val EXTRA_SHARE_SINGLE_IMAGE = "EXTRA_SHARE_SINGLE_IMAGE"
        private const val EXTRA_SHARE_IMAGES = "EXTRA_SHARE_IMAGES"
        private const val EXTRA_SHARE_VIDEO = "EXTRA_SHARE_VIDEO"
        private const val EXTRA_SHARE_WEBPAGE = "EXTRA_SHARE_WEBPAGE"

        /**
         * 授权
         */
        fun startAuth() {
            WeiBoHelper.applicationContext.startActivity(Intent(WeiBoHelper.applicationContext, RequestWeiBoActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(EXTRA_WEIBO_ACTION, WeiBoAction.AUTH)
            })
        }

        /**
         * 启动客户端授权
         */
        fun startClientAuth() {
            WeiBoHelper.applicationContext.startActivity(Intent(WeiBoHelper.applicationContext, RequestWeiBoActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(EXTRA_WEIBO_ACTION, WeiBoAction.CLIENT_AUTH)
            })
        }

        /**
         * 启动网页（H5）授权
         */
        fun startWebAuth() {
            WeiBoHelper.applicationContext.startActivity(Intent(WeiBoHelper.applicationContext, RequestWeiBoActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(EXTRA_WEIBO_ACTION, WeiBoAction.CLIENT_WEB)
            })
        }

        /**
         * 分享文字
         */
        fun shareText(text: String) {
            WeiBoHelper.applicationContext.startActivity(Intent(WeiBoHelper.applicationContext, RequestWeiBoActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(EXTRA_WEIBO_ACTION, WeiBoAction.SHARE_TEXT)
                putExtra(EXTRA_SHARE_TEXT, text)
            })
        }

        /**
         * 分享单张图片
         */
        fun shareImage(url: String) {
            WeiBoHelper.applicationContext.startActivity(Intent(WeiBoHelper.applicationContext, RequestWeiBoActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(EXTRA_WEIBO_ACTION, WeiBoAction.SHARE_SINGLE_IMAGE)
                putExtra(EXTRA_SHARE_SINGLE_IMAGE, url)
            })
        }

        /**
         * 分享多张图片
         */
        fun shareImages(url: String) {
            WeiBoHelper.applicationContext.startActivity(Intent(WeiBoHelper.applicationContext, RequestWeiBoActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(EXTRA_WEIBO_ACTION, WeiBoAction.SHARE_IMAGES)
                putExtra(EXTRA_SHARE_IMAGES, url)
            })
        }

        /**
         * 分享视频
         */
        fun shareVideo() {
            WeiBoHelper.applicationContext.startActivity(Intent(WeiBoHelper.applicationContext, RequestWeiBoActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(EXTRA_WEIBO_ACTION, WeiBoAction.SHARE_VIDEO)
                // putExtra(EXTRA_SHARE_VIDEO, WeiBoAction.SHARE_VIDEO)
            })
        }

        /**
         * 分享网页
         */
        fun shareWebpage(shareWebpageEntity: ShareWebpageEntity) {
            WeiBoHelper.applicationContext.startActivity(Intent(WeiBoHelper.applicationContext, RequestWeiBoActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(EXTRA_WEIBO_ACTION, WeiBoAction.SHARE_WEBPAGE)
                putExtra(EXTRA_SHARE_WEBPAGE, shareWebpageEntity)
            })
        }
    }

    // 微博AppId
    private val mAppId: String = MetaUtil.getWeiBoAppId(WeiBoHelper.applicationContext) ?: ""

    // 微博 REDIRECT_URL
    private val mRedirectUrl: String = MetaUtil.getWeiBoRedirectUrl(WeiBoHelper.applicationContext) ?: REDIRECT_URL

    // 微博 SCOPE
    private val mScope: String = MetaUtil.getWeiBoScope(WeiBoHelper.applicationContext) ?: SCOPE

    private val mAuthInfo = AuthInfo(WeiBoHelper.applicationContext, mAppId, mRedirectUrl, mScope)

    private lateinit var mIWBAPI: IWBAPI

    private lateinit var mWeiBoAction: WeiBoAction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Logger.i(TAG, "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
        Logger.i(TAG, "┃ mAppId = $mAppId")
        Logger.i(TAG, "┃ mRedirectUrl = $mRedirectUrl")
        Logger.i(TAG, "┃ mScope = $mScope")
        Logger.i(TAG, "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")

        mWeiBoAction = (intent.getSerializableExtra(EXTRA_WEIBO_ACTION) as? WeiBoAction?).let { weiBoAction ->
            if (null == weiBoAction) {
                finish()
                return
            }
            return@let weiBoAction
        }


        mIWBAPI = WBAPIFactory.createWBAPI(this)

        registerApp {
            Logger.i(TAG, "registerApp  22222")
            when (mWeiBoAction) {
                // 授权登录
                WeiBoAction.AUTH -> {
                    startAuth()
                }
                // 客户端授权登录
                WeiBoAction.CLIENT_AUTH -> {
                    startClientAuth()
                }
                // 指定网页（H5）授权登录
                WeiBoAction.CLIENT_WEB -> {
                    startWebAuth()
                }
                // 分享文字
                WeiBoAction.SHARE_TEXT -> {
                    val text = intent.getStringExtra(EXTRA_SHARE_TEXT) ?: ""
                    shareText(text)
                }
                // 分享单张图片
                WeiBoAction.SHARE_SINGLE_IMAGE -> {
                    val url = intent.getStringExtra(EXTRA_SHARE_SINGLE_IMAGE) ?: ""
                    shareSingleImage(url)
                }
                // 分享多张图片
                WeiBoAction.SHARE_IMAGES -> {
                    val url = intent.getStringExtra(EXTRA_SHARE_IMAGES) ?: ""
                    val data = ArrayList<String>()
                    data.add(url)
                    shareImages(data)
                }
                // 分享视频
                WeiBoAction.SHARE_VIDEO -> {

                }
                // 分享网页
                WeiBoAction.SHARE_WEBPAGE -> {
                    val shareWebpageEntity = intent.getSerializableExtra(EXTRA_SHARE_WEBPAGE) as ShareWebpageEntity
                    shareWebpage(shareWebpageEntity)
                }
            }
            WeiBoHelper.mWeiBoAuthListener?.onLoadingStart()
            WeiBoHelper.mWeiBoShareListener?.onLoadingStart()
        }
    }

    private fun registerApp(listener: (() -> Unit)) {
        if (WeiBoHelper.isRegistered) {
            listener()
            return
        }
        mIWBAPI.registerApp(this, mAuthInfo, object : SdkListener {
            override fun onInitSuccess() {
                WeiBoHelper.isRegistered = true
                Logger.i(TAG, "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
                Logger.i(TAG, "┃ 微博SDK初始化成功回调，成功一次后再次初始化会再有任何回调")
                Logger.i(TAG, "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
                GlobalScope.launch(Dispatchers.Main) {
                    listener()
                }
            }

            override fun onInitFailure(p0: java.lang.Exception?) {
                WeiBoHelper.isRegistered = false
                Logger.i(TAG, "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
                Logger.i(TAG, "┃ 微博SDK初始化失败回调")
                Logger.i(TAG, "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
                finishAfterTransition()
            }
        })
    }

    override fun onDestroy() {
        WeiBoHelper.mWeiBoAuthListener?.onLoadingEnd()
        WeiBoHelper.mWeiBoShareListener?.onLoadingEnd()
        super.onDestroy()
    }

    /**
     * 授权操作
     */
    private fun startAuth() {
        mIWBAPI.authorize(this, WeiBoHelper.mWbAuthListener)
    }

    /**
     * 通过微博客户端授权操作
     */
    private fun startClientAuth() {
        mIWBAPI.authorizeClient(this, WeiBoHelper.mWbAuthListener)
    }

    /**
     * 通过指定网页（H5）授权操作
     */
    private fun startWebAuth() {
        mIWBAPI.authorizeWeb(this, WeiBoHelper.mWbAuthListener)
        finishAfterTransition()
    }

    /**
     * 分享文字
     */
    private fun shareText(text: String) {
        val message = WeiboMultiMessage()
        message.textObject = TextObject().apply {
            this.text = text
        }
        mIWBAPI.shareMessage(this, message, true)
    }

    /**
     * 分享单张图片
     */
    private fun shareSingleImage(url: String) {
        // 创建协程
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val bitmap = Glide.with(this@RequestWeiBoActivity).asBitmap().load(url).submit().get()
                val message = WeiboMultiMessage()
                val imageObject = ImageObject()
                imageObject.setImageData(bitmap)
                message.imageObject = imageObject
                mIWBAPI.shareMessage(this@RequestWeiBoActivity, message, true)
            } catch (e: Exception) {
                e.printStackTrace()
                finishAfterTransition()
            }
        }
    }

    /**
     * 分享多图
     * 多图分享必须是本地图片文件的路径，并且是当前应用可以访问的路径，现在不支持网络路径。
     * 接入程序必须有文件读写权限，否则会造成分享失败。
     * 使用前通过 WbSdk.isSupportMultipleImage(context) 判断是否支持。
     */
    @Deprecated("暂不支持")
    private fun shareImages(images: ArrayList<String>) {
        // 创建协程
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val imageList = ArrayList<Uri>()
                images.forEach { url ->
                    val file: File = Glide.with(this@RequestWeiBoActivity).asFile().load(url).submit().get()
                    imageList.add(Uri.fromFile(file))
                }
                val message = WeiboMultiMessage()
                val multiImageObject = MultiImageObject()
                multiImageObject.imageList = imageList
                message.multiImageObject = multiImageObject
                mIWBAPI.shareMessage(this@RequestWeiBoActivity, message, true)
            } catch (e: Exception) {
                e.printStackTrace()
                finishAfterTransition()
            }
        }
    }

    /**
     * 分享视频
     * 设置的是本地视频文件的路径，并且是当前应用可以访问的路径，现在不支持网络路径。
     * 目前支持视频格式为：mp4
     */
    @Deprecated("很少用，暂不支持")
    private fun shareVideo() {
        // val message = WeiboMultiMessage()
        // val videoSourceObject = VideoSourceObject()
        // mIWBAPI.shareMessage(message, true)
        finishAfterTransition()
    }

    /**
     * 分享网页
     */
    private fun shareWebpage(shareWebpageEntity: ShareWebpageEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val bitmap = Glide.with(this@RequestWeiBoActivity).asBitmap().load(shareWebpageEntity.thumb).submit().get()
                val message = WeiboMultiMessage()

                val webpageObject = WebpageObject()
                webpageObject.identify = UUID.randomUUID().toString()
                webpageObject.title = shareWebpageEntity.title
                webpageObject.description = shareWebpageEntity.description
                webpageObject.thumbData = getThumbData(bitmap)
                webpageObject.actionUrl = shareWebpageEntity.actionUrl
                webpageObject.defaultText = shareWebpageEntity.title

                message.mediaObject = webpageObject
                mIWBAPI.shareMessage(this@RequestWeiBoActivity, message, true)
            } catch (e: Exception) {
                e.printStackTrace()
                finishAfterTransition()
            }
        }
    }

    /**
     * 获取缩略图
     */
    private fun getThumbData(bitmap: Bitmap): ByteArray? {
        var os: ByteArrayOutputStream? = null
        return try {
            os = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, os)
            os.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            try {
                bitmap.recycle()
                os?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     *
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mIWBAPI.authorizeCallback(this@RequestWeiBoActivity, requestCode, resultCode, data)
        mIWBAPI.doResultIntent(data, WeiBoHelper.mWbShareCallback)
        finish()
    }
}