package com.example.vktest.ui.navigation

sealed class Screen(val route: String) {
    object ProductListScreen : Screen(PRODUCT_LIST_SCREEN)
    object ProductDetailScreen : Screen(PRODUCT_DETAIL_SCREEN)
    object SearchFilterScreen : Screen(SEARCH_FILTER_SCREEN)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}


private const val PRODUCT_LIST_SCREEN = "product_list_screen"
private const val PRODUCT_DETAIL_SCREEN = "product_detail_screen"
private const val SEARCH_FILTER_SCREEN = "search_filter_screen"