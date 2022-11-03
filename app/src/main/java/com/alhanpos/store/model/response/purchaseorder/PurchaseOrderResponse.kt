package com.alhanpos.store.model.response.purchaseorder


import com.google.gson.annotations.SerializedName

data class PurchaseOrderResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>
)