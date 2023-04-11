package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.DetailResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class PosDetailViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setMsg = MutableLiveData<Resource<DetailResponse>>()
    val getMsg: LiveData<Resource<DetailResponse>>
        get() = setMsg

    fun posDetail(
        token: String,
        transaction_id: String,
    ) {
        viewModelScope.launch {
            setMsg.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.invoice(
                    token,
                    transaction_id,
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.success == 1)
                            setMsg.postValue(Resource.success(it.body()))
                        else
                            setMsg.postValue(
                                Resource.error(
                                    "Something went wrong",
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
}