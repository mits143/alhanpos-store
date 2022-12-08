package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.request.payment.PaymentRequest
import com.alhanpos.store.model.response.account.PaymentAccountResponse
import com.alhanpos.store.model.response.method.PaymentMethodResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import com.google.gson.JsonObject
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

    private val setPaymentData = MutableLiveData<Resource<JsonObject>>()
    val getPaymentData: LiveData<Resource<JsonObject>>
        get() = setPaymentData

    fun fetchPaymentAccountData(
        token: String
    ) {
        viewModelScope.launch {
            setPaymentAccountData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.paymentAccounts(
                    token, "", ""
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
                    token, "", ""
                ).let {
                    if (it.isSuccessful) {
                        setPaymentMethodData.postValue(Resource.success(it.body()))
                    } else setPaymentMethodData.postValue(
                        Resource.error(
                            it.message(),
                            null
                        )
                    )
                }
            } else setPaymentMethodData.postValue(Resource.error("No internet connection", null))
        }
    }

    fun fetchPaymentData(
        token: String,
        jsonObject: PaymentRequest
    ) {
        viewModelScope.launch {
            setPaymentData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.finalizePayment(
                    token, jsonObject
                ).let {
                    if (it.isSuccessful) {
                        setPaymentData.postValue(Resource.success(it.body()))
                    } else setPaymentData.postValue(
                        Resource.error(
                            it.message(),
                            null
                        )
                    )
                }
            } else setPaymentData.postValue(Resource.error("No internet connection", null))
        }
    }

    class Common(var name: String, var id: String) {
        override fun toString(): String {
            return name
        }
    }
}