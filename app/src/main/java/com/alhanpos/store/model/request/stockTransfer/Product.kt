package com.alhanpos.store.model.request.stockTransfer


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("account_id")
    val accountId: String,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("base_unit_multiplier")
    val baseUnitMultiplier: String,
    @SerializedName("method")
    val method: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("paid_on")
    val paidOn: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("product_unit_id")
    val productUnitId: String,
    @SerializedName("purchase_price")
    val purchasePrice: String,
    @SerializedName("purchase_price_inc_tax")
    val purchasePriceIncTax: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("sub_unit_id")
    val subUnitId: String,
    @SerializedName("unit_price")
    val unitPrice: String,
    @SerializedName("variation_id")
    val variationId: String
)