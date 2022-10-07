package com.alhanpos.store.model.response.product

data class Variation(
    val combo_variations: List<Any> = arrayListOf(),
    val created_at: String? = null,
    val default_purchase_price: String? = null,
    val default_sell_price: String? = null,
    val deleted_at: String? = null,
    val discounts: List<Any>,
    val dpp_inc_tax: String? = null,
    val id: Int,
    val media: List<Any>,
    val name: String? = null,
    val product_id: String? = null,
    val product_variation_id: String? = null,
    val profit_percent: String? = null,
    var sell_price_inc_tax: String? = null,
    val sub_sku: String? = null,
    val updated_at: String? = null,
    val variation_location_details: List<VariationLocationDetail>,
    val variation_value_id: String? = null,
    val woocommerce_variation_id: String? = null
)