package com.alhanpos.store.model.response.dashboard.graph

data class SellsChart2(
    val api_url: String,
    val container: String,
    val dataset: String,
    val datasets: ArrayList<DatasetX>,
    val height: Int,
    val id: String,
    val labels: ArrayList<String>,
    val loader: Boolean,
    val loaderColor: String,
    val options: OptionsX,
    val plugins: List<Any>,
    val pluginsViews: List<Any>,
    val script: String,
    val scriptAttributes: ScriptAttributes,
    val type: String,
    val width: Any
)