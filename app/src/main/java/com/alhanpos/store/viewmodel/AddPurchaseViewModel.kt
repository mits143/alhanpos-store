package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.contact.ContactListResponse
import com.alhanpos.store.model.response.location.LocationResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddPurchaseViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setMsg = MutableLiveData<Resource<String>>()
    val getMsg: LiveData<Resource<String>>
        get() = setMsg

    private val setLocationData = MutableLiveData<Resource<LocationResponse>>()
    val getLocationData: LiveData<Resource<LocationResponse>>
        get() = setLocationData

    private val setContactData = MutableLiveData<Resource<ContactListResponse>>()
    val getContactData: LiveData<Resource<ContactListResponse>>
        get() = setContactData


    fun fetchLocation(
        token: String
    ) {
        viewModelScope.launch {
            setLocationData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.location(
                    token,"", ""
                ).let {
                    if (it.isSuccessful) {
                        setLocationData.postValue(Resource.success(it.body()))
                    } else setLocationData.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else setLocationData.postValue(Resource.error("No internet connection", null))
        }
    }

    fun fetchSupplier(
        token: String
    ) {
        viewModelScope.launch {
            setContactData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.supplierList(
                    token,"", ""
                ).let {
                    if (it.isSuccessful) {
                        setContactData.postValue(Resource.success(it.body()))
                    } else setContactData.postValue(
                        Resource.error(
                            it.message(),
                            null
                        )
                    )
                }
            } else setContactData.postValue(Resource.error("No internet connection", null))
        }
    }

    fun addUpdatePurchase(
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
    ) {
        viewModelScope.launch {
            setMsg.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.add_purchase(
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
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("success")?.asInt!! == 1)
                            setMsg.postValue(Resource.success(it.body()?.get("msg")?.asString))
                        else
                            setMsg.postValue(
                                Resource.error(
                                    it.body()?.get("msg")?.asString!!,
                                    null
                                )
                            )
                    } else setMsg.postValue(
                        Resource.error(
                            it.message(),
                            null
                        )
                    )
                }
            } else setMsg.postValue(Resource.error("No internet connection", null))
        }
    }

    class Common(var name: String, var id: String) {
        override fun toString(): String {
            return name
        }
    }
}