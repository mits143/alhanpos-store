package com.alhanpos.store.networking

import com.alhanpos.store.model.response.category.CategoryResponse
import com.alhanpos.store.model.response.contact.ContactListResponse
import com.alhanpos.store.model.response.dashboard.DashboardResponse
import com.alhanpos.store.model.response.dashboard.graph.DashboardGraphResponse
import com.alhanpos.store.model.response.location.LocationResponse
import com.alhanpos.store.model.response.login.LoginResponse
import com.alhanpos.store.model.response.product.ProductListResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Field

interface ApiHelper {

    suspend fun login(
        grant_type: String,
        client_id: String,
        client_secret: String,
        username: String,
        password: String
    ): Response<LoginResponse>

    suspend fun dashboard(
        token: String,
        start: String,
        end: String,
        location_id: String
    ): Response<DashboardResponse>

    suspend fun dashboard_graph(
        token: String,
        currency_id: String,
        business_id: String
    ): Response<DashboardGraphResponse>

    suspend fun location(
        token: String
    ): Response<LocationResponse>

    suspend fun contactList(
        token: String
    ): Response<ContactListResponse>

    suspend fun productList(
        token: String
    ): Response<ProductListResponse>

    suspend fun categoryList(
        token: String
    ): Response<CategoryResponse>

    suspend fun addUpdateCategory(
        token: String,
        id: String,
        name: String,
        short_code: String,
        category_type: String,
        description: String,
        add_as_sub_cat: String
    ): Response<JsonObject>

    suspend fun deleteCategory(
        token: String,
        id: String
    ): Response<JsonObject>
}