package com.seo.sesac.chargenavi.ui.screen.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.common.chargeTpMap
import com.seo.sesac.chargenavi.common.cpStatMap
import com.seo.sesac.chargenavi.common.cpTpMap
import com.seo.sesac.chargenavi.ui.navigation.NavigationRoute
import com.seo.sesac.chargenavi.ui.screen.common.CircularProgress
import com.seo.sesac.chargenavi.ui.screen.common.dividerModifier
import com.seo.sesac.chargenavi.viewmodel.FavoriteViewModel
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.mainViewModelFactory
import com.seo.sesac.chargenavi.viewmodel.factory.userViewModelFactory
import com.seo.sesac.data.common.RestResult
import com.seo.sesac.data.entity.EvCsInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


/**
 * 충전소에 대한 상세 정보 화면
 * */
@Composable
fun DetailScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory),
    favoriteViewModel: FavoriteViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(factory = userViewModelFactory),
    csId: String
) {

    // 충전소 정보
    var csInfo by remember {
        mutableStateOf<List<EvCsInfo>>(emptyList())
    }

    /** viewModel 에서 충전소 정보 가져오기 */
    LaunchedEffect(key1 = mainViewModel.evCsList) {
        if (mainViewModel.evCsList.value is RestResult.Success) {

            Log.e("csId Detail", csId)
            csInfo =
                mainViewModel.findCSByCsId(csId).values.flatten() // flatten 을 사용해 여러 list 를 하나의 list 로 통합
                    .sortedBy { it.cpId } // cpId(충전기 Id) 순으로 정렬
        }
    }

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

    // 즐겨찾기 상태 관리
    var favoriteState by remember {
        mutableStateOf(false)
    }

    // 즐겨찾기 상태 갱신
    LaunchedEffect(key1 = localUserInfo) {
        Log.e("DetailScreen", "localUserInfo: $localUserInfo , favoriteState $favoriteState")
        val (userId, _) = localUserInfo
        if (userId.isNotEmpty()) {
            favoriteState = favoriteViewModel.isFavorite(localUserInfo.first, csId)
            Log.e("DetailScreen", "즐겨찾기 상태: $favoriteState")
        }
    }

    // 스크롤 상태
    val scrollState = rememberScrollState()

    //
    if (csInfo.isEmpty()) {
        CircularProgress()
    } else {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 10.dp, end = 10.dp
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start,
            ) {

                // 뒤로가기 버튼
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }

                // 충전소 이름
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // 충전소 이름
                    Text(
                        text = csInfo.first().csNm,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // 즐겨찾기 버튼
                    IconButton(
                        onClick = {
                            favoriteState = !favoriteState
                            // 로그인 된 상태에서만 반응
                            if (localUserInfo.first.isNotEmpty()) {
                                if (favoriteState) {
                                    favoriteViewModel.addFavorite(localUserInfo.first, csId)
                                } else {
                                    favoriteViewModel.deleteFavorite(localUserInfo.first, csId)
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
                        .fillMaxWidth()
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

                csInfo.forEach { csInfo ->
                    ChargeInfoItem(csInfo)
                }

                // 최근 리뷰 혹은 좋아요 가장 많은 리뷰 보이기
                // firebase 에 연결하고 review 데이터 가져오면 구현
                // 충전소 리뷰 일부 (전체 화면일 때만 보인다)
                Text(text = "리뷰")
                Text(text = "최신 리뷰1")
                Text(text = "리뷰 더보기 버튼")
                Text(text = "리뷰 작성 버튼")

                // navigation test button
                Button(onClick = {
                    navController.navigate(
                        "${NavigationRoute.ReviewList.routeName}/${csId}"
                    )
                }) {
                    Text(text = "리뷰 더보기")
                }


                Button(onClick = {
                    navController.navigate(NavigationRoute.ReviewWrite.routeName)
                }) {
                    Text(text = "리뷰 작성")
                }
            }

        }
    }
}