package com.alhanpos.store.model.response


import com.google.gson.annotations.SerializedName

data class PaymentAccountResponse(
    @SerializedName("data")
    val `data`: List<Data>
)