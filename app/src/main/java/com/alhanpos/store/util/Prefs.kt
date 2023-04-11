package com.alhanpos.store.util

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val ACCESS_TOKEN = "access_token"
    private val SKU = "sku"

    private val preferences: SharedPreferences =
        context.getSharedPreferences("alhanpos_pref", Context.MODE_PRIVATE)

    var accessToken: String?
        get() = preferences.getString(ACCESS_TOKEN, "")
        set(value) = preferences.edit().putString(ACCESS_TOKEN, value).apply()

    var sku: String?
        get() = preferences.getString(SKU, "")
        set(value) = preferences.edit().putString(SKU, value).apply()
}