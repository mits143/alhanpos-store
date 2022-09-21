package com.alhanpos.store.networking

import com.alhanpos.store.model.response.contact.ContactListResponse
import com.alhanpos.store.model.response.location.LocationResponse
import com.alhanpos.store.model.response.login.LoginResponse
import com.alhanpos.store.model.response.product.ProductListResponse
import com.alhanpos.store.util.Constants.CONTACTLIST
import com.alhanpos.store.util.Constants.LOCATION
import com.alhanpos.store.util.Constants.LOGIN
import com.alhanpos.store.util.Constants.PRODUCTLIST
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST(LOGIN)
    suspend fun login(
        @Field(
            value = "grant_type",
            encoded = false
        ) grant_type: String,
        @Field(
            value = "client_id",
            encoded = false
        ) client_id: String,
        @Field(
            value = "client_secret",
            encoded = false
        ) client_secret: String,
        @Field(
            value = "username",
            encoded = false
        ) username: String,
        @Field(
            value = "password",
            encoded = false
        ) password: String
    ): Response<LoginResponse>

    @GET(LOCATION)
    suspend fun location(
        @Header("Authorization") token: String
    ): Response<LocationResponse>

    @GET(CONTACTLIST)
    suspend fun contactList(
        @Header("Authorization") token: String
    ): Response<ContactListResponse>

    @GET(PRODUCTLIST)
    suspend fun productList(
        @Header("Authorization") token: String
    ): Response<ProductListResponse>

}