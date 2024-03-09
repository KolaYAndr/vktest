package com.example.vktest.ui.screens.search_filter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.vktest.ui.composables.ProductItemView
import com.example.vktest.ui.navigation.Screen

@Composable
fun SearchFilterScreen(
    modifier: Modifier,
    viewModel: SearchFilterViewModel,
    navController: NavController,
    intent: String
) {
    viewModel.performSearch(intent)
    val products = viewModel.searchResultProducts.collectAsState()
    Scaffold(modifier = modifier) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(products.value.size) { index ->
                val product = products.value[index]
                ProductItemView(
                    modifier = Modifier.fillMaxSize(),
                    product = product
                ) {
                    navController.navigate(Screen.ProductDetailScreen.withArgs(product.title))
                }
            }
        }
    }
}