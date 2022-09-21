package com.alhanpos.store.model.response.product

data class Variation(
    val combo_variations: List<Any>,
    val created_at: String,
    val default_purchase_price: String,
    val default_sell_price: String,
    val deleted_at: Any,
    val discounts: List<Any>,
    val dpp_inc_tax: String,
    val id: Int,
    val media: List<Any>,
    val name: String,
    val product_id: String,
    val product_variation_id: String,
    val profit_percent: String,
    var sell_price_inc_tax: String,
    val sub_sku: String,
    val updated_at: String,
    val variation_location_details: List<VariationLocationDetail>,
    val variation_value_id: Any,
    val woocommerce_variation_id: Any
)