package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.stocktransfer.StockTransferResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class StockTransferViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setStockData = MutableLiveData<Resource<StockTransferResponse>>()
    val getStockData: LiveData<Resource<StockTransferResponse>>
        get() = setStockData

    fun fetchStock(
        token: String,
        term: String
    ) {
        viewModelScope.launch {
            setStockData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.stocktransfer(
                    token,
                    term
                ).let {
                    if (it.isSuccessful) {
                        setStockData.postValue(Resource.success(it.body()))
                    } else setStockData.postValue(
                        Resource.error(
                            it.message(),
                            null
                        )
                    )
                }
            } else setStockData.postValue(Resource.error("No internet connection", null))
        }
    }
}