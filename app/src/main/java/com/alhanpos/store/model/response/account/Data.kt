package com.alhanpos.store.model.response.account


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("account_details")
    val accountDetails: List<AccountDetail>,
    @SerializedName("account_number")
    val accountNumber: String,
    @SerializedName("account_type_id")
    val accountTypeId: String,
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
    @SerializedName("is_closed")
    val isClosed: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("note")
    val note: Any,
    @SerializedName("updated_at")
    val updatedAt: String
)