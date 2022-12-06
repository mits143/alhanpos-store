package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.sell.SellResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class SellsViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setSellData = MutableLiveData<Resource<SellResponse>>()
    val getSellData: LiveData<Resource<SellResponse>>
        get() = setSellData

    fun fetchSells(
        token: String,
        term: String
    ) {
        viewModelScope.launch {
            setSellData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.sells(
                    token,
                    term
                ).let {
                    if (it.isSuccessful) {
                        setSellData.postValue(Resource.success(it.body()))
                    } else setSellData.postValue(
                        Resource.error(
                            it.message(),
                            null
                        )
                    )
                }
            } else setSellData.postValue(Resource.error("No internet connection", null))
        }
    }
}