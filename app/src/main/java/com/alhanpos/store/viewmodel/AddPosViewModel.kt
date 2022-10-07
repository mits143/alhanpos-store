package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.PaymentAccountResponse
import com.alhanpos.store.model.response.PaymentMethodResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class AddPosViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setPaymentAccountData = MutableLiveData<Resource<PaymentAccountResponse>>()
    val getPaymentAccountData: LiveData<Resource<PaymentAccountResponse>>
        get() = setPaymentAccountData

    private val setPaymentMethodData = MutableLiveData<Resource<PaymentMethodResponse>>()
    val getPaymentMethodData: LiveData<Resource<PaymentMethodResponse>>
        get() = setPaymentMethodData

    fun fetchPaymentAccountData(
        token: String
    ) {
        viewModelScope.launch {
            setPaymentAccountData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.paymentAccounts(
                    token
                ).let {
                    if (it.isSuccessful) {
                        setPaymentAccountData.postValue(Resource.success(it.body()))
                    } else setPaymentAccountData.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else setPaymentAccountData.postValue(Resource.error("No internet connection", null))
        }
    }

    fun fetchPaymentMethodData(
        token: String
    ) {
        viewModelScope.launch {
            setPaymentMethodData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.paymentMethods(
                    token
                ).let {
                    if (it.isSuccessful) {
                        setPaymentMethodData.postValue(Resource.success(it.body()))
                    } else setPaymentMethodData.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else setPaymentMethodData.postValue(Resource.error("No internet connection", null))
        }
    }
}