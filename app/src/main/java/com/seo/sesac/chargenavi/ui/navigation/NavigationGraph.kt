package com.seo.sesac.chargenavi.ui.navigation

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.seo.sesac.chargenavi.ui.screen.favorite.FavoriteScreen
import com.seo.sesac.chargenavi.ui.screen.main.DetailScreen
import com.seo.sesac.chargenavi.ui.screen.main.AllReviewScreen
import com.seo.sesac.chargenavi.ui.screen.main.ReviewWriteScreen
import com.seo.sesac.chargenavi.ui.screen.main.SearchScreen
import com.seo.sesac.chargenavi.ui.screen.mypage.EditProfileScreen
import com.seo.sesac.chargenavi.ui.screen.mypage.MyPageScreen
import com.seo.sesac.chargenavi.ui.screen.mypage.ReviewManagementScreen
import com.seo.sesac.chargenavi.ui.screen.mypage.SettingsScreen
import com.seo.sesac.chargenavi.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.mainNavGraph(
    navController: NavController,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    mainViewModel: MainViewModel
) {

    navigation(
        startDestination = NavigationRoute.Main.routeName,
        route = "main_route"
    ) {
        /* 충전소 상세 화면, 충전소 id를 넘긴다 */
        composable(route = "${NavigationRoute.Detail.routeName}/{csId}") {
            val csId = it.arguments?.getString("csId")
            if (csId != null) {
                DetailScreen(navController, mainViewModel = mainViewModel, csId = csId, bottomSheetScaffoldState = bottomSheetScaffoldState)
            }
        }
        /* 검색 화면 */
        composable(route = NavigationRoute.Search.routeName) {
            SearchScreen(navController, mainViewModel = mainViewModel, bottomSheetScaffoldState = bottomSheetScaffoldState)
        }
        /* 리뷰 목록 화면 */
        composable(route = "${NavigationRoute.ReviewList.routeName}/{csId}/{userId}") {
            val csId = it.arguments?.getString("csId")
            val userId = it.arguments?.getString("userId")
            if (csId != null && userId != null) {
                AllReviewScreen(navController, csId = csId, userId = userId)
            }
        }
        /* 리뷰 작성 화면 */
        composable(route = "${NavigationRoute.ReviewWrite.routeName}/{csId}") {
            val csId = it.arguments?.getString("csId")
            if (csId != null) {
                ReviewWriteScreen(navController, csId = csId)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.myPageNavGraph(
    navController: NavController,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {

    navigation(
        startDestination = NavigationRoute.MyPage.routeName,
        route = "myPage_route"
    ) {
        /* 마이 페이지 화면 */
        composable(route = NavigationRoute.MyPage.routeName) {
            MyPageScreen(navController = navController, bottomSheetScaffoldState = bottomSheetScaffoldState)
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

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.favoriteNavGraph(
    navController: NavController,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {

    navigation(
        startDestination = NavigationRoute.Favorite.routeName,
        route = "favorite_route"
    ) {
        /* 즐겨찾기 화면 */
        composable(route = NavigationRoute.Favorite.routeName) {
            FavoriteScreen(navController = navController, bottomSheetScaffoldState = bottomSheetScaffoldState)
        }
    }
}