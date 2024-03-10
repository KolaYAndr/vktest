package com.example.vktest.ui.screens.product_list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.vktest.R
import com.example.vktest.ui.composables.DropdownCategoriesMenu
import com.example.vktest.ui.composables.ProductItemView
import com.example.vktest.ui.composables.SearchBar
import com.example.vktest.ui.navigation.Screen
import com.example.vktest.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    modifier: Modifier,
    viewModel: ProductListViewModel,
    navController: NavController
) {
    val products = viewModel.products.collectAsLazyPagingItems()
    val dropdownExpanded = remember { mutableStateOf(false) }
    val categories = viewModel.categories.collectAsState()
    val showSearch = remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "List of products") },
                actions = {
                    IconButton(onClick = { showSearch.value = !showSearch.value }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search button"
                        )
                    }
                    IconButton(onClick = {
                        viewModel.getCategories()
                        dropdownExpanded.value = true
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter),
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
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            categories = categories.value,
            expanded = dropdownExpanded
        ) {
            navController.navigate(
                Screen.SearchFilterScreen.withArgs(
                    "category", it
                )
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F4F4))
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (showSearch.value) {
                item {
                    SearchBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        searchText = searchText
                    ) {
                        navController.navigate(
                            Screen.SearchFilterScreen.withArgs("poisk", searchText.value)
                        )
                    }
                }
            }
            items(products.itemCount) { index ->
                val product = products[index]
                if (product != null)
                    ProductItemView(
                        product = product,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                    ) {
                        navController.navigate(Screen.ProductDetailScreen.withArgs(it.title))
                    }
            }
        }
    }
}
