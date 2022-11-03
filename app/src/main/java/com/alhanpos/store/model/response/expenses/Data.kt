package com.alhanpos.store.model.response.expenses


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("added_by")
    val addedBy: String,
    @SerializedName("additional_notes")
    val additionalNotes: String,
    @SerializedName("amount_paid")
    val amountPaid: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("contact_name")
    val contactName: Any,
    @SerializedName("document")
    val document: Any,
    @SerializedName("expense_for")
    val expenseFor: String,
    @SerializedName("final_total")
    val finalTotal: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_recurring")
    val isRecurring: String,
    @SerializedName("location_name")
    val locationName: String,
    @SerializedName("payment_status")
    val paymentStatus: String,
    @SerializedName("recur_interval")
    val recurInterval: String,
    @SerializedName("recur_interval_type")
    val recurIntervalType: String,
    @SerializedName("recur_parent_id")
    val recurParentId: Any,
    @SerializedName("recur_repetitions")
    val recurRepetitions: Any,
    @SerializedName("recurring_parent")
    val recurringParent: Any,
    @SerializedName("ref_no")
    val refNo: String,
    @SerializedName("subscription_repeat_on")
    val subscriptionRepeatOn: Any,
    @SerializedName("tax")
    val tax: Any,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("type")
    val type: String
)