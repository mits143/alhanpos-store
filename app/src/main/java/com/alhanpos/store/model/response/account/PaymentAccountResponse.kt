package com.alhanpos.store.model.response.account


import com.google.gson.annotations.SerializedName

data class PaymentAccountResponse(
    @SerializedName("data")
    val `data`: List<Data>
)