package com.alhanpos.store.model.response.product

data class Brand(
    val business_id: String? = null,
    val created_at: String? = null,
    val created_by: String? = null,
    val deleted_at: String? = null,
    val description: String? = null,
    val id: Int = 0,
    val name: String? = null,
    val updated_at: String? = null,
    val use_for_repair: String? = null
) : java.io.Serializable