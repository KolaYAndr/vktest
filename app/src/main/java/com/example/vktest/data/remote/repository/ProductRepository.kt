package com.example.vktest.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.vktest.data.remote.DummyApi
import com.example.vktest.data.remote.paging.ProductPagingSource
import com.example.vktest.data.remote.response.product.Product
import kotlinx.coroutines.flow.Flow

const val NETWORK_PAGE_SIZE = 20

class ProductRepository(private val api: DummyApi) {
    fun getAllProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { ProductPagingSource(api) }
        ).flow
    }

    suspend fun getCategories() = api.getCategories()


    suspend fun getProductInCategory(category: String) = api.getProductsInCategory(category).products
}