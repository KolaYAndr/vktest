package com.example.vktest.di

import com.example.vktest.data.remote.DummyApi
import com.example.vktest.data.remote.repository.ProductRepository
import com.example.vktest.ui.screens.product_detail.ProductDetailViewModel
import com.example.vktest.ui.screens.search_filter.SearchFilterViewModel
import com.example.vktest.ui.screens.product_list.ProductListViewModel
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

    viewModel<ProductListViewModel> {
        ProductListViewModel(get<ProductRepository>())
    }

    viewModel<ProductDetailViewModel> {
        ProductDetailViewModel(get<ProductRepository>())
    }

    viewModel<SearchFilterViewModel> {
        SearchFilterViewModel(get<ProductRepository>())
    }
}