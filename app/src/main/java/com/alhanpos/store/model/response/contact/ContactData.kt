package com.alhanpos.store.model.response.contact

data class ContactData(
    val address_line_1: String,
    val address_line_2: String,
    val alternate_number: Any,
    val balance: String,
    val business_id: String,
    val city: String,
    val contact_id: String,
    val contact_status: String,
    val converted_by: Any,
    val converted_on: Any,
    val country: String,
    val created_at: String,
    val created_by: String,
    val credit_limit: String,
    val crm_life_stage: Any,
    val crm_source: Any,
    val custom_field1: String,
    val custom_field10: Any,
    val custom_field2: Any,
    val custom_field3: Any,
    val custom_field4: Any,
    val custom_field5: Any,
    val custom_field6: Any,
    val custom_field7: Any,
    val custom_field8: Any,
    val custom_field9: Any,
    val customer_group_id: String,
    val deleted_at: Any,
    val dob: Any,
    val email: String,
    val export_custom_field_1: Any,
    val export_custom_field_2: Any,
    val export_custom_field_3: Any,
    val export_custom_field_4: Any,
    val export_custom_field_5: Any,
    val export_custom_field_6: Any,
    val first_name: String,
    val id: Int,
    val is_default: String,
    val is_export: String,
    val landline: Any,
    val last_name: String,
    val middle_name: String,
    val mobile: String,
    val name: String,
    val pay_term_number: Any,
    val pay_term_type: Any,
    val position: Any,
    val prefix: String,
    val shipping_address: Any,
    val shipping_custom_field_details: Any,
    val state: String,
    val supplier_business_name: String,
    val tax_number: String,
    val total_rp: String,
    val total_rp_expired: String,
    val total_rp_used: String,
    val type: String,
    val updated_at: String,
    val zip_code: String
)