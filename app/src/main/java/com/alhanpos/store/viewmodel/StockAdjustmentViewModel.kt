package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.stockadjustment.StockAdjustmentResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class StockAdjustmentViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setStockAdjustmentData = MutableLiveData<Resource<StockAdjustmentResponse>>()
    val getStockAdjustmentData: LiveData<Resource<StockAdjustmentResponse>>
        get() = setStockAdjustmentData

    fun fetchExpenses(
        token: String,
        term: String,
        page: String
    ) {
        viewModelScope.launch {
            setStockAdjustmentData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.stockadjustments(
                    token,
                    term,
                    page
                ).let {
                    if (it.isSuccessful) {
                        setStockAdjustmentData.postValue(Resource.success(it.body()))
                    } else setStockAdjustmentData.postValue(
                        Resource.error(
                            it.message(),
                            null
                        )
                    )
                }
            } else setStockAdjustmentData.postValue(Resource.error("No internet connection", null))
        }
    }
}