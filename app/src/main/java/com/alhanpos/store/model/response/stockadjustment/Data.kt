package com.alhanpos.store.model.response.stockadjustment


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("added_by")
    val addedBy: String? = null,
    @SerializedName("additional_notes")
    val additionalNotes: Any,
    @SerializedName("adjustment_type")
    val adjustmentType: String? = null,
    @SerializedName("DT_RowId")
    val dTRowId: String? = null,
    @SerializedName("final_total")
    val finalTotal: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("location_name")
    val locationName: String? = null,
    @SerializedName("ref_no")
    val refNo: String? = null,
    @SerializedName("total_amount_recovered")
    val totalAmountRecovered: String? = null,
    @SerializedName("transaction_date")
    val transactionDate: String? = null
) : java.io.Serializable