package com.example.vktest.ui.screens.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.vktest.data.remote.repository.ProductRepository
import com.example.vktest.data.remote.response.product.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = repository.getAllProducts().cachedIn(viewModelScope)
    val products: Flow<PagingData<Product>> get() = _products

    private val _categories: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val categories get() = _categories

    private val _pickedFromCategoriesOrSearch: MutableStateFlow<List<Product>> =
        MutableStateFlow(emptyList())
    val pickedProducts get() = _pickedFromCategoriesOrSearch

    private val _selectedProduct =
        MutableStateFlow(
            Product("", "", "", 0.0, 0, listOf(""), 0, .0, 0, "", "")
        )
    val selectedProduct get() = _selectedProduct

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

    fun getCategories() {
        viewModelScope.launch {
            _categories.value = repository.getCategories()
        }
    }

    fun getProductsInCategory(category: String) {
        viewModelScope.launch {
            _pickedFromCategoriesOrSearch.value = repository.getProductInCategory(category)
        }
    }
}