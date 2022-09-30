package com.alhanpos.store.model.response.dashboard.graph

data class DashboardGraphResponse(
    val all_locations: AllLocations,
    val common_settings: List<Any>,
    val is_admin: Boolean,
    val sells_chart_1: SellsChart1,
    val sells_chart_2: SellsChart2,
    val widgets: List<Any>
)