package com.alhanpos.store.model.response.account


import com.google.gson.annotations.SerializedName

data class AccountDetail(
    @SerializedName("label")
    val label: Any,
    @SerializedName("value")
    val value: Any
)