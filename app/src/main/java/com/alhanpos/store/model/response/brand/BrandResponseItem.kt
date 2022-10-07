package com.alhanpos.store.model.response.brand


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BrandResponseItem(
    @SerializedName("business_id")
    val businessId: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("created_by")
    val createdBy: String? = null,
    @SerializedName("deleted_at")
    val deletedAt: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("use_for_repair")
    val useForRepair: String
) : Serializable