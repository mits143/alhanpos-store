package com.alhanpos.store.networking

import com.alhanpos.store.model.LoginResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun login(
        grant_type: String,
        client_id: String,
        client_secret: String,
        username: String,
        password: String
    ): Response<LoginResponse>
}