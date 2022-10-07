package com.alhanpos.store.model.response.product

data class Brand(
    val business_id: String? = null,
    val created_at: String? = null,
    val created_by: String? = null,
    val deleted_at: Any,
    val description: Any,
    val id: Int,
    val name: String? = null,
    val updated_at: String? = null,
    val use_for_repair: String? = null
)