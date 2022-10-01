package com.alhanpos.store.networking

import com.alhanpos.store.model.response.category.CategoryResponse
import com.alhanpos.store.model.response.contact.ContactListResponse
import com.alhanpos.store.model.response.dashboard.DashboardResponse
import com.alhanpos.store.model.response.dashboard.graph.DashboardGraphResponse
import com.alhanpos.store.model.response.location.LocationResponse
import com.alhanpos.store.model.response.login.LoginResponse
import com.alhanpos.store.model.response.product.ProductListResponse
import com.alhanpos.store.util.Constants.ADDUPDATECATEGORY
import com.alhanpos.store.util.Constants.CATEGORYLIST
import com.alhanpos.store.util.Constants.CONTACTLIST
import com.alhanpos.store.util.Constants.DASHBOARD
import com.alhanpos.store.util.Constants.DASHBOARD_GRAPH
import com.alhanpos.store.util.Constants.DELETECATEGORY
import com.alhanpos.store.util.Constants.LOCATION
import com.alhanpos.store.util.Constants.LOGIN
import com.alhanpos.store.util.Constants.PRODUCTLIST
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST(LOGIN)
    suspend fun login(
        @Field(
            value = "grant_type", encoded = false
        ) grant_type: String, @Field(
            value = "client_id", encoded = false
        ) client_id: String, @Field(
            value = "client_secret", encoded = false
        ) client_secret: String, @Field(
            value = "username", encoded = false
        ) username: String, @Field(
            value = "password", encoded = false
        ) password: String
    ): Response<LoginResponse>

    @GET(DASHBOARD)
    suspend fun dashboard(
        @Header("Authorization") token: String,
        @Query("start") start: String,
        @Query("end") end: String,
        @Query("location_id") location_id: String
    ): Response<DashboardResponse>

    @GET(DASHBOARD_GRAPH)
    suspend fun dashboard_graph(
        @Header("Authorization") token: String,
        @Query("currency_id") currency_id: String,
        @Query("business_id") business_id: String,
    ): Response<DashboardGraphResponse>

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

    @GET(CATEGORYLIST)
    suspend fun categoryList(
        @Header("Authorization") token: String
    ): Response<CategoryResponse>

    @FormUrlEncoded
    @POST(ADDUPDATECATEGORY)
    suspend fun addUpdateCategory(
        @Header("Authorization") token: String,
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("short_code") short_code: String,
        @Field("category_type") category_type: String,
        @Field("description") description: String,
        @Field("add_as_sub_cat") add_as_sub_cat: String
    ): Response<JsonObject>

    @FormUrlEncoded
    @POST(DELETECATEGORY)
    suspend fun deleteCategory(
        @Header("Authorization") token: String,
        @Field("id") id: String
    ): Response<JsonObject>

}