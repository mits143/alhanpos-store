package com.alhanpos.store.model.request.stockTransfer


import com.google.gson.annotations.SerializedName

data class StockTransferRequest(
    @SerializedName("contact_id")
    val contactId: String,
    @SerializedName("discount_amount")
    val discountAmount: String,
    @SerializedName("discount_type")
    val discountType: String,
    @SerializedName("final_total")
    val finalTotal: String,
    @SerializedName("location_id")
    val locationId: String,
    @SerializedName("pay_term_type")
    val payTermType: Int,
    @SerializedName("products")
    val products: ArrayList<Product>,
    @SerializedName("ref_no")
    val refNo: String,
    @SerializedName("shipping_charges")
    val shippingCharges: String,
    @SerializedName("shipping_details")
    val shippingDetails: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tax_amount")
    val taxAmount: String,
    @SerializedName("tax_id")
    val taxId: String,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("transfer_location_id")
    val transferLocationId: Int
)