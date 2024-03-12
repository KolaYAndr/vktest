package com.example.vktest.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.vktest.data.remote.DummyApi
import com.example.vktest.data.remote.paging.ProductPagingSource
import com.example.vktest.data.remote.response.product.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

const val NETWORK_PAGE_SIZE = 20

class ProductRepository(private val api: DummyApi) {
    fun getProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { ProductPagingSource(api) }
        )
            .flow
            .flowOn(Dispatchers.IO)
    }

    suspend fun getAllProducts() = api.getProducts(limit = 0, skip = 0)

    suspend fun getCategories() = api.getCategories()

    suspend fun getProductInCategory(category: String) =
        api.getProductsInCategory(category).products

    suspend fun getProductByQuery(q: String) = api.getProductsByQuery(q).products
}