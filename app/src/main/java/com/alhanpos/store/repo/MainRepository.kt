package com.alhanpos.store.repo

import com.alhanpos.store.model.request.payment.PaymentRequest
import com.alhanpos.store.model.request.stockAdjustment.StockAdjustmentRequest
import com.alhanpos.store.model.request.stockTransfer.StockTransferRequest
import com.alhanpos.store.model.response.dashboard.DashboardResponse
import com.alhanpos.store.model.response.dashboard.graph.DashboardGraphResponse
import com.alhanpos.store.model.response.expenses.ExpensesResponse
import com.alhanpos.store.model.response.purchaseorder.PurchaseOrderResponse
import com.alhanpos.store.model.response.sell.SellResponse
import com.alhanpos.store.model.response.stockadjustment.StockAdjustmentResponse
import com.alhanpos.store.model.response.stocktransfer.StockTransferResponse
import com.alhanpos.store.model.response.subscription.SubscripitionResponse
import com.alhanpos.store.model.response.units.UnitResponse
import com.alhanpos.store.networking.ApiHelper
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        token: String, term: String, page: String
    ) = apiHelper.location(token, term, page)

    suspend fun contactList(
        token: String, term: String, page: String
    ) = apiHelper.contactList(token, term, page)

    suspend fun supplierList(
        token: String, term: String, page: String
    ) = apiHelper.supplierList(token, term, page)

    suspend fun productList(
        token: String, term: String, page: String
    ) = apiHelper.productList(token, term, page)

    suspend fun categoryList(
        token: String, term: String, page: String
    ) = apiHelper.categoryList(token, term, page)

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
        token: String, term: String, page: String
    ) = apiHelper.brandList(token, term, page)

    suspend fun addUpdateBrand(
        token: String, id: String, name: String, description: String, add_as_sub_cat: String
    ) = apiHelper.addUpdateBrand(
        token, id, name, description, add_as_sub_cat
    )

    suspend fun deleteBrand(
        token: String, id: String
    ) = apiHelper.deleteBrand(token, id)

    suspend fun paymentAccounts(
        token: String, term: String, page: String
    ) = apiHelper.paymentAccounts(token, term, page)

    suspend fun paymentMethods(
        token: String, term: String, page: String
    ) = apiHelper.paymentMethods(token, term, page)

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
    ) = apiHelper.addUpdateProduct(
        token, name, brand_id, category_id, unit_id, selling_price, /*tax,*/ sku, alert_quantity
    )

    suspend fun add_supplier(
        token: String,
        name: String,
        email: String,
        mobile: String,
        balance: String,
        id: String,
        due: String,
    ) = apiHelper.add_supplier(
        token, name, email, mobile, balance, id, due
    )

    suspend fun add_customer(
        token: String,
        name: String,
        email: String,
        mobile: String,
        balance: String,
        id: String,
        due: String,
    ) = apiHelper.add_customer(
        token, name, email, mobile, balance, id, due
    )

    suspend fun finalizePayment(
        token: String, jsonObject: PaymentRequest
    ): Response<JsonObject> = apiHelper.finalizePayment(
        token, jsonObject
    )

    suspend fun stocktransfer(
        token: String, term: String, page: String
    ): Response<StockTransferResponse> = apiHelper.stocktransfer(
        token, term, page
    )

    suspend fun expenses(
        token: String, term: String, page: String
    ): Response<ExpensesResponse> = apiHelper.expenses(
        token, term, page
    )

    suspend fun sells(
        token: String, term: String, page: String
    ): Response<SellResponse> = apiHelper.sells(
        token, term, page
    )

    suspend fun purchaseorders(
        token: String, term: String, page: String
    ): Response<PurchaseOrderResponse> = apiHelper.purchaseorders(
        token, term, page
    )

    suspend fun stockadjustments(
        token: String, term: String, page: String
    ): Response<StockAdjustmentResponse> = apiHelper.stockadjustments(
        token, term, page
    )

    suspend fun subscriptions(
        token: String, term: String, page: String
    ): Response<SubscripitionResponse> = apiHelper.subscriptions(
        token, term, page
    )

    suspend fun unitsList(
        token: String, term: String, page: String
    ): Response<UnitResponse> = apiHelper.unitsList(
        token, term, page
    )

    suspend fun add_stocktransfer(
        token: String, jsonObject: StockTransferRequest
    ): Response<JsonObject> = apiHelper.add_stocktransfer(
        token, jsonObject
    )

    suspend fun add_stockadjustment(
        token: String, jsonObject: StockAdjustmentRequest
    ): Response<JsonObject> = apiHelper.add_stockadjustment(
        token, jsonObject
    )

    suspend fun add_purchase(
        token: String,
        document: MultipartBody.Part,
        data: RequestBody
    ): Response<JsonObject> = apiHelper.add_purchase(
        token,
        document,
        data
    )

    suspend fun add_expense(
        token: String,
        document: MultipartBody.Part,
        data: RequestBody
    ): Response<JsonObject> = apiHelper.add_expense(
        token,
        document,
        data
    )
}