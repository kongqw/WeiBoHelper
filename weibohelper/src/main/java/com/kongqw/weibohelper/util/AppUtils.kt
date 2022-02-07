package com.kongqw.weibohelper.util

import android.annotation.SuppressLint
import com.kongqw.weibohelper.WeiBoHelper

internal object AppUtils {

    //    const val PACKAGE_NAME_WECHAT = "com.tencent.mm"
    //    const val PACKAGE_NAME_QQ = "com.tencent.mobileqq"
    //    const val PACKAGE_NAME_SINA = "com.sina.weibo"
    //
    //    /**
    //     * 判断 用户是否安装APP
    //     *
    //     */
    //    @SuppressLint("QueryPermissionsNeeded")
    //    @JvmStatic
    //    fun isAppInstalled(context: Context, packageName: String): Boolean {
    //        try {
    //            context.packageManager.getInstalledPackages(0).forEach { packageInfo ->
    //                if (packageName == packageInfo.packageName) {
    //                    return true
    //                }
    //            }
    //        } catch (e: Exception) {
    //            e.printStackTrace()
    //        }
    //        return false
    //    }

    /**
     * 是否安装了微博APP
     */
    @SuppressLint("QueryPermissionsNeeded")
    @JvmStatic
    fun isSinaInstalled(): Boolean {
        try {
            WeiBoHelper.applicationContext.packageManager.getInstalledPackages(0).forEach { packageInfo ->
                if ("com.sina.weibo" == packageInfo.packageName) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}

