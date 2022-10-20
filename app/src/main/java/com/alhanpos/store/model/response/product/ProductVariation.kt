package com.alhanpos.store.model.response.product

data class ProductVariation(
    val created_at: String? = null,
    val id: Int? = null,
    val is_dummy: String? = null,
    val name: String? = null,
    val product_id: String? = null,
    val updated_at: String? = null,
    val variation_template_id: String? = null,
    val variations: List<Variation>
) : java.io.Serializable