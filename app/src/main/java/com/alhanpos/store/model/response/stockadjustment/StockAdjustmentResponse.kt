package com.alhanpos.store.model.response.stockadjustment


import com.google.gson.annotations.SerializedName

data class StockAdjustmentResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>
)