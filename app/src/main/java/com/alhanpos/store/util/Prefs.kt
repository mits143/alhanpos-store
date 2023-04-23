package com.alhanpos.store.util

import android.content.Context
import android.content.SharedPreferences
import com.alhanpos.store.di.gson
import com.alhanpos.store.model.response.product.ProductListResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Prefs(context: Context) {
    private val ACCESS_TOKEN = "access_token"
    private val SKU = "sku"
    private val LIST = "list"

    private val preferences: SharedPreferences =
        context.getSharedPreferences("alhanpos_pref", Context.MODE_PRIVATE)

    var accessToken: String?
        get() = preferences.getString(ACCESS_TOKEN, "")
        set(value) = preferences.edit().putString(ACCESS_TOKEN, value).apply()

    var sku: String?
        get() = preferences.getString(SKU, "")
        set(value) = preferences.edit().putString(SKU, value).apply()

    fun saveArrayList(list: ArrayList<ProductListResponse.ProductListResponseItem>) {
        val json = gson.toJson(list)
        preferences.edit().putString(LIST, json).apply()
    }

    fun getArrayList(): ArrayList<ProductListResponse.ProductListResponseItem> {
        val json = preferences.getString(LIST, null)
        val type = object : TypeToken<ArrayList<ProductListResponse.ProductListResponseItem>>() {}.type
        return gson.fromJson(json, type)
    }
}