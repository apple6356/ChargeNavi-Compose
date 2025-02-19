package com.seo.sesac.chargenavi.ui.screen.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.seo.sesac.chargenavi.ui.screen.favorite.list.FavoriteListScreen
import com.seo.sesac.chargenavi.viewmodel.FavoriteViewModel
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.userViewModelFactory
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.common.RestResult
import com.seo.sesac.data.entity.EvCsInfo
import com.seo.sesac.data.entity.Favorite
import com.seo.sesac.data.entity.UserInfo
import kotlinx.coroutines.flow.collectLatest

/**
 * 즐겨찾기 화면,
 * 즐겨찾기 된 충전소 목록을 보여주고,
 * 찾는 충전소의 중요 정보(이름, 주소, 단자 타입, 충전 가능 상태 등)를 목록에서 보여 준다
 * */
@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(factory = userViewModelFactory)
) {

    // 유저 정보
    var userInfo by remember {
        mutableStateOf(UserInfo())
    }

    // 유저 정보 읽기
    LaunchedEffect(key1 = userViewModel) {
        userViewModel.userInfo.collectLatest { result ->
            if (result is FireResult.Success) {
                userInfo = result.data
            }
        }
    }

    // 즐겨찾기 목록
    var favoriteList by remember {
        mutableStateOf(emptyList<Favorite>())
    }

    // 즐겨찾기 목록 불러오기
    LaunchedEffect(key1 = userInfo) {
        if (!userInfo.id.equals("-1")) {
            userInfo.id?.let { favoriteViewModel.getFavoriteList(it) }
        }

        favoriteViewModel.favoriteList.collectLatest { result ->
            if (result is FireResult.Success) {
                favoriteList = result.data
            } else {
                favoriteList = emptyList()
            }
        }
    }

    // 즐겨찾기 한 충전소 정보
    var favoriteCsList by remember {
        mutableStateOf(emptyList<EvCsInfo>())
    }

    LaunchedEffect(key1 = favoriteViewModel.favoriteCsList) {
        favoriteViewModel.favoriteCsList.collectLatest { result ->
            if (result is RestResult.Success) {
                favoriteCsList = result.data
            } else {
                favoriteCsList = emptyList()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 10.dp,
                    end = 10.dp
                )
        ) {

            Text(
                text = "즐겨찾기",
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(
                        bottom = 30.dp
                    )
            )

            if (favoriteList.isNotEmpty()) {
                // 즐겨찾기 된 충전소 리스트
                FavoriteListScreen(favoriteList)
            } else {
                Text(
                    text = "즐겨찾기 된 충전소가 존재하지 않습니다.",
                    fontSize = 16.sp
                )
            }

        }
    }
}