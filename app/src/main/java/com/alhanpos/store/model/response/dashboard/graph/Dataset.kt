package com.alhanpos.store.model.response.dashboard.graph

data class Dataset(
    val name: String,
    val options: List<Any>,
    val specialDatasets: List<String>,
    val type: String,
    val undefinedColor: String,
    val values: List<Int>
)