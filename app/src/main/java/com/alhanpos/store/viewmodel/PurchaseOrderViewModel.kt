package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.purchaseorder.PurchaseOrderResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class PurchaseOrderViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setPurchaseOrderData = MutableLiveData<Resource<PurchaseOrderResponse>>()
    val getPurchaseOrderData: LiveData<Resource<PurchaseOrderResponse>>
        get() = setPurchaseOrderData

    fun fetchPurchaseOrder(
        token: String,
        term: String,
        page: String
    ) {
        viewModelScope.launch {
            setPurchaseOrderData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.purchaseorders(
                    token,
                    term,
                    page
                ).let {
                    if (it.isSuccessful) {
                        setPurchaseOrderData.postValue(Resource.success(it.body()))
                    } else setPurchaseOrderData.postValue(
                        Resource.error(
                            it.message(),
                            null
                        )
                    )
                }
            } else setPurchaseOrderData.postValue(Resource.error("No internet connection", null))
        }
    }
}