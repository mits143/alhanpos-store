package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.subscription.SubscripitionResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class SubscripitionViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setSubscriptionData = MutableLiveData<Resource<SubscripitionResponse>>()
    val getSubscriptionData: LiveData<Resource<SubscripitionResponse>>
        get() = setSubscriptionData

    fun fetchSubscriptions(
        token: String,
        term: String
    ) {
        viewModelScope.launch {
            setSubscriptionData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.subscriptions(
                    token,
                    term
                ).let {
                    if (it.isSuccessful) {
                        setSubscriptionData.postValue(Resource.success(it.body()))
                    } else setSubscriptionData.postValue(
                        Resource.error(
                            it.message(),
                            null
                        )
                    )
                }
            } else setSubscriptionData.postValue(Resource.error("No internet connection", null))
        }
    }
}