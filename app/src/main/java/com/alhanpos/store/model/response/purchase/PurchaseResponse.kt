package com.alhanpos.store.model.response.purchase


import com.google.gson.annotations.SerializedName

data class PurchaseResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>
) {
    data class Data(
        @SerializedName("added_by")
        val addedBy: String?,
        @SerializedName("amount_paid")
        val amountPaid: Any?,
        @SerializedName("amount_return")
        val amountReturn: String?,
        @SerializedName("document")
        val document: Any?,
        @SerializedName("final_total")
        val finalTotal: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("location_name")
        val locationName: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("pay_term_number")
        val payTermNumber: String?,
        @SerializedName("pay_term_type")
        val payTermType: String?,
        @SerializedName("payment_status")
        val paymentStatus: String?,
        @SerializedName("ref_no")
        val refNo: String?,
        @SerializedName("return_exists")
        val returnExists: String?,
        @SerializedName("return_paid")
        val returnPaid: Any?,
        @SerializedName("return_transaction_id")
        val returnTransactionId: Any?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("supplier_business_name")
        val supplierBusinessName: String?,
        @SerializedName("transaction_date")
        val transactionDate: String?
    )
}