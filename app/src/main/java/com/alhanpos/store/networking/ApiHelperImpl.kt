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
import com.alhanpos.store.model.response.units.UnitResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        token: String,
        term: String,
        page: String
    ): Response<LocationResponse> = apiService.location(token, term, page)

    override suspend fun contactList(
        token: String,
        term: String,
        page: String
    ): Response<ContactListResponse> = apiService.contactList(token, term, page)

    override suspend fun supplierList(
        token: String,
        term: String,
        page: String
    ): Response<ContactListResponse> = apiService.supplierList(token, term, page)

    override suspend fun productList(
        token: String,
        term: String,
        page: String
    ): Response<ProductListResponse> = apiService.productList(token, term, page)

    override suspend fun categoryList(
        token: String,
        term: String,
        page: String
    ): Response<CategoryResponse> = apiService.categoryList(token, term, page)

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
        term: String,
        page: String
    ): Response<BrandResponse> = apiService.brandList(token, term, page)

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
        term: String,
        page: String
    ): Response<PaymentAccountResponse> = apiService.paymentAccounts(token, term, page)

    override suspend fun paymentMethods(
        token: String,
        term: String,
        page: String
    ): Response<PaymentMethodResponse> = apiService.paymentMethods(token, term, page)

    override suspend fun addUpdateProduct(
        token: String,
        name: String,
        brand_id: String,
        category_id: String,
        unit_id: String,
        selling_price: String,
//        tax: String,
        sku: String,
        alert_quantity: String
    ): Response<JsonObject> = apiService.addUpdateProduct(
        token, name, brand_id, category_id, unit_id, selling_price, /*tax,*/ sku, alert_quantity
    )

    override suspend fun add_supplier(
        token: String,
        name: String,
        email: String,
        mobile: String,
        balance: String,
        id: String,
        due: String,
    ): Response<JsonObject> = apiService.add_supplier(
        token, name, email, mobile, balance, id, due
    )

    override suspend fun add_customer(
        token: String,
        name: String,
        email: String,
        mobile: String,
        balance: String,
        id: String,
        due: String,
    ): Response<JsonObject> = apiService.add_customer(
        token, name, email, mobile, balance, id, due
    )

    override suspend fun finalizePayment(
        token: String,
        jsonObject: PaymentRequest
    ): Response<JsonObject> = apiService.finalizePayment(
        token, jsonObject
    )

    override suspend fun stocktransfer(
        token: String,
        term: String,
        page: String
    ): Response<StockTransferResponse> = apiService.stocktransfer(
        token, term, page
    )

    override suspend fun expenses(
        token: String,
        term: String,
        page: String
    ): Response<ExpensesResponse> = apiService.expenses(
        token, term, page
    )

    override suspend fun sells(
        token: String,
        term: String,
        page: String
    ): Response<SellResponse> = apiService.sells(
        token, term, page
    )

    override suspend fun purchaseorders(
        token: String,
        term: String,
        page: String
    ): Response<PurchaseOrderResponse> = apiService.purchaseorders(
        token, term, page
    )

    override suspend fun stockadjustments(
        token: String,
        term: String,
        page: String
    ): Response<StockAdjustmentResponse> = apiService.stockadjustments(
        token,
        term, page
    )

    override suspend fun subscriptions(
        token: String,
        term: String,
        page: String
    ): Response<SubscripitionResponse> = apiService.subscriptions(
        token, term, page
    )

    override suspend fun unitsList(
        token: String,
        term: String,
        page: String
    ): Response<UnitResponse> = apiService.unitsList(
        token, term, page
    )

    override suspend fun add_stocktransfer(
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
    ): Response<JsonObject> = apiService.add_stocktransfer(
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

    override suspend fun add_stockadjustment(
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
        price: String,
    ): Response<JsonObject> = apiService.add_stockadjustment(
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
        price,
    )

    override suspend fun add_purchase(
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
    ): Response<JsonObject> = apiService.add_purchase(
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

    override suspend fun add_expense(
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
    ): Response<JsonObject> = apiService.add_expense(
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