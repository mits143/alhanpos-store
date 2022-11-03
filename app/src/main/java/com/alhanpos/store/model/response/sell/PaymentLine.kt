package com.alhanpos.store.model.response.sell


import com.google.gson.annotations.SerializedName

data class PaymentLine(
    @SerializedName("account_id")
    val accountId: String,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("bank_account_number")
    val bankAccountNumber: String,
    @SerializedName("business_id")
    val businessId: String,
    @SerializedName("card_holder_name")
    val cardHolderName: String,
    @SerializedName("card_month")
    val cardMonth: String,
    @SerializedName("card_number")
    val cardNumber: String,
    @SerializedName("card_security")
    val cardSecurity: String,
    @SerializedName("card_transaction_number")
    val cardTransactionNumber: String,
    @SerializedName("card_type")
    val cardType: String,
    @SerializedName("card_year")
    val cardYear: Any,
    @SerializedName("cheque_number")
    val chequeNumber: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("created_by")
    val createdBy: String,
    @SerializedName("document")
    val document: Any,
    @SerializedName("gateway")
    val gateway: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_advance")
    val isAdvance: String,
    @SerializedName("is_return")
    val isReturn: String,
    @SerializedName("method")
    val method: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("paid_on")
    val paidOn: String,
    @SerializedName("paid_through_link")
    val paidThroughLink: String,
    @SerializedName("parent_id")
    val parentId: String,
    @SerializedName("payment_for")
    val paymentFor: String,
    @SerializedName("payment_ref_no")
    val paymentRefNo: String,
    @SerializedName("transaction_id")
    val transactionId: String,
    @SerializedName("transaction_no")
    val transactionNo: String,
    @SerializedName("updated_at")
    val updatedAt: String
)