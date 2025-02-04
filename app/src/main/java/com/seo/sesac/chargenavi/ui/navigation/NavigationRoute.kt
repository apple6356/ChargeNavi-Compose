package com.seo.sesac.chargenavi.ui.navigation

sealed class NavigationRoute(val routeName : String) {
    data object Main : NavigationRoute("Main")
    data object Detail : NavigationRoute("Detail")
    data object Search : NavigationRoute("Search")
    data object ReviewList : NavigationRoute("ReviewList")
    data object ReviewWrite : NavigationRoute("ReviewWrite")

    data object MyPage : NavigationRoute("MyPage")
    data object EditProfile : NavigationRoute("EditProfile")
    data object ReviewManagement : NavigationRoute("ReviewManagement")
    data object Settings : NavigationRoute("Settings")

    data object Favorite : NavigationRoute("Favorite")
    data object FavoriteList : NavigationRoute("FavoriteList")
}