package com.alhanpos.store.model.response.method


import com.google.gson.annotations.SerializedName

data class PaymentMethodResponse(
    @SerializedName("bank_transfer")
    val bankTransfer: String,
    @SerializedName("card")
    val card: String,
    @SerializedName("cash")
    val cash: String,
    @SerializedName("cheque")
    val cheque: String,
    @SerializedName("custom_pay_1")
    val customPay1: String,
    @SerializedName("custom_pay_2")
    val customPay2: String,
    @SerializedName("custom_pay_3")
    val customPay3: String,
    @SerializedName("custom_pay_4")
    val customPay4: String,
    @SerializedName("custom_pay_5")
    val customPay5: String,
    @SerializedName("custom_pay_6")
    val customPay6: String,
    @SerializedName("custom_pay_7")
    val customPay7: String,
    @SerializedName("other")
    val other: String
)