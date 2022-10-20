package com.alhanpos.store.model.request.payment


import com.google.gson.annotations.SerializedName

data class PaymentRequest(
    @SerializedName("additional_notes")
    val additionalNotes: String,
    @SerializedName("advance_balance")
    val advanceBalance: String,
    @SerializedName("change_return")
    val changeReturn: String,
    @SerializedName("contact_id")
    val contactId: Int,
    @SerializedName("delivered_to")
    val deliveredTo: String,
    @SerializedName("delivered_to_modal")
    val deliveredToModal: String,
    @SerializedName("discount_amount")
    val discountAmount: String,
    @SerializedName("discount_amount_modal")
    val discountAmountModal: String,
    @SerializedName("discount_type")
    val discountType: String,
    @SerializedName("discount_type_modal")
    val discountTypeModal: String,
    @SerializedName("final_total")
    val finalTotal: String,
    @SerializedName("hidden_price_group")
    val hiddenPriceGroup: Int,
    @SerializedName("is_credit_sale")
    val isCreditSale: String,
    @SerializedName("is_enabled_stock")
    val isEnabledStock: String,
    @SerializedName("is_suspend")
    val isSuspend: String,
    @SerializedName("location_id")
    val locationId: Int,
    @SerializedName("order_tax_modal")
    val orderTaxModal: String,
    @SerializedName("pay_term_number")
    val payTermNumber: String,
    @SerializedName("pay_term_type")
    val payTermType: String,
    @SerializedName("payment")
    val payment: List<Payment>,
    @SerializedName("price_group")
    val priceGroup: Int,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("recur_interval")
    val recurInterval: String,
    @SerializedName("recur_interval_type")
    val recurIntervalType: String,
    @SerializedName("recur_repetitions")
    val recurRepetitions: String,
    @SerializedName("res_table_id")
    val resTableId: String,
    @SerializedName("res_waiter_id")
    val resWaiterId: String,
    @SerializedName("rp_redeemed")
    val rpRedeemed: String,
    @SerializedName("rp_redeemed_amount")
    val rpRedeemedAmount: String,
    @SerializedName("sale_note")
    val saleNote: String,
    @SerializedName("search_product")
    val searchProduct: String,
    @SerializedName("sell_price_tax")
    val sellPriceTax: String,
    @SerializedName("shipping_address")
    val shippingAddress: String,
    @SerializedName("shipping_address_modal")
    val shippingAddressModal: String,
    @SerializedName("shipping_charges")
    val shippingCharges: String,
    @SerializedName("shipping_charges_modal")
    val shippingChargesModal: String,
    @SerializedName("shipping_details")
    val shippingDetails: String,
    @SerializedName("shipping_details_modal")
    val shippingDetailsModal: String,
    @SerializedName("shipping_status")
    val shippingStatus: String,
    @SerializedName("shipping_status_modal")
    val shippingStatusModal: String,
    @SerializedName("size")
    val size: String,
    @SerializedName("staff_note")
    val staffNote: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("sub_type")
    val subType: String,
    @SerializedName("subscription_repeat_on")
    val subscriptionRepeatOn: String,
    @SerializedName("tax_calculation_amount")
    val taxCalculationAmount: String,
    @SerializedName("tax_rate_id")
    val taxRateId: String,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("types_of_service_id")
    val typesOfServiceId: String,
    @SerializedName("types_of_service_price_group")
    val typesOfServicePriceGroup: String
)