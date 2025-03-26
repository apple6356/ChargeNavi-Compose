package com.seo.sesac.chargenavi.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.common.ChangedStarRatingBar
import com.seo.sesac.chargenavi.common.showToast
import com.seo.sesac.chargenavi.ui.screen.common.dividerModifier
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.chargenavi.viewmodel.ReviewViewModel
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.mainViewModelFactory
import com.seo.sesac.chargenavi.viewmodel.factory.userViewModelFactory
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.UserInfo
import kotlinx.coroutines.flow.collectLatest

/**
 * 리뷰 작성 화면
 * */
@Composable
fun ReviewWriteScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory),
    userViewModel: UserViewModel = viewModel(factory = userViewModelFactory),
    reviewViewModel: ReviewViewModel = viewModel(),
    csId: String
) {

    // 리뷰 내용
    var reviewContent by remember {
        mutableStateOf("")
    }

    // 별점
    var rating by remember {
        mutableStateOf(0)
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

    // 리뷰 저장 시 뒤로 이동
    LaunchedEffect(key1 = reviewViewModel.reviewCompleteEvent) {
        reviewViewModel.reviewCompleteEvent.collect {
            navController.popBackStack()
        }
    }

    // 입력 영역 스크롤 상태
    val writeScrollState = rememberScrollState()

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
                .fillMaxSize()
        ) {
            // 뒤로가기
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.review_write_text),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            // 별점
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 40.dp
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ChangedStarRatingBar(5, rating) {
                    rating = it
                }
            }

            HorizontalDivider(
                modifier = Modifier.dividerModifier(),
                color = Color.LightGray
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // 작성 완료 버튼
                TextButton(
                    onClick = {
                        // 작성된 리뷰 정보를 저장하고 DetailScreen 으로 화면 전환

                        if (rating <= 0) {
                            showToast(R.string.star_rating_write.toString())
                        } else {
                            reviewViewModel.writeReview(reviewContent, rating, userInfo, csId)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = null,
                        tint = Color.Blue
                    )

                    Text(
                        text = stringResource(R.string.save_text),
                        fontSize = 15.sp,
                        color = Color.Blue
                    )
                }
            }

            // 리뷰 작성 칸
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .verticalScroll(writeScrollState)
                    .padding(
                        vertical = 10.dp
                    ),
                value = reviewContent,
                onValueChange = {
                    reviewContent = it
                },
                textStyle = TextStyle(fontSize = 15.sp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.review_write_guide),
                        color = Color.LightGray
                    )
                },
                colors = TextFieldDefaults.colors(
                    // TextField 바탕 색
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    // TextField 밑줄 색 설정, 투명
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}