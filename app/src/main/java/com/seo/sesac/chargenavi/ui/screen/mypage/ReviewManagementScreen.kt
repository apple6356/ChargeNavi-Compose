package com.seo.sesac.chargenavi.ui.screen.mypage

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.ui.screen.common.dividerModifier
import com.seo.sesac.chargenavi.ui.screen.mypage.list.MyReviewListScreen
import com.seo.sesac.chargenavi.viewmodel.ReviewViewModel
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.userViewModelFactory
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Review
import com.seo.sesac.data.entity.UserInfo
import kotlinx.coroutines.flow.collectLatest

/**
 * 리뷰 관리 화면,
 * 자신의 리뷰 목록을 조회 및 삭제
 * */
@SuppressLint("MutableCollectionMutableState")
@Composable
fun ReviewManagementScreen(
    navController: NavController,
    reviewViewModel: ReviewViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(factory = userViewModelFactory)
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

    // 현재 충전소의 리뷰 정보 불러오기
    LaunchedEffect(userInfo) {
        if (userInfo.id != "-1") {
            reviewViewModel.findByUserId(userInfo.id.toString())
        }
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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {

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
                text = stringResource(R.string.review_management_text),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider(
                modifier = Modifier
                    .dividerModifier()
            )

            MyReviewListScreen(reviewList)
        }
    }
}
