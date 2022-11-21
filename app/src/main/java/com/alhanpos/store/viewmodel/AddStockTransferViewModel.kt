package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.location.LocationResponse
import com.alhanpos.store.model.response.product.ProductListResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class AddStockTransferViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setMsg = MutableLiveData<Resource<String>>()
    val getMsg: LiveData<Resource<String>>
        get() = setMsg

    private val setLocationData = MutableLiveData<Resource<LocationResponse>>()
    val getLocationData: LiveData<Resource<LocationResponse>>
        get() = setLocationData

    private val setProductData = MutableLiveData<Resource<ProductListResponse>>()
    val getProductData: LiveData<Resource<ProductListResponse>>
        get() = setProductData


    fun fetchLocation(
        token: String
    ) {
        viewModelScope.launch {
            setLocationData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.location(
                    token
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

    fun addUpdateStockTransfer(
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
    ) {
        viewModelScope.launch {
            setMsg.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.add_stocktransfer(
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
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("success")?.asInt == 1)
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

    fun fetchProduct(
        token: String,
    ) {
        viewModelScope.launch {
            setProductData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.productList(
                    token
                ).let {
                    if (it.isSuccessful) {
                        setProductData.postValue(Resource.success(it.body()))
                    } else setProductData.postValue(
                        Resource.error(
                            it.message(), null
                        )
                    )
                }
            } else setProductData.postValue(Resource.error("No internet connection", null))
        }
    }

    class Common(var name: String, var id: String) {
        override fun toString(): String {
            return name
        }
    }

    class product(var name: String, var sku: String) {
        override fun toString(): String {
            return name
        }
    }
}