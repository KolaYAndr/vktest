package com.example.vktest.ui.screens.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vktest.data.remote.repository.ProductRepository
import com.example.vktest.data.remote.response.product.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> get() = _selectedProduct

    fun getProduct(productTitle: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedProduct.value =
                repository.getAllProducts().products.firstOrNull { it.title == productTitle }
        }
    }
}