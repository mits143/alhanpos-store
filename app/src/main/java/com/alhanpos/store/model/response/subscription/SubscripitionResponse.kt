package com.alhanpos.store.model.response.subscription


import com.google.gson.annotations.SerializedName

data class SubscripitionResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>
)