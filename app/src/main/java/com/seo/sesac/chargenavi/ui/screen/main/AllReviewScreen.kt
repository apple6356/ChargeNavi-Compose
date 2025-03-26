package com.seo.sesac.chargenavi.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
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
import com.seo.sesac.data.entity.Review
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.ui.navigation.NavigationRoute
import com.seo.sesac.chargenavi.ui.screen.main.list.ReviewListScreen
import com.seo.sesac.chargenavi.viewmodel.ReviewViewModel
import com.seo.sesac.data.common.FireResult
import kotlinx.coroutines.flow.collectLatest

/**
 * 충전소의 모든 리뷰 보여주는 화면
 * */
@Composable
fun AllReviewScreen(
    navController: NavController,
    reviewViewModel: ReviewViewModel = viewModel(),
    csId: String,
    userId: String
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
                reviewList = it.data.sortedByDescending {
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
            modifier = Modifier
                .fillMaxWidth()
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

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.review_text),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                TextButton(
                    onClick = {
                        navController.navigate("${NavigationRoute.ReviewWrite.routeName}/${csId}")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        tint = Color.Blue
                    )

                    Text(
                        text = stringResource(R.string.review_write_text),
                        fontSize = 15.sp,
                        color = Color.Blue
                    )
                }
            }

            HorizontalDivider(
                color = Color.LightGray
            )

            // 리뷰 목록
            ReviewListScreen(reviewList, userId)
        }
    }

}