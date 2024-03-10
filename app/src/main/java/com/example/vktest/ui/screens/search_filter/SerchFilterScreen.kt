package com.example.vktest.ui.screens.search_filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vktest.ui.composables.ProductItemView
import com.example.vktest.ui.navigation.Screen
import com.example.vktest.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFilterScreen(
    modifier: Modifier,
    viewModel: SearchFilterViewModel,
    navController: NavController,
    intent: String
) {
    viewModel.performSearch(intent)
    val products = viewModel.searchResultProducts.collectAsState()
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(
            title = { },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Purple40,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White
            ),
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back to list of products"
                    )
                }
            }
        )
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F4F4))
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
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