package com.alhanpos.store.networking

import com.alhanpos.store.model.request.payment.PaymentRequest
import com.alhanpos.store.model.request.stockAdjustment.StockAdjustmentRequest
import com.alhanpos.store.model.request.stockTransfer.StockTransferRequest
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
import com.alhanpos.store.model.response.purchaseorder.PurchaseOrderResponse
import com.alhanpos.store.model.response.sell.SellResponse
import com.alhanpos.store.model.response.stockadjustment.StockAdjustmentResponse
import com.alhanpos.store.model.response.stocktransfer.StockTransferResponse
import com.alhanpos.store.model.response.subscription.SubscripitionResponse
import com.alhanpos.store.model.response.units.UnitResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface ApiHelper {

    suspend fun login(
        grant_type: String,
        client_id: String,
        client_secret: String,
        username: String,
        password: String
    ): Response<LoginResponse>

    suspend fun dashboard(
        token: String, start: String, end: String, location_id: String
    ): Response<DashboardResponse>

    suspend fun dashboard_graph(
        token: String, currency_id: String, business_id: String
    ): Response<DashboardGraphResponse>

    suspend fun location(
        token: String, term: String, page: String
    ): Response<LocationResponse>

    suspend fun contactList(
        token: String, term: String, page: String
    ): Response<ContactListResponse>

    suspend fun supplierList(
        token: String, term: String, page: String
    ): Response<ContactListResponse>

    suspend fun productList(
        token: String, term: String, page: String
    ): Response<ProductListResponse>

    suspend fun categoryList(
        token: String, term: String, page: String
    ): Response<CategoryResponse>

    suspend fun addUpdateCategory(
        token: String,
        id: String,
        name: String,
        short_code: String,
        category_type: String,
        description: String,
        add_as_sub_cat: String
    ): Response<JsonObject>

    suspend fun add_supplier(
        token: String,
        name: String,
        email: String,
        mobile: String,
        balance: String,
        id: String,
        due: String
    ): Response<JsonObject>

    suspend fun add_customer(
        token: String,
        name: String,
        email: String,
        mobile: String,
        balance: String,
        id: String,
        due: String
    ): Response<JsonObject>

    suspend fun deleteCategory(
        token: String, id: String
    ): Response<JsonObject>

    suspend fun brandList(
        token: String, term: String, page: String
    ): Response<BrandResponse>

    suspend fun addUpdateBrand(
        token: String, id: String, name: String, description: String, add_as_sub_cat: String
    ): Response<JsonObject>

    suspend fun deleteBrand(
        token: String, id: String
    ): Response<JsonObject>

    suspend fun paymentAccounts(
        token: String, term: String, page: String
    ): Response<PaymentAccountResponse>

    suspend fun paymentMethods(
        token: String, term: String, page: String
    ): Response<PaymentMethodResponse>

    suspend fun addUpdateProduct(
        token: String,
        name: String,
        brand_id: String,
        category_id: String,
        unit_id: String,
        selling_price: String,
//        tax: String,
        sku: String,
        alert_quantity: String
    ): Response<JsonObject>

    suspend fun finalizePayment(
        token: String, jsonObject: PaymentRequest
    ): Response<JsonObject>

    suspend fun stocktransfer(
        token: String, term: String, page: String
    ): Response<StockTransferResponse>

    suspend fun expenses(
        token: String, term: String, page: String
    ): Response<ExpensesResponse>

    suspend fun sells(
        token: String, term: String, page: String
    ): Response<SellResponse>

    suspend fun purchaseorders(
        token: String, term: String, page: String
    ): Response<PurchaseOrderResponse>

    suspend fun stockadjustments(
        token: String, term: String, page: String
    ): Response<StockAdjustmentResponse>

    suspend fun subscriptions(
        token: String, term: String, page: String
    ): Response<SubscripitionResponse>

    suspend fun unitsList(
        token: String, term: String, page: String
    ): Response<UnitResponse>

    suspend fun add_stocktransfer(
        token: String, jsonObject: StockTransferRequest
    ): Response<JsonObject>

    suspend fun add_stockadjustment(
        token: String, jsonObject: StockAdjustmentRequest
    ): Response<JsonObject>

    suspend fun add_purchase(
        token: String,
        document: MultipartBody.Part,
        data: RequestBody,
    ): Response<JsonObject>

    suspend fun add_expense(
        token: String, document: MultipartBody.Part, data: RequestBody
    ): Response<JsonObject>
}