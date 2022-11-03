package com.alhanpos.store.networking

import com.alhanpos.store.model.request.payment.PaymentRequest
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
import com.google.gson.JsonObject
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

    override suspend fun dashboard(
        token: String, start: String, end: String, location_id: String
    ): Response<DashboardResponse> = apiService.dashboard(token, start, end, location_id)

    override suspend fun dashboard_graph(
        token: String, currency_id: String, business_id: String
    ): Response<DashboardGraphResponse> =
        apiService.dashboard_graph(token, currency_id, business_id)

    override suspend fun location(
        token: String
    ): Response<LocationResponse> = apiService.location(token)

    override suspend fun contactList(
        token: String
    ): Response<ContactListResponse> = apiService.contactList(token)

    override suspend fun supplierList(
        token: String
    ): Response<ContactListResponse> = apiService.supplierList(token)

    override suspend fun productList(
        token: String
    ): Response<ProductListResponse> = apiService.productList(token)

    override suspend fun categoryList(
        token: String
    ): Response<CategoryResponse> = apiService.categoryList(token)

    override suspend fun addUpdateCategory(
        token: String,
        id: String,
        name: String,
        short_code: String,
        category_type: String,
        description: String,
        add_as_sub_cat: String
    ): Response<JsonObject> = apiService.addUpdateCategory(
        token, id, name, short_code, category_type, description, add_as_sub_cat
    )

    override suspend fun deleteCategory(
        token: String, id: String
    ): Response<JsonObject> = apiService.deleteCategory(token, id)

    override suspend fun brandList(
        token: String,
    ): Response<BrandResponse> = apiService.brandList(token)

    override suspend fun addUpdateBrand(
        token: String, id: String, name: String, description: String, add_as_sub_cat: String
    ): Response<JsonObject> = apiService.addUpdateBrand(
        token, id, name, description, add_as_sub_cat
    )

    override suspend fun deleteBrand(
        token: String, id: String
    ): Response<JsonObject> = apiService.deleteBrand(token, id)

    override suspend fun paymentAccounts(
        token: String,
    ): Response<PaymentAccountResponse> = apiService.paymentAccounts(token)

    override suspend fun paymentMethods(
        token: String,
    ): Response<PaymentMethodResponse> = apiService.paymentMethods(token)

    override suspend fun addUpdateProduct(
        token: String,
        name: String,
        brand_id: String,
        category_id: String,
        unit_id: String,
        selling_price: String,
    ): Response<JsonObject> = apiService.addUpdateProduct(
        token, name, brand_id, category_id, unit_id, selling_price
    )

    override suspend fun finalizePayment(
        token: String,
        jsonObject: PaymentRequest
    ): Response<JsonObject> = apiService.finalizePayment(
        token, jsonObject
    )

    override suspend fun stocktransfer(
        token: String
    ): Response<StockTransferResponse> = apiService.stocktransfer(
        token
    )

    override suspend fun expenses(
        token: String
    ): Response<ExpensesResponse> = apiService.expenses(
        token
    )

    override suspend fun sells(
        token: String
    ): Response<SellResponse> = apiService.sells(
        token
    )

    override suspend fun purchaseorders(
        token: String
    ): Response<PurchaseOrderResponse> = apiService.purchaseorders(
        token
    )

    override suspend fun stockadjustments(
        token: String
    ): Response<StockAdjustmentResponse> = apiService.stockadjustments(
        token
    )

    override suspend fun subscriptions(
        token: String
    ): Response<SubscripitionResponse> = apiService.subscriptions(
        token
    )

}