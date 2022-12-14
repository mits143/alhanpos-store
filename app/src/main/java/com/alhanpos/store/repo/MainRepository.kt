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
        token: String,
        term: String,
        page: String
    ) = apiHelper.location(token, term, page)

    suspend fun contactList(
        token: String,
        term: String,
        page: String
    ) = apiHelper.contactList(token, term, page)

    suspend fun supplierList(
        token: String,
        term: String,
        page: String
    ) = apiHelper.supplierList(token, term, page)

    suspend fun productList(
        token: String,
        term: String,
        page: String
    ) = apiHelper.productList(token, term, page)

    suspend fun categoryList(
        token: String,
        term: String,
        page: String
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
        token: String,
        term: String,
        page: String
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
        token: String,
        term: String,
        page: String
    ) = apiHelper.paymentAccounts(token, term, page)

    suspend fun paymentMethods(
        token: String,
        term: String,
        page: String
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
        token: String,
        term: String,
        page: String
    ): Response<StockTransferResponse> = apiHelper.stocktransfer(
        token, term, page
    )

    suspend fun expenses(
        token: String,
        term: String,
        page: String
    ): Response<ExpensesResponse> = apiHelper.expenses(
        token, term, page
    )

    suspend fun sells(
        token: String,
        term: String,
        page: String
    ): Response<SellResponse> = apiHelper.sells(
        token, term, page
    )

    suspend fun purchaseorders(
        token: String,
        term: String,
        page: String
    ): Response<PurchaseOrderResponse> = apiHelper.purchaseorders(
        token, term, page
    )

    suspend fun stockadjustments(
        token: String,
        term: String,
        page: String
    ): Response<StockAdjustmentResponse> = apiHelper.stockadjustments(
        token, term, page
    )

    suspend fun subscriptions(
        token: String,
        term: String,
        page: String
    ): Response<SubscripitionResponse> = apiHelper.subscriptions(
        token, term, page
    )

    suspend fun unitsList(
        token: String,
        term: String,
        page: String
    ): Response<UnitResponse> = apiHelper.unitsList(
        token, term, page
    )

    suspend fun add_stocktransfer(
        token: String,
        transaction_date: String,
        ref_no: String,
        status: String,
        final_total: String,
        transfer_location_id: String,
        shipping_charges: String,
        location_id: String,
        lot_no_line_id: String,
        product_id: String,
        variation_id: String,
        enable_stock: String,
        quantity: String,
        base_unit_multiplier: String,
        product_unit_id: String,
        sub_unit_id: String,
        unit_price: String,
        price: String,
        additional_notes: String
    ): Response<JsonObject> = apiHelper.add_stocktransfer(
        token,
        transaction_date,
        ref_no,
        status,
        final_total,
        transfer_location_id,
        shipping_charges,
        location_id,
        lot_no_line_id,
        product_id,
        variation_id,
        enable_stock,
        quantity,
        base_unit_multiplier,
        product_unit_id,
        sub_unit_id,
        unit_price,
        price,
        additional_notes
    )

    suspend fun add_stockadjustment(
        token: String,
        location_id: String,
        ref_no: String,
        transaction_date: String,
        adjustment_type: String,
        search_product: String,
        final_total: String,
        total_amount_recovered: String,
        additional_notes: String,
        lot_no_line_id: String,
        product_id: String,
        variation_id: String,
        enable_stock: String,
        quantity: String,
        base_unit_multiplier: String,
        product_unit_id: String,
        sub_unit_id: String,
        unit_price: String,
        price: String
    ): Response<JsonObject> = apiHelper.add_stockadjustment(
        token,
        location_id,
        ref_no,
        transaction_date,
        adjustment_type,
        search_product,
        final_total,
        total_amount_recovered,
        additional_notes,
        lot_no_line_id,
        product_id,
        variation_id,
        enable_stock,
        quantity,
        base_unit_multiplier,
        product_unit_id,
        sub_unit_id,
        unit_price,
        price
    )

    suspend fun add_purchase(
        token: String,
        contact_id: RequestBody,
        ref_no: RequestBody,
        transaction_date: RequestBody,
        status: RequestBody,
        location_id: RequestBody,
        pay_term_number: RequestBody,
        pay_term_type: RequestBody,
        file: MultipartBody.Part,
        shipping_details: RequestBody,
        shipping_charges: RequestBody,
        discount_type: RequestBody,
        tax_id: RequestBody,
        advance_balance: RequestBody,
        final_total: RequestBody,
        product_id: RequestBody,
        variation_id: RequestBody,
        quantity: RequestBody,
        product_unit_id: RequestBody,
        sub_unit_id: RequestBody,
        purchase_price: RequestBody,
        purchase_line_tax_id: RequestBody,
        purchase_price_inc_tax: RequestBody,
        default_sell_price: RequestBody,
        paymentamount: RequestBody,
        paymentpaid_on: RequestBody,
        paymentmethod: RequestBody,
        paymentaccount_id: RequestBody,
        paymentnote: RequestBody
    ): Response<JsonObject> = apiHelper.add_purchase(
        token,
        contact_id,
        ref_no,
        transaction_date,
        status,
        location_id,
        pay_term_number,
        pay_term_type,
        file,
        shipping_details,
        shipping_charges,
        discount_type,
        tax_id,
        advance_balance,
        final_total,
        product_id,
        variation_id,
        quantity,
        product_unit_id,
        sub_unit_id,
        purchase_price,
        purchase_line_tax_id,
        purchase_price_inc_tax,
        default_sell_price,
        paymentamount,
        paymentpaid_on,
        paymentmethod,
        paymentaccount_id,
        paymentnote
    )

    suspend fun add_expense(
        token: String,
        location_id: RequestBody,
        expense_category_id: RequestBody,
        expense_sub_category_id: RequestBody,
        ref_no: RequestBody,
//        transaction_date: RequestBody,
        expense_for: RequestBody,
        contact_id: RequestBody,
        document: MultipartBody.Part,
        tax_id: RequestBody,
        tax_calculation_amount: RequestBody,
        recur_interval: RequestBody,
        recur_interval_type: RequestBody,
        recur_repetitions: RequestBody,
        final_total: RequestBody,
        additional_notes: RequestBody,
        paymentamount: RequestBody,
        paymentpaid_on: RequestBody,
        paymentmethod: RequestBody,
        paymentaccount_id: RequestBody,
        paymentnote: RequestBody,
    ): Response<JsonObject> = apiHelper.add_expense(
        token,
        location_id,
        expense_category_id,
        expense_sub_category_id,
        ref_no,
//        transaction_date,
        expense_for,
        contact_id,
        document,
        tax_id,
        tax_calculation_amount,
        recur_interval,
        recur_interval_type,
        recur_repetitions,
        final_total,
        additional_notes,
        paymentamount,
        paymentpaid_on,
        paymentmethod,
        paymentaccount_id,
        paymentnote
    )
}