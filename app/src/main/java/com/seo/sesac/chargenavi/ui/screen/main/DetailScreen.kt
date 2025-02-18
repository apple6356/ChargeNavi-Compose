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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.seo.sesac.chargenavi.ui.navigation.NavigationRoute
import com.seo.sesac.chargenavi.ui.screen.common.ChargeInfoItem
import com.seo.sesac.chargenavi.ui.screen.common.CircularProgress
import com.seo.sesac.chargenavi.ui.screen.common.ReviewContentItem
import com.seo.sesac.chargenavi.ui.screen.common.dividerModifier
import com.seo.sesac.chargenavi.viewmodel.FavoriteViewModel
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.chargenavi.viewmodel.ReviewViewModel
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.mainViewModelFactory
import com.seo.sesac.chargenavi.viewmodel.factory.userViewModelFactory
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.common.RestResult
import com.seo.sesac.data.entity.EvCsInfo
import com.seo.sesac.data.entity.Review
import com.seo.sesac.data.entity.UserInfo
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
    reviewViewModel: ReviewViewModel = viewModel(),
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
            csInfo = mainViewModel.findByCsId(csId).values
                .flatten() // flatten 을 사용해 여러 list 를 하나의 list 로 통합
                .sortedBy { it.cpId } // cpId(충전기 Id) 순으로 정렬
        }
    }

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

    // 즐겨찾기 상태 관리
    var favoriteState by remember {
        mutableStateOf(false)
    }

    // 즐겨찾기 상태 갱신
    LaunchedEffect(key1 = userInfo) {

        if (!userInfo.id.equals("-1") && userInfo.id != null) {
            favoriteState = favoriteViewModel.isFavorite(userInfo.id!!, csId)
        }
    }

    // 현재 충전소의 리뷰 정보 불러오기
    LaunchedEffect(Unit) {
        reviewViewModel.findByCsIdOrderByCreateTime(csId)
    }

    // 리뷰 목록
    var reviewList by remember {
        mutableStateOf<List<Review>>(emptyList())
    }

    // 리뷰 목록 가져오기
    LaunchedEffect(key1 = reviewViewModel.reviewList) {
        reviewViewModel.reviewList.collectLatest {
            if (it is FireResult.Success) {
                reviewList = it.data.sortedByDescending { // 최신순 정렬
                    it.createTime
                }
            }
        }
    }

    // 스크롤 상태
    val scrollState = rememberScrollState()

    if (csInfo.isEmpty() && reviewList.isEmpty()) {
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
                            if (userInfo.id != null && !userInfo.id.equals("-1")) {
                                if (favoriteState) {
                                    favoriteViewModel.addFavorite(userInfo.id!!, csInfo.first())
                                } else {
                                    favoriteViewModel.deleteFavorite(userInfo.id!!, csId)
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

                // 충전기 정보
                csInfo.forEach { csInfo ->
                    ChargeInfoItem(csInfo)
                }

                HorizontalDivider(
                    modifier = Modifier.dividerModifier(),
                    color = Color.LightGray
                )

                // 리뷰
                Text(
                    modifier = Modifier
                        .padding(
                            bottom = 20.dp
                        ),
                    text = "리뷰",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold
                )

                // 최신 리뷰 3개 보이기
                reviewList.take(3).forEach {
                    ReviewContentItem(it, userInfo.id.toString())
                }

                // 충전소의 모든 리뷰 보기
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            navController.navigate("${NavigationRoute.ReviewList.routeName}/${csId}/${userInfo.id}")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "전체 리뷰 버튼",
                            tint = Color.Blue
                        )

                        Text(
                            text = "리뷰 더보기",
                            fontSize = 15.sp,
                            color = Color.Blue
                        )}
                }

            }

        }
    }
}