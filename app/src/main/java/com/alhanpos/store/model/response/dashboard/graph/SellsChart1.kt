package com.alhanpos.store.model.response.dashboard.graph

data class SellsChart1(
    val api_url: String,
    val container: String,
    val dataset: String,
    val datasets: ArrayList<Dataset>,
    val height: Int,
    val id: String,
    val labels: ArrayList<String>,
    val loader: Boolean,
    val loaderColor: String,
    val options: Options,
    val plugins: ArrayList<Any>,
    val pluginsViews: ArrayList<Any>,
    val script: String,
    val scriptAttributes: ScriptAttributes,
    val type: String,
    val width: Any
)