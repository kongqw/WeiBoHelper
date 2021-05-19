package com.kongqw.weibohelper.util

import android.content.Context
import android.content.pm.PackageManager

object MetaUtil {

    /**
     * 读取AppMetaData信息
     */
    private fun getAppMetaData(context: Context, key: String): String? {
        if (key.isEmpty()) {
            return null
        }
        return try {
            context.packageManager?.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
                ?.metaData?.get(key)?.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 获取 WeiBo APP ID
     */
    fun getWeiBoAppId(context: Context): String? {
        return getAppMetaData(context, "weibo_app_id")
    }

    /**
     * 获取 WeiBo REDIRECT URL
     */
    fun getWeiBoRedirectUrl(context: Context): String? {
        return getAppMetaData(context, "weibo_redirect_url")
    }

    /**
     * 获取 WeiBo SCOPE
     */
    fun getWeiBoScope(context: Context): String? {
        return getAppMetaData(context, "weibo_scope")
    }

}