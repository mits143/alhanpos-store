package com.alhanpos.store.model.request.expense


import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("account_id")
    val accountId: String,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("bank_account_number")
    val bankAccountNumber: String,
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
    val cardYear: String,
    @SerializedName("cheque_number")
    val chequeNumber: String,
    @SerializedName("method")
    val method: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("transaction_no_1")
    val transactionNo1: String
)