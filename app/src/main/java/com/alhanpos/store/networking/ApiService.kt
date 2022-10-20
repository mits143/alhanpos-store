package com.alhanpos.store.networking

import com.alhanpos.store.model.request.payment.PaymentRequest
import com.alhanpos.store.model.response.account.PaymentAccountResponse
import com.alhanpos.store.model.response.brand.BrandResponse
import com.alhanpos.store.model.response.category.CategoryResponse
import com.alhanpos.store.model.response.contact.ContactListResponse
import com.alhanpos.store.model.response.dashboard.DashboardResponse
import com.alhanpos.store.model.response.dashboard.graph.DashboardGraphResponse
import com.alhanpos.store.model.response.location.LocationResponse
import com.alhanpos.store.model.response.login.LoginResponse
import com.alhanpos.store.model.response.method.PaymentMethodResponse
import com.alhanpos.store.model.response.product.ProductListResponse
import com.alhanpos.store.util.Constants.ADDUPDATEBRAND
import com.alhanpos.store.util.Constants.ADDUPDATECATEGORY
import com.alhanpos.store.util.Constants.ADDUPDATEPRODUCT
import com.alhanpos.store.util.Constants.BRANDLIST
import com.alhanpos.store.util.Constants.CATEGORYLIST
import com.alhanpos.store.util.Constants.CONTACTLIST
import com.alhanpos.store.util.Constants.DASHBOARD
import com.alhanpos.store.util.Constants.DASHBOARD_GRAPH
import com.alhanpos.store.util.Constants.DELETEBRAND
import com.alhanpos.store.util.Constants.DELETECATEGORY
import com.alhanpos.store.util.Constants.FINALIZEPAYMENT
import com.alhanpos.store.util.Constants.LOCATION
import com.alhanpos.store.util.Constants.LOGIN
import com.alhanpos.store.util.Constants.PAYMENT_ACCOUNTS
import com.alhanpos.store.util.Constants.PAYMENT_METHODS
import com.alhanpos.store.util.Constants.PRODUCTLIST
import com.alhanpos.store.util.Constants.UNITSLIST
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST(LOGIN)
    suspend fun login(
        @Field(value = "grant_type", encoded = false) grant_type: String,
        @Field(value = "client_id", encoded = false) client_id: String,
        @Field(value = "client_secret", encoded = false) client_secret: String,
        @Field(value = "username", encoded = false) username: String,
        @Field(value = "password", encoded = false) password: String
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

    @GET(PAYMENT_ACCOUNTS)
    suspend fun paymentAccounts(
        @Header("Authorization") token: String
    ): Response<PaymentAccountResponse>

    @GET(PAYMENT_METHODS)
    suspend fun paymentMethods(
        @Header("Authorization") token: String
    ): Response<PaymentMethodResponse>

    @GET(UNITSLIST)
    suspend fun unitsList(
        @Header("Authorization") token: String
    ): Response<CategoryResponse>

    @GET(CATEGORYLIST)
    suspend fun categoryList(
        @Header("Authorization") token: String
    ): Response<CategoryResponse>

    @FormUrlEncoded
    @POST(ADDUPDATECATEGORY)
    suspend fun addUpdateCategory(
        @Header("Authorization") token: String,
        @Field(value = "id", encoded = false) id: String,
        @Field(value = "name", encoded = false) name: String,
        @Field(value = "short_code", encoded = false) short_code: String,
        @Field(value = "category_type", encoded = false) category_type: String,
        @Field(value = "description", encoded = false) description: String,
        @Field(value = "add_as_sub_cat", encoded = false) add_as_sub_cat: String
    ): Response<JsonObject>

    @FormUrlEncoded
    @POST(DELETECATEGORY)
    suspend fun deleteCategory(
        @Header("Authorization") token: String, @Field(value = "id", encoded = false) id: String
    ): Response<JsonObject>

    @GET(BRANDLIST)
    suspend fun brandList(
        @Header("Authorization") token: String
    ): Response<BrandResponse>

    @FormUrlEncoded
    @POST(ADDUPDATEBRAND)
    suspend fun addUpdateBrand(
        @Header("Authorization") token: String,
        @Field(value = "id", encoded = false) id: String,
        @Field(value = "name", encoded = false) name: String,
        @Field(value = "description", encoded = false) description: String,
        @Field(value = "use_for_repair", encoded = false) use_for_repair: String
    ): Response<JsonObject>

    @FormUrlEncoded
    @POST(DELETEBRAND)
    suspend fun deleteBrand(
        @Header("Authorization") token: String, @Field(value = "id", encoded = false) id: String
    ): Response<JsonObject>

    @FormUrlEncoded
    @POST(ADDUPDATEPRODUCT)
    suspend fun addUpdateProduct(
        @Header("Authorization") token: String,
        @Field(value = "name", encoded = false) name: String,
        @Field(value = "brand_id", encoded = false) brand_id: String,
        @Field(value = "category_id", encoded = false) category_id: String,
        @Field(value = "unit_id", encoded = false) unit_id: String,
        @Field(value = "selling_price", encoded = false) selling_price: String
    ): Response<JsonObject>

    @POST(FINALIZEPAYMENT)
    suspend fun finalizePayment(
        @Header("Authorization") token: String, @Body jsonObject: PaymentRequest
    ): Response<JsonObject>

}