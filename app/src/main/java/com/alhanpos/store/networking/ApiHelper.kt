package com.alhanpos.store.networking

import com.alhanpos.store.model.response.contact.ContactListResponse
import com.alhanpos.store.model.response.location.LocationResponse
import com.alhanpos.store.model.response.login.LoginResponse
import com.alhanpos.store.model.response.product.ProductListResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun login(
        grant_type: String,
        client_id: String,
        client_secret: String,
        username: String,
        password: String
    ): Response<LoginResponse>

    suspend fun location(
        token: String
    ): Response<LocationResponse>

    suspend fun contactList(
        token: String
    ): Response<ContactListResponse>

    suspend fun productList(
        token: String
    ): Response<ProductListResponse>
}