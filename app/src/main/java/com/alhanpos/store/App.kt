package com.alhanpos.store

import android.app.Application
import com.alhanpos.store.di.appModule
import com.alhanpos.store.di.repoModule
import com.alhanpos.store.di.viewModelModule
import com.alhanpos.store.util.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

val prefs: Prefs by lazy {
    App.prefs!!
}

class App : Application() {
    companion object {
        var prefs: Prefs? = null
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        prefs = Prefs(applicationContext)

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}