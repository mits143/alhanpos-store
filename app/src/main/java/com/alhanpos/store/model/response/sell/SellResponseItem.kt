package com.alhanpos.store.model.response.sell


import com.google.gson.annotations.SerializedName

data class SellResponseItem(
    @SerializedName("added_by")
    val addedBy: String,
    @SerializedName("additional_notes")
    val additionalNotes: String,
    @SerializedName("amount_return")
    val amountReturn: String,
    @SerializedName("business_location")
    val businessLocation: String,
    @SerializedName("contact_id")
    val contactId: String,
    @SerializedName("crm_is_order_request")
    val crmIsOrderRequest: String,
    @SerializedName("custom_field_1")
    val customField1: Any,
    @SerializedName("custom_field_2")
    val customField2: Any,
    @SerializedName("custom_field_3")
    val customField3: Any,
    @SerializedName("custom_field_4")
    val customField4: Any,
    @SerializedName("discount_amount")
    val discountAmount: String,
    @SerializedName("discount_type")
    val discountType: String,
    @SerializedName("document")
    val document: Any,
    @SerializedName("final_total")
    val finalTotal: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("invoice_no")
    val invoiceNo: String,
    @SerializedName("invoice_no_text")
    val invoiceNoText: String,
    @SerializedName("is_direct_sale")
    val isDirectSale: String,
    @SerializedName("is_export")
    val isExport: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("pay_term_number")
    val payTermNumber: Any,
    @SerializedName("pay_term_type")
    val payTermType: Any,
    @SerializedName("payment_lines")
    val paymentLines: List<PaymentLine>,
    @SerializedName("payment_status")
    val paymentStatus: String,
    @SerializedName("return_exists")
    val returnExists: String,
    @SerializedName("return_paid")
    val returnPaid: String,
    @SerializedName("return_transaction_id")
    val returnTransactionId: String,
    @SerializedName("rp_earned")
    val rpEarned: String,
    @SerializedName("rp_redeemed")
    val rpRedeemed: String,
    @SerializedName("rp_redeemed_amount")
    val rpRedeemedAmount: String,
    @SerializedName("sale_date")
    val saleDate: String,
    @SerializedName("service_custom_field_1")
    val serviceCustomField1: Any,
    @SerializedName("shipping_custom_field_1")
    val shippingCustomField1: Any,
    @SerializedName("shipping_custom_field_2")
    val shippingCustomField2: Any,
    @SerializedName("shipping_custom_field_3")
    val shippingCustomField3: Any,
    @SerializedName("shipping_custom_field_4")
    val shippingCustomField4: Any,
    @SerializedName("shipping_custom_field_5")
    val shippingCustomField5: Any,
    @SerializedName("shipping_details")
    val shippingDetails: String,
    @SerializedName("shipping_status")
    val shippingStatus: String,
    @SerializedName("so_qty_remaining")
    val soQtyRemaining: String,
    @SerializedName("staff_note")
    val staffNote: Any,
    @SerializedName("status")
    val status: String,
    @SerializedName("supplier_business_name")
    val supplierBusinessName: String,
    @SerializedName("table_name")
    val tableName: Any,
    @SerializedName("tax_amount")
    val taxAmount: String,
    @SerializedName("total_before_tax")
    val totalBeforeTax: String,
    @SerializedName("total_items")
    val totalItems: String,
    @SerializedName("total_paid")
    val totalPaid: String,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("types_of_service_id")
    val typesOfServiceId: Any,
    @SerializedName("types_of_service_name")
    val typesOfServiceName: Any,
    @SerializedName("waiter")
    val waiter: String
)