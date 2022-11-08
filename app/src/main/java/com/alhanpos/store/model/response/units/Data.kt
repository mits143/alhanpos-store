package com.alhanpos.store.model.response.units


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("actual_name")
    val actualName: String,
    @SerializedName("allow_decimal")
    val allowDecimal: String,
    @SerializedName("base_unit_id")
    val baseUnitId: Any,
    @SerializedName("base_unit_multiplier")
    val baseUnitMultiplier: Any,
    @SerializedName("business_id")
    val businessId: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("created_by")
    val createdBy: String,
    @SerializedName("deleted_at")
    val deletedAt: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("short_name")
    val shortName: String,
    @SerializedName("updated_at")
    val updatedAt: String
)