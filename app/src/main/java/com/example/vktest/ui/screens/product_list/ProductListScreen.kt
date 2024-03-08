package com.example.vktest.ui.screens.product_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.vktest.ui.composables.DropdownCategoriesMenu
import com.example.vktest.ui.composables.ProductItemView
import com.example.vktest.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(modifier: Modifier, viewModel: ProductListViewModel) {
    val expanded = remember { mutableStateOf(false) }
    val products = viewModel.products.collectAsLazyPagingItems()
    val categories = viewModel.categories.collectAsState()
    val pickedProducts = viewModel.pickedProducts.collectAsState()

    Scaffold(modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "List of products") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search button"
                        )
                    }
                    IconButton(onClick = {
                        viewModel.getCategories()
                        expanded.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Filter"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple40,
                    actionIconContentColor = Color.White,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        DropdownCategoriesMenu(
            categories = categories.value,
            expanded = expanded
        ) { pickedCategory ->
            viewModel.getProductsInCategory(pickedCategory)
            //TODO отображение списка выбранных продуктов
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F4F4))
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(products.itemCount) { index ->
                val product = products[index]
                if (product != null) ProductItemView(product = product)
            }
        }
    }
}
