package com.seo.sesac.chargenavi.ui.screen.favorite

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.seo.sesac.chargenavi.ui.screen.common.ChargeInfoScreen
import com.seo.sesac.chargenavi.ui.screen.common.CircularProgress
import com.seo.sesac.chargenavi.viewmodel.FavoriteViewModel
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.mainViewModelFactory
import com.seo.sesac.data.common.RestResult
import com.seo.sesac.data.entity.EvCsInfo
import com.seo.sesac.data.entity.Favorite
import com.seo.sesac.data.entity.UserInfo

/**
 * 즐겨찾기 충전소 정보
 * */
@Composable
fun FavoriteCsScreen(
    favoriteViewModel: FavoriteViewModel = viewModel(),
    userInfo: UserInfo,
    favoriteInfo: Favorite
) {
    // 즐겨찾기 상태, 기본 true
    var favoriteState by remember {
        mutableStateOf(true)
    }

    // 즐겨찾기 상태 갱신
    LaunchedEffect(key1 = userInfo) {
        Log.e("FavoriteListScreen", "UserInfo: $userInfo , favoriteState $favoriteState")
        if (!userInfo.id.equals("-1") && userInfo.id != null) {
            favoriteState = favoriteViewModel.isFavorite(userInfo.id!!, favoriteInfo.csId)
            Log.e("FavoriteCsScreen", "즐겨찾기 상태: $favoriteState")
        }
    }

    val favoriteCsListState by favoriteViewModel.favoriteCsList.collectAsState()

    // 충전소 정보
    var csInfo by remember {
        mutableStateOf<List<EvCsInfo>>(emptyList())
    }

    /** viewModel 에서 충전소 정보 가져오기 */
    LaunchedEffect(key1 = favoriteCsListState) {
        Log.e("FavoriteCsScreen", "csInfo: $csInfo")
        if (favoriteViewModel.favoriteCsList.value is RestResult.Success) {
            Log.e("FavoriteCsScreen", "csInfo: $csInfo")

            csInfo = favoriteViewModel.findByCsId(favoriteInfo.csId).values
                .flatten() // flatten 을 사용해 여러 list 를 하나의 list 로 통합
                .sortedBy { it.cpId } // cpId(충전기 Id) 순으로 정렬

            Log.e("FavoriteCsScreen", "csInfo: $csInfo")
        }
    }


    if (csInfo.isEmpty()) {
        CircularProgress()
    } else {
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
                        text = csInfo.first().csNm,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // 즐겨찾기 버튼
                    IconButton(
                        onClick = {
                            favoriteState = !favoriteState // 로그인 된 상태에서만 반응
                            if (userInfo.id != null && !userInfo.id.equals("-1")) {
                                if (favoriteState) {
                                    favoriteViewModel.addFavorite(userInfo.id!!, csInfo.first())
                                } else {
                                    favoriteViewModel.deleteFavorite(userInfo.id!!, favoriteInfo.csId)
                                }
                            }
                        }
                    ) {
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
                        text = csInfo.first().address,
                        fontSize = 13.sp
                    )
                }

                // 충전기 정보 표시 (아이콘 제외)
                csInfo.forEach {
                    ChargeInfoScreen(it)
                }
            }
        }
    }
}