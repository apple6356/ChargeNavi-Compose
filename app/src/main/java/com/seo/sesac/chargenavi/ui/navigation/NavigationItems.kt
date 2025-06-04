package com.seo.sesac.chargenavi.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val tabName : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {
    fun settingBottomNavigationItems() : List<NavigationItem> {
        return listOf(
            NavigationItem(
                tabName = "지도",
                icon = Icons.Filled.LocationOn,
                route = NavigationRoute.Main.routeName
            ),
            NavigationItem(
                tabName = "즐겨찾기",
                icon = Icons.Filled.Favorite,
                route = NavigationRoute.Favorite.routeName
            ),
            NavigationItem(
                tabName = "MY",
                icon = Icons.Filled.Person,
                route = NavigationRoute.MyPage.routeName
            ),
        )
    }
}