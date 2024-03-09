package com.example.vktest.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vktest.ui.screens.product_detail.ProductDetailScreen
import com.example.vktest.ui.screens.product_list.ProductListScreen
import com.example.vktest.ui.screens.shared.ProductViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val productViewModel = getViewModel<ProductViewModel>()
    val product = productViewModel.selectedProduct.collectAsState()
    NavHost(navController = navController, startDestination = PRODUCT_LIST_SCREEN) {
        composable(
            PRODUCT_LIST_SCREEN,
            arguments = listOf(navArgument(PRODUCT_TITLE) { type = NavType.StringType })
        ) {
            ProductListScreen(modifier = Modifier.fillMaxSize(), viewModel = productViewModel) {
                navController.navigate(PRODUCT_DETAIL_SCREEN)
            }
        }
        composable(PRODUCT_DETAIL_SCREEN) {
            ProductDetailScreen(
                modifier = Modifier.fillMaxSize(),
                product = product.value
            ) {
                navController.navigateUp()
            }
        }
    }
}

const val PRODUCT_LIST_SCREEN = "product_list_screen"
const val PRODUCT_DETAIL_SCREEN = "product_detail_screen"
private const val PRODUCT_TITLE = "productTitle"