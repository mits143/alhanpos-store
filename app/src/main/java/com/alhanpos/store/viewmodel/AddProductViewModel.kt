package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.brand.BrandResponse
import com.alhanpos.store.model.response.category.CategoryResponse
import com.alhanpos.store.model.response.units.UnitResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class AddProductViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setUnitData = MutableLiveData<Resource<UnitResponse>>()
    val getUnitData: LiveData<Resource<UnitResponse>>
        get() = setUnitData

    private val setCategoryData = MutableLiveData<Resource<CategoryResponse>>()
    val getCategoryData: LiveData<Resource<CategoryResponse>>
        get() = setCategoryData

    private val setBrandData = MutableLiveData<Resource<BrandResponse>>()
    val getBrandData: LiveData<Resource<BrandResponse>>
        get() = setBrandData

    private val setMsg = MutableLiveData<Resource<String>>()
    val getMsg: LiveData<Resource<String>>
        get() = setMsg

    fun fetchUnit(
        token: String
    ) {
        viewModelScope.launch {
            setUnitData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.unitsList(
                    token
                ).let {
                    if (it.isSuccessful) {
                        setUnitData.postValue(Resource.success(it.body()))
                    } else setUnitData.postValue(
                        Resource.error(
                            it.errorBody().toString(), null
                        )
                    )
                }
            } else setUnitData.postValue(Resource.error("No internet connection", null))
        }
    }

    fun fetchCategory(
        token: String
    ) {
        viewModelScope.launch {
            setCategoryData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.categoryList(
                    token
                ).let {
                    if (it.isSuccessful) {
                        setCategoryData.postValue(Resource.success(it.body()))
                    } else setCategoryData.postValue(
                        Resource.error(
                            it.errorBody().toString(), null
                        )
                    )
                }
            } else setCategoryData.postValue(Resource.error("No internet connection", null))
        }
    }


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
                            it.message(), null
                        )
                    )
                }
            } else setBrandData.postValue(Resource.error("No internet connection", null))
        }
    }

    fun addUpdateProduct(
        token: String,
        name: String,
        brand_id: String,
        category_id: String,
        unit_id: String,
        selling_price: String,
        tax: String,
        sku: String,
        alert_quantity: String
    ) {
        viewModelScope.launch {
            setMsg.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.addUpdateProduct(
                    token, name, brand_id, category_id, unit_id, selling_price, tax, sku, alert_quantity
                ).let {
                    if (it.isSuccessful) {
                        if (it.body()?.get("success")?.asBoolean!!
                        ) setMsg.postValue(Resource.success(it.body()?.get("msg")?.asString))
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