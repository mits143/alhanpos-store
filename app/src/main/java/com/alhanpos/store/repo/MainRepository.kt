package com.alhanpos.store.repo

import com.alhanpos.store.model.response.dashboard.graph.DashboardGraphResponse
import com.alhanpos.store.model.response.dashboard.DashboardResponse
import com.alhanpos.store.networking.ApiHelper
import retrofit2.Response

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun login(
        grant_type: String,
        client_id: String,
        client_secret: String,
        username: String,
        password: String
    ) = apiHelper.login(grant_type, client_id, client_secret, username, password)

    suspend fun dashboard(
        token: String,
        start: String,
        end: String,
        location_id: String
    ): Response<DashboardResponse> = apiHelper.dashboard(token, start, end, location_id)

    suspend fun dashboard_graph(
        token: String,
        currency_id: String,
        business_id: String
    ): Response<DashboardGraphResponse> = apiHelper.dashboard_graph(token, currency_id, business_id)

    suspend fun location(
        token: String
    ) = apiHelper.location(token)

    suspend fun contactList(
        token: String
    ) = apiHelper.contactList(token)

    suspend fun productList(
        token: String
    ) = apiHelper.productList(token)
}