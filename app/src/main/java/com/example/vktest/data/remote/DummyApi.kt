package com.example.vktest.data.remote

import com.example.vktest.data.remote.response.product.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyApi {
    companion object {
        const val BASE_ENDPOINT = "https://dummyjson.com"
    }

    @GET("/products")
    suspend fun getProducts(@Query("limit") limit: Int, @Query("skip") skip: Int): ProductResponse

    @GET("/products")
    suspend fun getProductsByQuery(@Query("q") q: String): ProductResponse

    @GET("/products/category/{category}")
    suspend fun getProductsInCategory(@Path("category") category: String): ProductResponse

    @GET("/products/categories")
    suspend fun getCategories(): List<String>
}