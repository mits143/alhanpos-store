package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.account.PaymentAccountResponse
import com.alhanpos.store.model.response.contact.ContactListResponse
import com.alhanpos.store.model.response.location.LocationResponse
import com.alhanpos.store.model.response.method.PaymentMethodResponse
import com.alhanpos.store.model.response.product.ProductListResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddPurchaseViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setMsg = MutableLiveData<Resource<String>>()
    val getMsg: LiveData<Resource<String>>
        get() = setMsg

    private val setLocationData = MutableLiveData<Resource<LocationResponse>>()
    val getLocationData: LiveData<Resource<LocationResponse>>
        get() = setLocationData

    private val setContactData = MutableLiveData<Resource<ContactListResponse>>()
    val getContactData: LiveData<Resource<ContactListResponse>>
        get() = setContactData

    private val setProductData = MutableLiveData<Resource<ProductListResponse>>()
    val getProductData: LiveData<Resource<ProductListResponse>>
        get() = setProductData

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


    fun fetchLocation(
        token: String
    ) {
        viewModelScope.launch {
            setLocationData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.location(
                    token, "", ""
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

    fun fetchSupplier(
        token: String
    ) {
        viewModelScope.launch {
            setContactData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.supplierList(
                    token, "", ""
                ).let {
                    if (it.isSuccessful) {
                        setContactData.postValue(Resource.success(it.body()))
                    } else setContactData.postValue(
                        Resource.error(
                            it.message(),
                            null
                        )
                    )
                }
            } else setContactData.postValue(Resource.error("No internet connection", null))
        }
    }

    fun fetchProduct(
        token: String,
    ) {
        viewModelScope.launch {
            setProductData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.productList(
                    token, "", ""
                ).let {
                    if (it.isSuccessful) {
                        setProductData.postValue(Resource.success(it.body()))
                    } else setProductData.postValue(
                        Resource.error(
                            it.message(), null
                        )
                    )
                }
            } else setProductData.postValue(Resource.error("No internet connection", null))
        }
    }

    fun addUpdatePurchase(
        token: String,
        document: MultipartBody.Part,
        data: RequestBody
    ) {
        viewModelScope.launch {
            setMsg.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.add_purchase(
                    token,
                    document,
                    data
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
                            it.message(),
                            null
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