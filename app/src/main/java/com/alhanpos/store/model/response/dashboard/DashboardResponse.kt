package com.alhanpos.store.model.response.dashboard

data class DashboardResponse(
    val invoice_due: Int,
    val purchase_due: Int,
    val total_additional_expense: Int,
    val total_expense: Int,
    val total_purchase: Int,
    val total_purchase_exc_tax: Int,
    val total_purchase_inc_tax: Int,
    val total_purchase_return: Int,
    val total_sell: Int,
    val total_sell_return: Int,
    val total_shipping_charges: Int
)