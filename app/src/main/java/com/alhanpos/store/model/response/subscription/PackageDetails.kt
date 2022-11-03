package com.alhanpos.store.model.response.subscription


import com.google.gson.annotations.SerializedName

data class PackageDetails(
    @SerializedName("assetmanagement_module")
    val assetmanagementModule: String,
    @SerializedName("connector_module")
    val connectorModule: String,
    @SerializedName("crm_module")
    val crmModule: String,
    @SerializedName("essentials_module")
    val essentialsModule: String,
    @SerializedName("invoice_count")
    val invoiceCount: String,
    @SerializedName("location_count")
    val locationCount: String,
    @SerializedName("manufacturing_module")
    val manufacturingModule: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("product_count")
    val productCount: String,
    @SerializedName("productcatalogue_module")
    val productcatalogueModule: String,
    @SerializedName("project_module")
    val projectModule: String,
    @SerializedName("repair_module")
    val repairModule: String,
    @SerializedName("user_count")
    val userCount: String,
    @SerializedName("woocommerce_module")
    val woocommerceModule: String
)