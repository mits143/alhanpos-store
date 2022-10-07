package com.alhanpos.store.repo

import com.alhanpos.store.model.response.PaymentAccountResponse
import com.alhanpos.store.model.response.PaymentMethodResponse
import com.alhanpos.store.model.response.dashboard.DashboardResponse
import com.alhanpos.store.model.response.dashboard.graph.DashboardGraphResponse
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
        token: String, start: String, end: String, location_id: String
    ): Response<DashboardResponse> = apiHelper.dashboard(token, start, end, location_id)

    suspend fun dashboard_graph(
        token: String, currency_id: String, business_id: String
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

    suspend fun categoryList(
        token: String
    ) = apiHelper.categoryList(token)

    suspend fun addUpdateCategory(
        token: String,
        id: String,
        name: String,
        short_code: String,
        category_type: String,
        description: String,
        add_as_sub_cat: String
    ) = apiHelper.addUpdateCategory(
        token,
        id,
        name,
        short_code,
        category_type,
        description,
        add_as_sub_cat
    )

    suspend fun deleteCategory(
        token: String, id: String
    ) = apiHelper.deleteCategory(token, id)

    suspend fun brandList(
        token: String
    ) = apiHelper.brandList(token)

    suspend fun addUpdateBrand(
        token: String,
        id: String,
        name: String,
        description: String,
        add_as_sub_cat: String
    ) = apiHelper.addUpdateBrand(
        token,
        id,
        name,
        description,
        add_as_sub_cat
    )

    suspend fun deleteBrand(
        token: String, id: String
    ) = apiHelper.deleteBrand(token, id)

    suspend fun paymentAccounts(
        token: String,
    ): Response<PaymentAccountResponse> =
        apiHelper.paymentAccounts(token)

    suspend fun paymentMethods(
        token: String,
    ): Response<PaymentMethodResponse> =
        apiHelper.paymentMethods(token)
}