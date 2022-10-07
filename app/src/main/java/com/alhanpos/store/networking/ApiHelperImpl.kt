package com.alhanpos.store.networking

import com.alhanpos.store.model.response.PaymentAccountResponse
import com.alhanpos.store.model.response.PaymentMethodResponse
import com.alhanpos.store.model.response.brand.BrandResponse
import com.alhanpos.store.model.response.category.CategoryResponse
import com.alhanpos.store.model.response.contact.ContactListResponse
import com.alhanpos.store.model.response.dashboard.DashboardResponse
import com.alhanpos.store.model.response.dashboard.graph.DashboardGraphResponse
import com.alhanpos.store.model.response.location.LocationResponse
import com.alhanpos.store.model.response.login.LoginResponse
import com.alhanpos.store.model.response.product.ProductListResponse
import com.google.gson.JsonObject
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun login(
        grant_type: String,
        client_id: String,
        client_secret: String,
        username: String,
        password: String
    ): Response<LoginResponse> =
        apiService.login(grant_type, client_id, client_secret, username, password)

    override suspend fun dashboard(
        token: String,
        start: String,
        end: String,
        location_id: String
    ): Response<DashboardResponse> = apiService.dashboard(token, start, end, location_id)

    override suspend fun dashboard_graph(
        token: String,
        currency_id: String,
        business_id: String
    ): Response<DashboardGraphResponse> =
        apiService.dashboard_graph(token, currency_id, business_id)

    override suspend fun location(
        token: String
    ): Response<LocationResponse> =
        apiService.location(token)

    override suspend fun contactList(
        token: String
    ): Response<ContactListResponse> =
        apiService.contactList(token)

    override suspend fun productList(
        token: String
    ): Response<ProductListResponse> =
        apiService.productList(token)

    override suspend fun categoryList(
        token: String
    ): Response<CategoryResponse> =
        apiService.categoryList(token)

    override suspend fun addUpdateCategory(
        token: String,
        id: String,
        name: String,
        short_code: String,
        category_type: String,
        description: String,
        add_as_sub_cat: String
    ): Response<JsonObject> =
        apiService.addUpdateCategory(
            token,
            id,
            name,
            short_code,
            category_type,
            description,
            add_as_sub_cat
        )

    override suspend fun deleteCategory(
        token: String,
        id: String
    ): Response<JsonObject> =
        apiService.deleteCategory(token, id)

    override suspend fun brandList(
        token: String,
    ): Response<BrandResponse> =
        apiService.brandList(token)

    override suspend fun addUpdateBrand(
        token: String,
        id: String,
        name: String,
        description: String,
        add_as_sub_cat: String
    ): Response<JsonObject> =
        apiService.addUpdateBrand(
            token,
            id,
            name,
            description,
            add_as_sub_cat
        )

    override suspend fun deleteBrand(
        token: String,
        id: String
    ): Response<JsonObject> =
        apiService.deleteBrand(token, id)

    override suspend fun paymentAccounts(
        token: String,
    ): Response<PaymentAccountResponse> =
        apiService.paymentAccounts(token)

    override suspend fun paymentMethods(
        token: String,
    ): Response<PaymentMethodResponse> =
        apiService.paymentMethods(token)

}