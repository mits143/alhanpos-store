package com.alhanpos.store.model.response.category


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryResponseItem(
    @SerializedName("business_id")
    val businessId: String? = null,
    @SerializedName("category_type")
    val categoryType: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("created_by")
    val createdBy: String? = null,
    @SerializedName("deleted_at")
    val deletedAt: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("parent_id")
    val parentId: String? = null,
    @SerializedName("short_code")
    val shortCode: String? = null,
    @SerializedName("slug")
    val slug: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("woocommerce_cat_id")
    val woocommerceCatId: String? = null
) : Serializable

