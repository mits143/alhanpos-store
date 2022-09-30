package com.alhanpos.store.di

import com.alhanpos.store.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        LoginViewModel(get(), get())
    }
    viewModel {
        HomeViewModel(get(), get())
    }
    viewModel {
        PosViewModel(get(), get())
    }
    viewModel {
        ContactViewModel(get(), get())
    }
    viewModel {
        AllProductViewModel(get(), get())
    }
}