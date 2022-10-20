package com.alhanpos.store.model.response.product

import java.io.Serializable

data class ProductData(
    var value: Float = 0f,
    var price: String? = null,
    var quantity: Int = 0,
    val alert_quantity: String? = null,
    val barcode_type: String? = null,
    val brand: Brand,
    val business_id: String? = null,
    val category: Any? = null,
    val created_by: String? = null,
    val enable_sr_no: String? = null,
    val enable_stock: String? = null,
    val expiry_period: String? = null,
    val expiry_period_type: String? = null,
    val id: Int = 0,
    val image: String? = null,
    val image_url: String? = null,
    val is_inactive: String? = null,
    val name: String? = null,
    val not_for_selling: String? = null,
    val product_custom_field1: String? = null,
    val product_custom_field2: String? = null,
    val product_custom_field3: String? = null,
    val product_custom_field4: String? = null,
    val product_description: String? = null,
    val product_locations: List<ProductLocation>,
    val product_tax: Any? = null,
    val product_variations: List<ProductVariation>,
    val repair_model_id: String? = null,
    val sku: String? = null,
    val sub_category: Any? = null,
    val sub_unit_ids: String? = null,
    val type: String? = null,
    val unit: Unit? = null,
    val warranty_id: String? = null,
    val weight: String? = null,
    val woocommerce_disable_sync: String? = null,
    val woocommerce_media_id: String? = null,
    val woocommerce_product_id: String? = null,
    var isAdded: Boolean = false
) : Serializable