package com.example.vktest.di

import com.example.vktest.data.remote.DummyApi
import com.example.vktest.data.remote.repository.ProductRepository
import com.example.vktest.ui.screens.shared.ProductViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<DummyApi> {
        Retrofit.Builder()
            .baseUrl(DummyApi.BASE_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(DummyApi::class.java)
    }

    single<OkHttpClient> {
        OkHttpClient.Builder().apply {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)
        }.build()
    }

    single<ProductRepository> { ProductRepository(get<DummyApi>()) }

    viewModel<ProductViewModel> {
        ProductViewModel(get<ProductRepository>())
    }
}