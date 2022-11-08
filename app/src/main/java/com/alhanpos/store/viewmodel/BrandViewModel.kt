package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.brand.BrandResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.Event
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class BrandViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setBrandData = MutableLiveData<Event<Resource<BrandResponse>>>()
    val getBrandData: LiveData<Event<Resource<BrandResponse>>>
        get() = setBrandData

    private val setMsg = MutableLiveData<Event<Resource<String>>>()
    val getMsg: LiveData<Event<Resource<String>>>
        get() = setMsg

    fun fetchBrand(
        token: String
    ) {
        viewModelScope.launch {
            setBrandData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.brandList(
                    token
                ).let {
                    if (it.isSuccessful) {
                        setBrandData.postValue(Event(Resource.success(it.body())))
                    } else setBrandData.postValue(
                        Event(
                            Resource.error(
                                it.message(),
                                null
                            )
                        )
                    )
                }
            } else setBrandData.postValue(Event(Resource.error("No internet connection", null)))
        }
    }

    fun deleteBrand(
        token: String,
        id: String
    ) {
        viewModelScope.launch {
            setMsg.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.deleteBrand(
                    token,
                    id
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("success")?.asBoolean!!)
                            setMsg.postValue(
                                Event(
                                    Resource.success(
                                        it.body()?.get("msg")?.asString
                                    )
                                )
                            )
                        else
                            setMsg.postValue(
                                Event(
                                    Resource.error(
                                        it.body()?.get("msg")?.asString!!,
                                        null
                                    )
                                )
                            )
                    } else setMsg.postValue(
                        Event(
                            Resource.error(
                                it.message(),
                                null
                            )
                        )
                    )
                }
            } else setMsg.postValue(Event(Resource.error("No internet connection", null)))
        }
    }
}