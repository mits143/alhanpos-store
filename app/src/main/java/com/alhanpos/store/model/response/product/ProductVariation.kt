package com.alhanpos.store.model.response.product

data class ProductVariation(
    val created_at: String,
    val id: Int,
    val is_dummy: String,
    val name: String,
    val product_id: String,
    val updated_at: String,
    val variation_template_id: Any,
    val variations: List<Variation>
)