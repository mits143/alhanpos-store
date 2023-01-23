package com.alhanpos.store.model.request.purchase


import com.google.gson.annotations.SerializedName

data class PurchaseRequest(
//    @SerializedName("advance_balance")
//    val advanceBalance: String,
    @SerializedName("contact_id")
    val contactId: String,
    @SerializedName("discount_amount")
    val discountAmount: String,
    @SerializedName("discount_type")
    val discountType: String,
    @SerializedName("exchange_rate")
    val exchangeRate: Int,
    @SerializedName("expense_category_id")
    val expenseCategoryId: Int,
    @SerializedName("expense_for")
    val expenseFor: String,
    @SerializedName("expense_sub_category_id")
    val expenseSubCategoryId: Int,
    @SerializedName("final_total")
    val finalTotal: String,
    @SerializedName("pay_term_number")
    val payTermNumber: Int,
    @SerializedName("pay_term_type")
    val payTermType: Int,
    @SerializedName("products")
    val products: List<Product>,
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
    val transactionDate: String
)