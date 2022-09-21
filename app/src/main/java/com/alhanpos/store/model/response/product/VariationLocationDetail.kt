package com.alhanpos.store.model.response.product

data class VariationLocationDetail(
    val created_at: String,
    val id: Int,
    val location_id: String,
    val product_id: String,
    val product_variation_id: String,
    val qty_available: String,
    val updated_at: String,
    val variation_id: String
)