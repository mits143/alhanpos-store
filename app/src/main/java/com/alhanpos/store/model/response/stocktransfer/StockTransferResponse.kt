package com.alhanpos.store.model.response.stocktransfer


import com.google.gson.annotations.SerializedName

data class StockTransferResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>
)