package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.category.CategoryResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setCategoryData = MutableLiveData<Resource<CategoryResponse>>()
    val getCategoryData: LiveData<Resource<CategoryResponse>>
        get() = setCategoryData

    private val setMsg = MutableLiveData<Resource<String>>()
    val getMsg: LiveData<Resource<String>>
        get() = setMsg

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
                            it.message(),
                            null
                        )
                    )
                }
            } else setCategoryData.postValue(Resource.error("No internet connection", null))
        }
    }

    fun deleteCategory(
        token: String,
        id: String
    ) {
        viewModelScope.launch {
            setMsg.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.deleteCategory(
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