package com.example.vktest.ui.screens.search_filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vktest.data.remote.repository.ProductRepository
import com.example.vktest.data.remote.response.product.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchFilterViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _searchResultProducts = MutableStateFlow<List<Product>>(emptyList())
    val searchResultProducts get() = _searchResultProducts

    fun performSearch(intent: String) {
        when (intent) {
            "q" -> getProductsByQuery(intent)
            "category" -> getProductsInCategory(intent)
        }
    }

    private fun getProductsInCategory(category: String) {
        viewModelScope.launch {
            _searchResultProducts.value = repository.getProductInCategory(category)
        }
    }

    private fun getProductsByQuery(q: String) {
        viewModelScope.launch {
            _searchResultProducts.value = repository.getProductByQuery(q)
        }
    }
}