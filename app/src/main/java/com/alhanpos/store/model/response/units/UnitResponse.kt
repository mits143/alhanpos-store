package com.alhanpos.store.model.response.units


import com.google.gson.annotations.SerializedName

data class UnitResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>
)