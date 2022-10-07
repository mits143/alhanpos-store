package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.brand.BrandResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class BrandViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setBrandData = MutableLiveData<Resource<BrandResponse>>()
    val getBrandData: LiveData<Resource<BrandResponse>>
        get() = setBrandData

    private val setMsg = MutableLiveData<Resource<String>>()
    val getMsg: LiveData<Resource<String>>
        get() = setMsg

    fun fetchBrand(
        token: String
    ) {
        viewModelScope.launch {
            setBrandData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.brandList(
                    token
                ).let {
                    if (it.isSuccessful) {
                        setBrandData.postValue(Resource.success(it.body()))
                    } else setBrandData.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else setBrandData.postValue(Resource.error("No internet connection", null))
        }
    }

    fun deleteBrand(
        token: String,
        id: String
    ) {
        viewModelScope.launch {
            setMsg.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.deleteBrand(
                    token,
                    id
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("success")?.asBoolean!!)
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
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else setMsg.postValue(Resource.error("No internet connection", null))
        }
    }
}