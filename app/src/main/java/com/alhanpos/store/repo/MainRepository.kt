package com.alhanpos.store.repo

import com.alhanpos.store.model.request.payment.PaymentRequest
import com.alhanpos.store.model.response.dashboard.DashboardResponse
import com.alhanpos.store.model.response.dashboard.graph.DashboardGraphResponse
import com.alhanpos.store.model.response.expenses.ExpensesResponse
import com.alhanpos.store.model.response.purchaseorder.PurchaseOrderResponse
import com.alhanpos.store.model.response.sell.SellResponse
import com.alhanpos.store.model.response.stockadjustment.StockAdjustmentResponse
import com.alhanpos.store.model.response.stocktransfer.StockTransferResponse
import com.alhanpos.store.model.response.subscription.SubscripitionResponse
import com.alhanpos.store.networking.ApiHelper
import com.google.gson.JsonObject
import retrofit2.Response

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun login(
        grant_type: String,
        client_id: String,
        client_secret: String,
        username: String,
        password: String
    ) = apiHelper.login(grant_type, client_id, client_secret, username, password)

    suspend fun dashboard(
        token: String, start: String, end: String, location_id: String
    ): Response<DashboardResponse> = apiHelper.dashboard(token, start, end, location_id)

    suspend fun dashboard_graph(
        token: String, currency_id: String, business_id: String
    ): Response<DashboardGraphResponse> = apiHelper.dashboard_graph(token, currency_id, business_id)

    suspend fun location(
        token: String
    ) = apiHelper.location(token)

    suspend fun contactList(
        token: String
    ) = apiHelper.contactList(token)

    suspend fun supplierList(
        token: String
    ) = apiHelper.supplierList(token)

    suspend fun productList(
        token: String
    ) = apiHelper.productList(token)

    suspend fun categoryList(
        token: String
    ) = apiHelper.categoryList(token)

    suspend fun addUpdateCategory(
        token: String,
        id: String,
        name: String,
        short_code: String,
        category_type: String,
        description: String,
        add_as_sub_cat: String
    ) = apiHelper.addUpdateCategory(
        token, id, name, short_code, category_type, description, add_as_sub_cat
    )

    suspend fun deleteCategory(
        token: String, id: String
    ) = apiHelper.deleteCategory(token, id)

    suspend fun brandList(
        token: String
    ) = apiHelper.brandList(token)

    suspend fun addUpdateBrand(
        token: String, id: String, name: String, description: String, add_as_sub_cat: String
    ) = apiHelper.addUpdateBrand(
        token, id, name, description, add_as_sub_cat
    )

    suspend fun deleteBrand(
        token: String, id: String
    ) = apiHelper.deleteBrand(token, id)

    suspend fun paymentAccounts(
        token: String,
    ) = apiHelper.paymentAccounts(token)

    suspend fun paymentMethods(
        token: String,
    ) = apiHelper.paymentMethods(token)

    suspend fun addUpdateProduct(
        token: String,
        name: String,
        brand_id: String,
        category_id: String,
        unit_id: String,
        selling_price: String
    ) = apiHelper.addUpdateProduct(
        token, name, brand_id, category_id, unit_id, selling_price
    )

    suspend fun finalizePayment(
        token: String, jsonObject: PaymentRequest
    ): Response<JsonObject> = apiHelper.finalizePayment(
        token, jsonObject
    )

    suspend fun stocktransfer(
        token: String
    ): Response<StockTransferResponse> = apiHelper.stocktransfer(
        token
    )

    suspend fun expenses(
        token: String
    ): Response<ExpensesResponse> = apiHelper.expenses(
        token
    )

    suspend fun sells(
        token: String
    ): Response<SellResponse> = apiHelper.sells(
        token
    )

    suspend fun purchaseorders(
        token: String
    ): Response<PurchaseOrderResponse> = apiHelper.purchaseorders(
        token
    )

    suspend fun stockadjustments(
        token: String
    ): Response<StockAdjustmentResponse> = apiHelper.stockadjustments(
        token
    )

    suspend fun subscriptions(
        token: String
    ): Response<SubscripitionResponse> = apiHelper.subscriptions(
        token
    )
}