package com.seo.sesac.chargenavi.ui.screen.favorite.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
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
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.EvCsInfo
import com.seo.sesac.data.entity.Favorite
import com.seo.sesac.data.entity.UserInfo
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

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        items(favoriteList) { favoriteInfo ->

            // 즐겨찾기 상태, 기본 true
            var favoriteState by remember {
                mutableStateOf(true)
            }

            // 즐겨찾기 상태 갱신
            LaunchedEffect(key1 = userInfo) {
                Log.e("FavoriteListScreen", "UserInfo: $userInfo , favoriteState $favoriteState")
                if (!userInfo.id.equals("-1") && userInfo.id != null) {
                    favoriteState = favoriteViewModel.isFavorite(userInfo.id!!, favoriteInfo.csId)
                    Log.e("FavoriteListScreen", "즐겨찾기 상태: $favoriteState")
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(5.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) { // 충전소 이름
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        // 충전소 이름
                        Text(
                            text = favoriteInfo.csNm,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )

                        // 즐겨찾기 버튼
                        IconButton(onClick = {
                            favoriteState = !favoriteState
                            // 로그인 된 상태에서만 반응
                            if (userInfo.id != null && !userInfo.id.equals("-1")) {
                                if (favoriteState) {
//                                    favoriteViewModel.addFavorite(userInfo.id!!, favoriteInfo.csId)
                                } else {
                                    favoriteViewModel.deleteFavorite(userInfo.id!!, favoriteInfo.csId)
                                }
                            }
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
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 15.dp
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Place,
                            contentDescription = "주소",
                            tint = Color.LightGray
                        )

                        Text(
                            text = favoriteInfo.address,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }

}
