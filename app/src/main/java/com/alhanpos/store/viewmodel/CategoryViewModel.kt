package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.category.CategoryResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.Event
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setCategoryData = MutableLiveData<Event<Resource<CategoryResponse>>>()
    val getCategoryData: LiveData<Event<Resource<CategoryResponse>>>
        get() = setCategoryData

    private val setMsg = MutableLiveData<Event<Resource<String>>>()
    val getMsg: LiveData<Event<Resource<String>>>
        get() = setMsg

    fun fetchCategory(
        token: String,
        term: String,
        page: String
    ) {
        viewModelScope.launch {
            setCategoryData.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.categoryList(
                    token,
                    term,
                    page
                ).let {
                    if (it.isSuccessful) {
                        setCategoryData.postValue(Event(Resource.success(it.body())))
                    } else setCategoryData.postValue(
                        Event(
                            Resource.error(
                                it.message(),
                                null
                            )
                        )
                    )
                }
            } else setCategoryData.postValue(Event(Resource.error("No internet connection", null)))
        }
    }

    fun deleteCategory(
        token: String,
        id: String
    ) {
        viewModelScope.launch {
            setMsg.postValue(Event(Resource.loading(null)))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.deleteCategory(
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
                                it.errorBody().toString(),
                                null
                            )
                        )
                    )
                }
            } else setMsg.postValue(Event(Resource.error("No internet connection", null)))
        }
    }
}