package com.alhanpos.store.model.response.sell
import com.google.gson.annotations.SerializedName

data class SellResponse(
    @SerializedName("data")
    val `data`: ArrayList<SellResponseItem>
) : java.io.Serializable
