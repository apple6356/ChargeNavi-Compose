package com.seo.sesac.chargenavi.ui.screen.favorite.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.userViewModelFactory
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.chargenavi.ui.screen.favorite.FavoriteCsScreen
import com.seo.sesac.data.entity.Favorite
import com.seo.sesac.data.entity.UserInfo

/**
 * 즐겨찾기한 충전소 리스트
 * */
@Composable
fun FavoriteListScreen(
    favoriteList: FireResult<MutableList<Favorite>>,
    userViewModel: UserViewModel = viewModel(factory = userViewModelFactory),
) {

    // 유저 정보 상태
    val userInfoState by userViewModel.userInfo.collectAsStateWithLifecycle()

    // 유저 정보
    val userInfo by remember {
        mutableStateOf((userInfoState as? FireResult.Success)?.data ?: UserInfo())
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        if (favoriteList is FireResult.Success) {
            items(favoriteList.data) { favoriteInfo ->
                FavoriteCsScreen(userInfo = userInfo, favoriteInfo = favoriteInfo)
            }
        }
    }

}
