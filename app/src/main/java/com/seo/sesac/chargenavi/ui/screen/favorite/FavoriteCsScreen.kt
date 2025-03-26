package com.seo.sesac.chargenavi.ui.screen.favorite

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seo.sesac.chargenavi.ui.screen.common.ChargeInfoScreen
import com.seo.sesac.chargenavi.ui.screen.common.CircularProgress
import com.seo.sesac.chargenavi.viewmodel.FavoriteViewModel
import com.seo.sesac.data.common.RestResult
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

    // 즐겨찾기 된 충전소 리스트 상태
    val favoriteCsListState by favoriteViewModel.favoriteCsList.collectAsStateWithLifecycle()

    if (favoriteCsListState is RestResult.Success) {

        // 즐겨찾기 된 충전소 리스트
        val favoriteCsList = (favoriteCsListState as RestResult.Success).data
            .groupBy { it.csId } // csId 별로 그룹화 Map<Int(csId), List<EvCsInfo>>
            .filter { it.key == favoriteInfo.csId.toInt() } // key(csId) 값이 favoriteInfo 의 csId 값과 같은 것만 필터링
            .values // Map 의 values 만 추출
            .flatten() // Collection<List<EvCsInfo>> 타입을 List<EvCsInfo>로 변환
            .sortedBy { it.cpId } // cpId 순으로 정렬

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
                        text = favoriteCsList.first().csNm,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // 즐겨찾기 버튼
                    IconButton(
                        onClick = {
                            favoriteState = !favoriteState // 로그인 된 상태에서만 반응
                            if (userInfo.id != null && !userInfo.id.equals("-1")) {
                                if (favoriteState) {
                                    favoriteViewModel.addFavorite(userInfo.id!!, favoriteCsList.first() /*csInfo.first()*/)
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
                        contentDescription = null,
                        tint = Color.LightGray
                    )

                    Text(
                        text = favoriteCsList.first().address,
                        fontSize = 13.sp
                    )
                }

                // 충전기 정보 표시 (아이콘 제외)
                favoriteCsList.forEach {
                    ChargeInfoScreen(it)
                }
            }
        }
    } else {
        CircularProgress()
    }
}