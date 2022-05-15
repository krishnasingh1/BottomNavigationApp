package com.coderkprathore.androdcompose.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItemData(
    val name: String,
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)
fun getItemData (): List<BottomNavItemData> {
    return listOf(
        BottomNavItemData(
            name = "Home",
            route = "home",
            icon = Icons.Default.Home
        ),
        BottomNavItemData(
            name = "Categories",
            route = "categories",
            icon = Icons.Default.Menu
        ),
        BottomNavItemData(
            name = "Search",
            route = "search",
            icon = Icons.Default.Search
        ),
        BottomNavItemData(
            name = "Wishlist",
            route = "wishlist",
            icon = Icons.Default.Check
        ),
        BottomNavItemData(
            name = "Cart",
            route = "cart",
            icon = Icons.Default.ShoppingCart,
            badgeCount = 20
        )
    )

}
