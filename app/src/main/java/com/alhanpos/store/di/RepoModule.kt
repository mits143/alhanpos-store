package com.alhanpos.store.di

import com.alhanpos.store.repo.MainRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        MainRepository(get())
    }
}