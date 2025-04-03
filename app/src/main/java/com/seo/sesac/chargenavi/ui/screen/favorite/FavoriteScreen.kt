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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.ui.screen.favorite.list.FavoriteListScreen
import com.seo.sesac.chargenavi.viewmodel.FavoriteViewModel
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.userViewModelFactory
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.UserInfo

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

    // 유저 정보 상태
    val userInfoState by userViewModel.userInfo.collectAsStateWithLifecycle()

    // 유저 정보
    val userInfo by remember {
        mutableStateOf((userInfoState as? FireResult.Success)?.data ?: UserInfo())
    }

    // 즐겨찾기 리스트 상태
    val favoriteListState = favoriteViewModel.favoriteList.collectAsStateWithLifecycle()

    // 즐겨찾기 정보를 Firestore 에서 불러온다
    LaunchedEffect(favoriteViewModel) {
        userInfo.id?.let { favoriteViewModel.getFavoriteList(it) }
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
                text = stringResource(R.string.favorite_text),
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(
                        bottom = 30.dp
                    )
            )

            if (favoriteListState.value is FireResult.Success) {
                // 즐겨찾기 된 충전소 리스트
                FavoriteListScreen(favoriteListState.value)
            } else {
                Text(
                    text = stringResource(R.string.empty_favorite_list),
                    fontSize = 16.sp
                )
            }

        }
    }
}