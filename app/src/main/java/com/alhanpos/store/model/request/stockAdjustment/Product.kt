package com.alhanpos.store.model.request.stockAdjustment


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("enable_stock")
    val enableStock: String,
    @SerializedName("lot_no_line_id")
    val lotNoLineId: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("unit_price")
    val unitPrice: String,
    @SerializedName("variation_id")
    val variationId: String
)