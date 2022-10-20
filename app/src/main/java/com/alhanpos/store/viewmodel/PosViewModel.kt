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

class PosViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setData = MutableLiveData<Resource<List<String>>>()
    val getData: LiveData<Resource<List<String>>>
        get() = setData

    private val setLocationData = MutableLiveData<Resource<LocationResponse>>()
    val getLocationData: LiveData<Resource<LocationResponse>>
        get() = setLocationData

    private val setContactData = MutableLiveData<Resource<ContactListResponse>>()
    val getContactData: LiveData<Resource<ContactListResponse>>
        get() = setContactData

    private val setProductData = MutableLiveData<Resource<ProductListResponse>>()
    val getProductData: LiveData<Resource<ProductListResponse>>
        get() = setProductData

    fun fetchData() {
        viewModelScope.launch {
            setData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val list = arrayListOf<String>()
                for (i in 0 until 20)
                    list.add("" + i)
                setData.postValue(Resource.success(list))
            } else setData.postValue(Resource.error("No internet connection", null))
        }
    }

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

    fun fetchContact(
        token: String
    ) {
        viewModelScope.launch {
            setContactData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.contactList(
                    token
                ).let {
                    if (it.isSuccessful) {
                        setContactData.postValue(Resource.success(it.body()))
                    } else setContactData.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else setContactData.postValue(Resource.error("No internet connection", null))
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

    class product(var name: String, var sku: String) {
        override fun toString(): String {
            return name
        }
    }
}