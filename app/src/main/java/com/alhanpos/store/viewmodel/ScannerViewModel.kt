package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.contact.ContactListResponse
import com.alhanpos.store.model.response.location.LocationResponse
import com.alhanpos.store.model.response.product.ProductListResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setProductData = MutableLiveData<Resource<ProductListResponse>>()
    val getProductData: LiveData<Resource<ProductListResponse>>
        get() = setProductData

    fun fetchProduct(
        token: String,
        term: String,
        sku: String
    ) {
        viewModelScope.launch {
            setProductData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.productList(
                    token, term, sku, ""
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

    class product(var name: String, var sku: String) {
        override fun toString(): String {
            return name
        }
    }
}