package com.example.vktest.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vktest.data.remote.response.product.Product
import com.example.vktest.ui.screens.product_detail.ProductDetailScreen
import com.example.vktest.ui.screens.product_list.ProductListScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = PRODUCT_LIST_SCREEN) {
        composable(
            PRODUCT_LIST_SCREEN,
            arguments = listOf(navArgument(PRODUCT_TITLE) { type = NavType.StringType })
        ) {
            ProductListScreen(modifier = Modifier.fillMaxSize(), viewModel = getViewModel()) {
                navController.navigate(PRODUCT_DETAIL_SCREEN)
            }
        }
        composable(PRODUCT_DETAIL_SCREEN) {
            ProductDetailScreen(
                modifier = Modifier.fillMaxSize(),
                Product(
                    id = 2, title = "iPhone X",
                    description = "SIM-Free, Model A19211 6.5-inch Super Retina HD display with OLED technology A12 Bionic chip with ...",
                    price = 899,
                    discountPercentage = 17.94,
                    rating = 4.44,
                    stock = 34,
                    brand = "Apple",
                    category = "smartphones",
                    thumbnail = "https://cdn.dummyjson.com/product-images/2/thumbnail.jpg",
                    images = listOf(
                        "https://cdn.dummyjson.com/product-images/2/1.jpg",
                        "https://cdn.dummyjson.com/product-images/2/2.jpg",
                        "https://cdn.dummyjson.com/product-images/2/3.jpg",
                        "https://cdn.dummyjson.com/product-images/2/thumbnail.jpg"
                    )
                )
            ) {
                navController.navigateUp()
            }
        }
    }
}

const val PRODUCT_LIST_SCREEN = "product_list_screen"
const val PRODUCT_DETAIL_SCREEN = "product_detail_screen"
private const val PRODUCT_TITLE = "productTitle"