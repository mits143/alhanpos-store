package com.alhanpos.store.model.request.stockAdjustment


import com.google.gson.annotations.SerializedName

data class StockAdjustmentRequest(
    @SerializedName("additional_notes")
    val additionalNotes: String,
    @SerializedName("adjustment_type")
    val adjustmentType: Int,
    @SerializedName("final_total")
    val finalTotal: String,
    @SerializedName("location_id")
    val locationId: String,
    @SerializedName("products")
    val products: ArrayList<Product>,
    @SerializedName("ref_no")
    val refNo: String,
    @SerializedName("search_product")
    val searchProduct: String,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("total_amount_recovered")
    val totalAmountRecovered: String
)