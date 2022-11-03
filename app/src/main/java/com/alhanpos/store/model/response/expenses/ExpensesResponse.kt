package com.alhanpos.store.model.response.expenses


import com.google.gson.annotations.SerializedName

data class ExpensesResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>
)