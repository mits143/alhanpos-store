package com.alhanpos.store.model.response.product

import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("data")
    val `data`: ArrayList<ProductListResponseItem>
) : java.io.Serializable