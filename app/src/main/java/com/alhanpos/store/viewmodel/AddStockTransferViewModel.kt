package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.request.stockTransfer.StockTransferRequest
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
                    token, "", ""
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
        jsonObject: StockTransferRequest
    ) {
        viewModelScope.launch {
            setMsg.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.add_stocktransfer(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("success")!!.asBoolean)
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
        sku: String
    ) {
        viewModelScope.launch {
            setProductData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.productList(
                    token, "", sku, ""
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