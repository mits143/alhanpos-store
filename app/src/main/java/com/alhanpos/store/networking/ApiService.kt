package com.alhanpos.store.networking

import com.alhanpos.store.model.LoginResponse
import com.alhanpos.store.util.Constants.LOGIN
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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

}