package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class PosViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setData = MutableLiveData<Resource<List<String>>>()
    val getData: LiveData<Resource<List<String>>>
        get() = setData

    fun fetchData() {
        viewModelScope.launch {
            setData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val list = arrayListOf<String>()
                for (i in 0 until 20)
                    list.add("" + i)
                setData.postValue(Resource.success(list))
            } else setData.postValue(Resource.error("No internet connection", null))
        }
    }
}