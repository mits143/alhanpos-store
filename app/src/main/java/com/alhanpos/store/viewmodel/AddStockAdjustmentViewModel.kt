package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.location.LocationResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class AddStockAdjustmentViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setMsg = MutableLiveData<Resource<String>>()
    val getMsg: LiveData<Resource<String>>
        get() = setMsg

    private val setLocationData = MutableLiveData<Resource<LocationResponse>>()
    val getLocationData: LiveData<Resource<LocationResponse>>
        get() = setLocationData


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

    fun addUpdateStockAdjustment(
        token: String,
        location_id: String,
        ref_no: String,
        transaction_date: String,
        adjustment_type: String,
        search_product: String,
        final_total: String,
        total_amount_recovered: String,
        additional_notes: String
    ) {
        viewModelScope.launch {
            setMsg.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.add_stockadjustment(
                    token,
                    location_id,
                    ref_no,
                    transaction_date,
                    adjustment_type,
                    search_product,
                    final_total,
                    total_amount_recovered,
                    additional_notes
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("success")?.asInt!! == 1)
                            setMsg.postValue(Resource.success(it.body()?.get("msg")?.asString))
                        else setMsg.postValue(
                            Resource.error(
                                it.body()?.get("msg")?.asString!!, null
                            )
                        )
                    } else setMsg.postValue(
                        Resource.error(
                            it.message(), null
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