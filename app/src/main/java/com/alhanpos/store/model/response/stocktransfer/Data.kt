package com.alhanpos.store.model.response.stocktransfer


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("additional_notes")
    val additionalNotes: Any,
    @SerializedName("DT_RowId")
    val dTRowId: String,
    @SerializedName("final_total")
    val finalTotal: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location_from")
    val locationFrom: String,
    @SerializedName("location_to")
    val locationTo: String,
    @SerializedName("ref_no")
    val refNo: String,
    @SerializedName("shipping_charges")
    val shippingCharges: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("transaction_date")
    val transactionDate: String
)