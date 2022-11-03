package com.alhanpos.store.model.response.purchaseorder


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("added_by")
    val addedBy: String,
    @SerializedName("document")
    val document: Any,
    @SerializedName("final_total")
    val finalTotal: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location_name")
    val locationName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("pay_term_number")
    val payTermNumber: Any,
    @SerializedName("pay_term_type")
    val payTermType: Any,
    @SerializedName("po_qty_remaining")
    val poQtyRemaining: String,
    @SerializedName("ref_no")
    val refNo: String,
    @SerializedName("shipping_status")
    val shippingStatus: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("supplier_business_name")
    val supplierBusinessName: String,
    @SerializedName("transaction_date")
    val transactionDate: String
)