package com.example.vktest.ui.screens.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.vktest.data.remote.repository.ProductRepository
import com.example.vktest.data.remote.response.product.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class ProductListViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = repository.getProducts().cachedIn(viewModelScope)
    val products: Flow<PagingData<Product>> get() = _products

    private val _categories: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val categories get() = _categories

    fun getCategories() {
        viewModelScope.launch {
            _categories.value = repository.getCategories()
        }
    }
}