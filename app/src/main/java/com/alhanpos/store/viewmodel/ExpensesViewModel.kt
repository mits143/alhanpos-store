package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.expenses.ExpensesResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class ExpensesViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setExpensesData = MutableLiveData<Resource<ExpensesResponse>>()
    val getExpensesData: LiveData<Resource<ExpensesResponse>>
        get() = setExpensesData

    fun fetchExpenses(
        token: String
    ) {
        viewModelScope.launch {
            setExpensesData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.expenses(
                    token
                ).let {
                    if (it.isSuccessful) {
                        setExpensesData.postValue(Resource.success(it.body()))
                    } else setExpensesData.postValue(
                        Resource.error(
                            it.message(),
                            null
                        )
                    )
                }
            } else setExpensesData.postValue(Resource.error("No internet connection", null))
        }
    }
}