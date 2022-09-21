package com.alhanpos.store.di

import com.alhanpos.store.viewmodel.ContactViewModel
import com.alhanpos.store.viewmodel.LoginViewModel
import com.alhanpos.store.viewmodel.PosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        LoginViewModel(get(), get())
    }
    viewModel {
        PosViewModel(get(), get())
    }
    viewModel {
        ContactViewModel(get(), get())
    }
}