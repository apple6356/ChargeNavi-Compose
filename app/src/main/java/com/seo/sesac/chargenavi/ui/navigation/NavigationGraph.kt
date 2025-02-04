package com.seo.sesac.chargenavi.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.seo.sesac.chargenavi.ui.screen.favorite.FavoriteScreen
import com.seo.sesac.chargenavi.ui.screen.main.DetailScreen
import com.seo.sesac.chargenavi.ui.screen.main.MainScreen
import com.seo.sesac.chargenavi.ui.screen.main.AllReviewScreen
import com.seo.sesac.chargenavi.ui.screen.main.ReviewWriteScreen
import com.seo.sesac.chargenavi.ui.screen.main.SearchScreen
import com.seo.sesac.chargenavi.ui.screen.mypage.EditProfileScreen
import com.seo.sesac.chargenavi.ui.screen.mypage.MyPageScreen
import com.seo.sesac.chargenavi.ui.screen.mypage.ReviewManagementScreen
import com.seo.sesac.chargenavi.ui.screen.mypage.SettingsScreen
import com.seo.sesac.chargenavi.viewmodel.MainViewModel

/**
 * viewModel 추가
 * */
fun NavGraphBuilder.mainNavGraph(navController: NavController, viewModel: MainViewModel) {

    navigation(
        startDestination = NavigationRoute.Main.routeName,
        route = "main_route"
    ) {
        /* 메인 화면 */
        composable(route = NavigationRoute.Main.routeName) {
            MainScreen(navController, viewModel)
        }
        /* 충전소 상세 화면, 충전소 id를 넘긴다 */
        composable(route = "${NavigationRoute.Detail.routeName}/{csId}") {
            val csId = it.arguments?.getString("csId")
            DetailScreen(navController, viewModel, csId)
        }
        /* 검색 화면 */
        composable(route = NavigationRoute.Search.routeName) {
            SearchScreen(navController, viewModel)
        }
        /* 리뷰 목록 화면 */
        composable(route = NavigationRoute.ReviewList.routeName) {
            AllReviewScreen(navController, viewModel)
        }
        /* 리뷰 작성 화면 */
        composable(route = NavigationRoute.ReviewWrite.routeName) {
            ReviewWriteScreen(navController, viewModel)
        }
    }
}

fun NavGraphBuilder.myPageNavGraph(navController: NavController) {

    navigation(
        startDestination = NavigationRoute.MyPage.routeName,
        route = "myPage_route"
    ) {
        /* 마이 페이지 화면 */
        composable(route = NavigationRoute.MyPage.routeName) {
            MyPageScreen(navController)
        }
        /* 프로필 수정 화면 */
        composable(route = NavigationRoute.EditProfile.routeName) {
            EditProfileScreen(navController)
        }
        /* 리뷰 관리 화면 */
        composable(route = NavigationRoute.ReviewManagement.routeName) {
            ReviewManagementScreen(navController)
        }
        /* 설정 화면 */
        composable(route = NavigationRoute.Settings.routeName) {
            SettingsScreen(navController)
        }
    }
}

fun NavGraphBuilder.favoriteNavGraph(navController: NavController) {

    navigation(
        startDestination = NavigationRoute.Favorite.routeName,
        route = "favorite_route"
    ) {
        /* 즐겨찾기 화면 */
        composable(route = NavigationRoute.Favorite.routeName) {
            FavoriteScreen(navController)
        }
    }
}