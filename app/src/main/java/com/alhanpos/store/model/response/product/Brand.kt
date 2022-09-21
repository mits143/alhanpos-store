package com.alhanpos.store.model.response.product

data class Brand(
    val business_id: String,
    val created_at: String,
    val created_by: String,
    val deleted_at: Any,
    val description: Any,
    val id: Int,
    val name: String,
    val updated_at: String,
    val use_for_repair: String
)