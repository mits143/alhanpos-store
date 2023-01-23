package com.alhanpos.store.model.request.expense


import com.google.gson.annotations.SerializedName

data class ExpenseRequest(
    @SerializedName("additional_notes")
    val additionalNotes: String,
//    @SerializedName("change_return")
//    val changeReturn: String,
    @SerializedName("contact_id")
    val contactId: Int,
//    @SerializedName("discount_type_modal")
//    val discountTypeModal: String,
    @SerializedName("expense_category_id")
    val expenseCategoryId: Int,
    @SerializedName("expense_for")
    val expenseFor: Int,
    @SerializedName("expense_sub_category_id")
    val expenseSubCategoryId: Int,
    @SerializedName("final_total")
    val finalTotal: String,
    @SerializedName("is_suspend")
    val isSuspend: String,
    @SerializedName("location_id")
    val locationId: Int,
    @SerializedName("payment")
    val payment: List<Payment>,
    @SerializedName("recur_interval")
    val recurInterval: String,
    @SerializedName("recur_interval_type")
    val recurIntervalType: String,
    @SerializedName("recur_repetitions")
    val recurRepetitions: String,
    @SerializedName("ref_no")
    val refNo: String,
//    @SerializedName("sale_note")
//    val saleNote: String,
    @SerializedName("staff_note")
    val staffNote: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("subscription_repeat_on")
    val subscriptionRepeatOn: String,
//    @SerializedName("tax_calculation_amount")
//    val taxCalculationAmount: Int,
    @SerializedName("tax_id")
    val taxId: Int,
    @SerializedName("transaction_date")
    val transactionDate: String
)