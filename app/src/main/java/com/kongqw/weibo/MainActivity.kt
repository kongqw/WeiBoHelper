package com.kongqw.weibo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kongqw.weibo.databinding.ActivityMainBinding
import com.kongqw.weibohelper.WeiBoHelper
import com.kongqw.weibohelper.entity.WeiBoError
import com.kongqw.weibohelper.entity.WeiBoOauth2AccessToken
import com.kongqw.weibohelper.listener.WeiBoAuthListener
import com.kongqw.weibohelper.listener.WeiBoShareListener

class MainActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityMainBinding

    private val mWeiBoAuthListener = object : WeiBoAuthListener {

        override fun onLoadingStart() {
            mViewBinding.loading.visibility = View.VISIBLE
        }

        override fun onLoadingEnd() {
            mViewBinding.loading.visibility = View.GONE
        }

        override fun onNotInstall() {
            Toast.makeText(applicationContext, "微博未安装", Toast.LENGTH_SHORT).show()
        }

        override fun onComplete(token: WeiBoOauth2AccessToken?) {
            Toast.makeText(applicationContext, "授权成功啦:$token", Toast.LENGTH_SHORT).show()
        }

        override fun onError(error: WeiBoError?) {
            Toast.makeText(applicationContext, "授权失败:$error", Toast.LENGTH_SHORT).show()
        }

        override fun onCancel() {
            Toast.makeText(applicationContext, "授权取消", Toast.LENGTH_SHORT).show()
        }
    }

    private val mWeiBoShareListener = object : WeiBoShareListener {

        override fun onLoadingStart() {
            mViewBinding.loading.visibility = View.VISIBLE
        }

        override fun onLoadingEnd() {
            mViewBinding.loading.visibility = View.GONE
        }

        override fun onNotInstall() {
            Toast.makeText(applicationContext, "微博未安装", Toast.LENGTH_SHORT).show()
        }

        override fun onComplete() {
            Toast.makeText(applicationContext, "分享完成", Toast.LENGTH_SHORT).show()
        }

        override fun onError(error: WeiBoError?) {
            Toast.makeText(applicationContext, "分享异常:$error", Toast.LENGTH_SHORT).show()
            Log.i("MainActivity", "error = $error")
        }

        override fun onCancel() {
            Toast.makeText(applicationContext, "分享取消", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        // 微博授权
        mViewBinding.btnAuth.setOnClickListener {
            WeiBoHelper.startAuth(mWeiBoAuthListener)
        }

        // 微博客户端授权
        mViewBinding.btnClientAuth.setOnClickListener {
            WeiBoHelper.startClientAuth(mWeiBoAuthListener)
        }

        // 网页（H5）授权
        mViewBinding.btnWebAuth.setOnClickListener {
            WeiBoHelper.startWebAuth(mWeiBoAuthListener)
        }

        // 分享文字
        mViewBinding.btnShareText.setOnClickListener {
            WeiBoHelper.shareText("我是分享到微博的文字！", mWeiBoShareListener)
        }

        // 分享单图
        mViewBinding.btnShareSingleImage.setOnClickListener {
            // WeiBoHelper.shareImage("https://gank.io/images/02eb8ca3297f4931ab64b7ebd7b5b89c", mWeiBoShareListener)
            WeiBoHelper.shareImage("https://t7.baidu.com/it/u=1819248061,230866778&fm=193&f=GIF", mWeiBoShareListener)
        }
        // 分享多图
        mViewBinding.btnShareImages.setOnClickListener {
            // WeiBoHelper.shareImages("https://gank.io/images/02eb8ca3297f4931ab64b7ebd7b5b89c", mWeiBoShareListener)
            WeiBoHelper.shareImages("https://t7.baidu.com/it/u=1819248061,230866778&fm=193&f=GIF", mWeiBoShareListener)
        }

        // 分享视频
        mViewBinding.btnShareVideo.setOnClickListener {
            WeiBoHelper.shareVideo(mWeiBoShareListener)
        }

        // 分享网页
        mViewBinding.btnShareWebpage.setOnClickListener {
            // WeiBoHelper.shareWebpage("我是标题", "我是描述", "https://gank.io/images/02eb8ca3297f4931ab64b7ebd7b5b89c", "https://www.baidu.com", mWeiBoShareListener)
            WeiBoHelper.shareWebpage("我是标题", "我是描述", "https://t7.baidu.com/it/u=1819248061,230866778&fm=193&f=GIF", "https://www.baidu.com", mWeiBoShareListener)
        }
    }
}