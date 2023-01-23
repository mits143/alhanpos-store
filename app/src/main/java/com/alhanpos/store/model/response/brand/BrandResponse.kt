package com.alhanpos.store.model.response.brand

import com.google.gson.annotations.SerializedName

class BrandResponse(
    @SerializedName("data")
    val `data`: ArrayList<BrandResponseItem>
) : java.io.Serializable