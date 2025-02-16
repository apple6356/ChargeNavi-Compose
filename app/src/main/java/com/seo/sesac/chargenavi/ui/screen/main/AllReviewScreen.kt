package com.seo.sesac.chargenavi.ui.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.seo.sesac.data.entity.Review
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.ui.navigation.NavigationRoute
import com.seo.sesac.chargenavi.ui.screen.main.List.ReviewListScreen
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.chargenavi.viewmodel.ReviewViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.mainViewModelFactory
import com.seo.sesac.data.common.FireResult
import kotlinx.coroutines.flow.collectLatest

/**
 * 충전소의 모든 리뷰 보여주는 화면
 * */
@Composable
fun AllReviewScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory),
    reviewViewModel: ReviewViewModel = viewModel(),
    csId: String
) {

    // 리뷰 정보
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
                reviewList = it.data.sortedBy {
                    it.createTime
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 10.dp,
                end = 10.dp
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 뒤로가기 버튼
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.back_button)
                )
            }

            Text(
                text = "리뷰",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider(
                color = Color.LightGray
            )

            // 리뷰 목록
            ReviewListScreen(reviewList)

            // 리뷰 작성 이동 버튼
            Button(
                onClick = {
                    navController.navigate(NavigationRoute.ReviewWrite.routeName)
                }
            ) {
                Text(text = "리뷰 작성 화면 이동")
            }
        }
    }
}