package com.kongqw.weibohelper.util

import com.google.gson.Gson
import java.lang.reflect.Type

internal object GsonUtils {

    private val mGson = Gson()

    fun <T> fromJson(json: String?, classOfT: Class<T>): T? {
        if (json.isNullOrEmpty()) {
            return null
        }
        return try {
            mGson.fromJson(json, classOfT)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun <T> fromJson(json: String?, type: Type): T? {
        if (json.isNullOrEmpty()) {
            return null
        }
        return try {
            mGson.fromJson(json, type)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun toJson(any: Any?): String = try {
        mGson.toJson(any)
    } catch (e: Exception) {
        e.printStackTrace()
        "{}"
    }
}