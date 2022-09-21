package com.alhanpos.store.model.response.product

data class Unit(
    val actual_name: String,
    val allow_decimal: String,
    val base_unit_id: Any,
    val base_unit_multiplier: Any,
    val business_id: String,
    val created_at: String,
    val created_by: String,
    val deleted_at: Any,
    val id: Int,
    val short_name: String,
    val updated_at: String
)