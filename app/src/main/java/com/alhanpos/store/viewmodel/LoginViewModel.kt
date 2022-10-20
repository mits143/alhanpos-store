package com.alhanpos.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.login.LoginResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch

class LoginViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val setUser = MutableLiveData<Resource<LoginResponse>>()
    val getUser: LiveData<Resource<LoginResponse>>
        get() = setUser

    fun fetchUsers(
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            setUser.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.login(
                    "password",
                    "3",
                    "w5dIwy79YCrLlmsOoTosQ9SVB1zF8D5QurYjy8ds",
                    username,
                    password
                ).let {
                    if (it.isSuccessful) {
                        setUser.postValue(Resource.success(it.body()))
                    } else setUser.postValue(
                        Resource.error(
                            it.message(), null
                        )
                    )
                }
            } else setUser.postValue(Resource.error("No internet connection", null))
        }
    }
}