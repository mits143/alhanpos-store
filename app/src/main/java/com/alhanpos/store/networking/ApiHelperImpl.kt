package com.alhanpos.store.networking

import com.alhanpos.store.model.response.contact.ContactListResponse
import com.alhanpos.store.model.response.location.LocationResponse
import com.alhanpos.store.model.response.login.LoginResponse
import com.alhanpos.store.model.response.product.ProductListResponse
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

}