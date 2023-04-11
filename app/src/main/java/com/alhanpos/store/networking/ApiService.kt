package com.alhanpos.store.networking

import com.alhanpos.store.model.request.payment.PaymentRequest
import com.alhanpos.store.model.request.stockAdjustment.StockAdjustmentRequest
import com.alhanpos.store.model.request.stockTransfer.StockTransferRequest
import com.alhanpos.store.model.response.DetailResponse
import com.alhanpos.store.model.response.account.PaymentAccountResponse
import com.alhanpos.store.model.response.brand.BrandResponse
import com.alhanpos.store.model.response.category.CategoryResponse
import com.alhanpos.store.model.response.contact.ContactListResponse
import com.alhanpos.store.model.response.dashboard.DashboardResponse
import com.alhanpos.store.model.response.dashboard.graph.DashboardGraphResponse
import com.alhanpos.store.model.response.expenses.ExpensesResponse
import com.alhanpos.store.model.response.location.LocationResponse
import com.alhanpos.store.model.response.login.LoginResponse
import com.alhanpos.store.model.response.method.PaymentMethodResponse
import com.alhanpos.store.model.response.product.ProductListResponse
import com.alhanpos.store.model.response.purchase.PurchaseResponse
import com.alhanpos.store.model.response.purchaseorder.PurchaseOrderResponse
import com.alhanpos.store.model.response.sell.SellResponse
import com.alhanpos.store.model.response.stockadjustment.StockAdjustmentResponse
import com.alhanpos.store.model.response.stocktransfer.StockTransferResponse
import com.alhanpos.store.model.response.subscription.SubscripitionResponse
import com.alhanpos.store.model.response.units.UnitResponse
import com.alhanpos.store.util.Constants.ADDUPDATEBRAND
import com.alhanpos.store.util.Constants.ADDUPDATECATEGORY
import com.alhanpos.store.util.Constants.ADDUPDATEPRODUCT
import com.alhanpos.store.util.Constants.ADD_CUSTOMER
import com.alhanpos.store.util.Constants.ADD_EXPENSE
import com.alhanpos.store.util.Constants.ADD_PURCHASE
import com.alhanpos.store.util.Constants.ADD_STOCKADJUSTMENT
import com.alhanpos.store.util.Constants.ADD_STOCKTRANSFER
import com.alhanpos.store.util.Constants.ADD_SUPPLIER
import com.alhanpos.store.util.Constants.BRANDLIST
import com.alhanpos.store.util.Constants.CATEGORYLIST
import com.alhanpos.store.util.Constants.CONTACTLIST
import com.alhanpos.store.util.Constants.DASHBOARD
import com.alhanpos.store.util.Constants.DASHBOARD_GRAPH
import com.alhanpos.store.util.Constants.DELETEBRAND
import com.alhanpos.store.util.Constants.DELETECATEGORY
import com.alhanpos.store.util.Constants.EXPENSES
import com.alhanpos.store.util.Constants.FINALIZEPAYMENT
import com.alhanpos.store.util.Constants.INVOICE
import com.alhanpos.store.util.Constants.LOCATION
import com.alhanpos.store.util.Constants.LOGIN
import com.alhanpos.store.util.Constants.PAYMENT_ACCOUNTS
import com.alhanpos.store.util.Constants.PAYMENT_METHODS
import com.alhanpos.store.util.Constants.PRODUCTLIST
import com.alhanpos.store.util.Constants.PURCHASE
import com.alhanpos.store.util.Constants.PURCHASEORDERS
import com.alhanpos.store.util.Constants.SELLS
import com.alhanpos.store.util.Constants.STOCKADJUSTMENTS
import com.alhanpos.store.util.Constants.STOCKTRANSFER
import com.alhanpos.store.util.Constants.SUBSCRIPTIONS
import com.alhanpos.store.util.Constants.SUPPLIERLIST
import com.alhanpos.store.util.Constants.UNITSLIST
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
    ): Response<LocationResponse>

    @GET(CONTACTLIST)
    suspend fun contactList(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
    ): Response<ContactListResponse>

    @GET(SUPPLIERLIST)
    suspend fun supplierList(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
    ): Response<ContactListResponse>

    @GET(PRODUCTLIST)
    suspend fun productList(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("sku") sku: String,
        @Query("page") page: String
    ): Response<ProductListResponse>

    @GET(PAYMENT_ACCOUNTS)
    suspend fun paymentAccounts(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
    ): Response<PaymentAccountResponse>

    @GET(PAYMENT_METHODS)
    suspend fun paymentMethods(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
    ): Response<PaymentMethodResponse>

    @GET(UNITSLIST)
    suspend fun unitsList(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
    ): Response<UnitResponse>

    @GET(CATEGORYLIST)
    suspend fun categoryList(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
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
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
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
        @Field(value = "selling_price", encoded = false) selling_price: String,
//        @Field(value = "tax", encoded = false) tax: String,
        @Field(value = "sku", encoded = false) sku: String,
        @Field(value = "alert_quantity", encoded = false) alert_quantity: String
    ): Response<JsonObject>

    @FormUrlEncoded
    @POST(ADD_SUPPLIER)
    suspend fun add_supplier(
        @Header("Authorization") token: String,
        @Field(value = "name", encoded = false) name: String,
        @Field(value = "email", encoded = false) email: String,
        @Field(value = "mobile", encoded = false) mobile: String,
        @Field(value = "balance", encoded = false) balance: String,
        @Field(value = "id", encoded = false) id: String,
        @Field(value = "due", encoded = false) due: String
    ): Response<JsonObject>

    @FormUrlEncoded
    @POST(ADD_CUSTOMER)
    suspend fun add_customer(
        @Header("Authorization") token: String,
        @Field(value = "name", encoded = false) name: String,
        @Field(value = "email", encoded = false) email: String,
        @Field(value = "mobile", encoded = false) mobile: String,
        @Field(value = "balance", encoded = false) balance: String,
        @Field(value = "id", encoded = false) id: String,
        @Field(value = "due", encoded = false) due: String
    ): Response<JsonObject>

    @POST(FINALIZEPAYMENT)
    suspend fun finalizePayment(
        @Header("Authorization") token: String, @Body jsonObject: PaymentRequest
    ): Response<JsonObject>

    @GET(STOCKTRANSFER)
    suspend fun stocktransfer(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
    ): Response<StockTransferResponse>

    @GET(EXPENSES)
    suspend fun expenses(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
    ): Response<ExpensesResponse>

    @GET(SELLS)
    suspend fun sells(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
    ): Response<SellResponse>

    @GET(PURCHASEORDERS)
    suspend fun purchaseorders(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
    ): Response<PurchaseOrderResponse>

    @GET(PURCHASE)
    suspend fun purchase(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
    ): Response<PurchaseResponse>

    @GET(STOCKADJUSTMENTS)
    suspend fun stockadjustments(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
    ): Response<StockAdjustmentResponse>

    @GET(SUBSCRIPTIONS)
    suspend fun subscriptions(
        @Header("Authorization") token: String,
        @Query("term") term: String,
        @Query("page") page: String
    ): Response<SubscripitionResponse>

    @POST(ADD_STOCKTRANSFER)
    suspend fun add_stocktransfer(
        @Header("Authorization") token: String, @Body jsonObject: StockTransferRequest
    ): Response<JsonObject>

    @POST(ADD_STOCKADJUSTMENT)
    suspend fun add_stockadjustment(
        @Header("Authorization") token: String, @Body jsonObject: StockAdjustmentRequest
    ): Response<JsonObject>

    @Multipart
    @POST(ADD_PURCHASE)
    suspend fun add_purchase(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("data") data: RequestBody
    ): Response<JsonObject>

    @Multipart
    @POST(ADD_EXPENSE)
    suspend fun add_expense(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("data") data: RequestBody
    ): Response<JsonObject>

    @FormUrlEncoded
    @POST(INVOICE)
    suspend fun invoice(
        @Header("Authorization") token: String,
        @Field(value = "transaction_id", encoded = false) transaction_id: String,
    ): Response<DetailResponse>
}