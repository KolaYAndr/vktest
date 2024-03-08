package com.example.vktest

import android.app.Application
import com.example.vktest.di.appModule
import org.koin.core.context.startKoin

class ProductApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}