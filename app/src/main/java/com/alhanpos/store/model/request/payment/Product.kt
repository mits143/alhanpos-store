package com.alhanpos.store.model.request.payment


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("base_unit_multiplier")
    val baseUnitMultiplier: String,
    @SerializedName("enable_stock")
    val enableStock: String,
    @SerializedName("item_tax")
    val itemTax: String,
    @SerializedName("line_discount_amount")
    val lineDiscountAmount: String,
    @SerializedName("line_discount_type")
    val lineDiscountType: String,
    @SerializedName("lot_no_line_id")
    val lotNoLineId: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("product_type")
    val productType: String,
    @SerializedName("product_unit_id")
    val productUnitId: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("sell_line_note")
    val sellLineNote: String,
    @SerializedName("sub_unit_id")
    val subUnitId: String,
    @SerializedName("tax_id")
    val taxId: String,
    @SerializedName("unit_price")
    val unitPrice: String,
    @SerializedName("unit_price_inc_tax")
    val unitPriceIncTax: String,
    @SerializedName("variation_id")
    val variationId: String
)