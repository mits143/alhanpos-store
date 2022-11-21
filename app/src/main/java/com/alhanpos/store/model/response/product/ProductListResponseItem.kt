package com.alhanpos.store.model.response.product


import com.google.gson.annotations.SerializedName

data class ProductListResponseItem(
    @SerializedName("enable_stock")
    val enableStock: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("qty_available")
    val qtyAvailable: String,
    @SerializedName("selling_price")
    val sellingPrice: String,
    @SerializedName("sub_sku")
    val subSku: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("variation")
    val variation: String,
    @SerializedName("variation_id")
    val variationId: String,

    var isAdded: Boolean,
    var quantity: Int,
    var price: String
) : java.io.Serializable