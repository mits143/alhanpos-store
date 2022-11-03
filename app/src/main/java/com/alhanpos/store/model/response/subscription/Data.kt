package com.alhanpos.store.model.response.subscription


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("business_id")
    val businessId: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("created_by")
    val createdBy: String,
    @SerializedName("created_id")
    val createdId: String,
    @SerializedName("deleted_at")
    val deletedAt: Any,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("package_details")
    val packageDetails: PackageDetails,
    @SerializedName("package_id")
    val packageId: String,
    @SerializedName("package_name")
    val packageName: String,
    @SerializedName("package_price")
    val packagePrice: String,
    @SerializedName("paid_via")
    val paidVia: String,
    @SerializedName("payment_transaction_id")
    val paymentTransactionId: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("trial_end_date")
    val trialEndDate: String,
    @SerializedName("updated_at")
    val updatedAt: String
)