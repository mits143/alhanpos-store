package com.alhanpos.store.model.response.stockadjustment


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("added_by")
    val addedBy: String,
    @SerializedName("additional_notes")
    val additionalNotes: Any,
    @SerializedName("adjustment_type")
    val adjustmentType: String,
    @SerializedName("DT_RowId")
    val dTRowId: String,
    @SerializedName("final_total")
    val finalTotal: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location_name")
    val locationName: String,
    @SerializedName("ref_no")
    val refNo: String,
    @SerializedName("total_amount_recovered")
    val totalAmountRecovered: String,
    @SerializedName("transaction_date")
    val transactionDate: String
)