package com.alhanpos.store.model.response


import com.google.gson.annotations.SerializedName

data class AccountDetail(
    @SerializedName("label")
    val label: Any,
    @SerializedName("value")
    val value: Any
)