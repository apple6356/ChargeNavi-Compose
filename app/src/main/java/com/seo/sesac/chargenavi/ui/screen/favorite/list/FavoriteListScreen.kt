package com.seo.sesac.chargenavi.ui.screen.favorite.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seo.sesac.chargenavi.ui.screen.common.dividerModifier
import com.seo.sesac.chargenavi.viewmodel.FavoriteViewModel
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.mainViewModelFactory
import com.seo.sesac.chargenavi.viewmodel.factory.userViewModelFactory
import com.seo.sesac.data.entity.EvCsInfo
import com.seo.sesac.data.entity.Favorite
import kotlinx.coroutines.flow.collectLatest

/**
 * 즐겨찾기한 충전소 리스트
 * */
@Composable
fun FavoriteListScreen(
    favoriteList: List<Favorite>,
    favoriteViewModel: FavoriteViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(factory = userViewModelFactory),
    mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory)
) {

    // 유저 정보
    var localUserInfo by remember {
        mutableStateOf(Pair("", ""))
    }

    // 유저 정보 읽기
    LaunchedEffect(key1 = userViewModel) {
        userViewModel.getLocalUserInfo().collectLatest { (userId, nickname) ->
            localUserInfo = Pair(userId, nickname)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        items(favoriteList) { favoriteInfo ->

            // 즐겨찾기 상태, 기본 true
            var favoriteState by remember {
                mutableStateOf(true)
            }

            // 즐겨찾기 상태 갱신
            LaunchedEffect(key1 = localUserInfo) {
                Log.e("FavoriteListScreen", "localUserInfo: $localUserInfo , favoriteState $favoriteState")
                val (userId, _) = localUserInfo
                if (userId.isNotEmpty()) {
                    favoriteState = favoriteViewModel.isFavorite(localUserInfo.first, favoriteInfo.csId)
                    Log.e("FavoriteListScreen", "즐겨찾기 상태: $favoriteState")
                }
            }

            // 즐겨찾기 된 충전소 정보
            var evInfo by remember {
                mutableStateOf(EvCsInfo)
            }

            LaunchedEffect(key1 = favoriteInfo) {

            }

            // 충전소 정보
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 충전소 이름
                Text(
                    text = "충전소", fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                // 즐겨찾기 버튼
                IconButton(onClick = {
                    favoriteState = !favoriteState
                }) {
                    Icon(
                        imageVector = // 즐겨찾기 되어 있지 않다면 빈 하트
                        if (favoriteState) Icons.Filled.Favorite
                        else Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }

            }

            // 주소
            Text(
                text = "주소"
            )

            // 충전 단자
            Text(
                text = "충전 단자"
            )

            // 충전 속도
            Text(
                text = "충전 속도"
            )

            // 충전기 상태
            Text(
                text = "충전기 상태"
            )

            HorizontalDivider(
                modifier = Modifier.dividerModifier()
            )
        }
    }

}
