package com.example.vktest.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vktest.ui.screens.product_detail.ProductDetailScreen
import com.example.vktest.ui.screens.product_detail.ProductDetailViewModel
import com.example.vktest.ui.screens.product_list.ProductListScreen
import com.example.vktest.ui.screens.product_list.ProductListViewModel
import com.example.vktest.ui.screens.search_filter.SearchFilterScreen
import com.example.vktest.ui.screens.search_filter.SearchFilterViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ProductListScreen.route) {
        composable(route = Screen.ProductListScreen.route) {
            ProductListScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = getViewModel<ProductListViewModel>(),
                navController = navController
            )
        }
        composable(
            route = Screen.ProductDetailScreen.route + "/{$PRODUCT_TITLE}",
            arguments = listOf(navArgument(PRODUCT_TITLE) { type = NavType.StringType })
        ) {
            val productTitle = it.arguments?.getString(PRODUCT_TITLE) ?: ""
            Log.d("route", Screen.ProductDetailScreen.route + "/{productTitle}")
            Log.d("route", productTitle)
            ProductDetailScreen(
                modifier = Modifier.fillMaxSize(),
                productTitle = productTitle,
                viewModel = getViewModel<ProductDetailViewModel>()
            ) {
                navController.navigateUp()
            }
        }
        composable(
            route = Screen.SearchFilterScreen.route + "/{$SEARCH_FILTER_WAY}/{$SEARCH_FILTER_REQUEST}",
            arguments = listOf(
                navArgument(SEARCH_FILTER_WAY) {
                    type = NavType.StringType
                },
                navArgument(SEARCH_FILTER_REQUEST) {
                    type = NavType.StringType
                }
            )) {
            val way = it.arguments?.getString(SEARCH_FILTER_WAY) ?: ""
            val request = it.arguments?.getString(SEARCH_FILTER_REQUEST) ?: ""
            SearchFilterScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = getViewModel<SearchFilterViewModel>(),
                navController = navController,
                intent = "$way/$request"
            )
        }
    }
}

private const val PRODUCT_TITLE = "productTitle"
private const val SEARCH_FILTER_WAY = "searchFilterWay"
private const val SEARCH_FILTER_REQUEST = "searchFilterRequest"