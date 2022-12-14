package com.alhanpos.store.model.response.location

data class LocationData(
    val alternate_number: Any,
    val business_id: String,
    val city: String,
    val country: String,
    val created_at: String,
    val custom_field1: Any,
    val custom_field2: Any,
    val custom_field3: Any,
    val custom_field4: Any,
    val deleted_at: Any,
    val email: Any,
    val featured_products: List<String>,
    val id: Int,
    val invoice_layout_id: String,
    val invoice_scheme_id: String,
    val is_active: String,
    val landmark: String,
    val location_id: String,
    val mobile: String,
    val name: String,
    val payment_methods: List<PaymentMethod>,
    val print_receipt_on_invoice: String,
    val printer_id: Any,
    val receipt_printer_type: String,
    val sale_invoice_layout_id: String,
    val selling_price_group_id: String,
    val state: String,
    val updated_at: String,
    val website: String,
    val zip_code: String
)