package com.seo.sesac.chargenavi.ui.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.ui.navigation.NavigationRoute
import com.seo.sesac.chargenavi.ui.screen.main.List.ReviewListScreen
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.mainViewModelFactory

/**
 * 충전소의 모든 리뷰 보여주는 화면
 * */
@Composable
fun AllReviewScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory),
    csId: String
) {
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

            // 임시 데이터
            val reviewList = mutableListOf("1번 리뷰", "2번 리뷰", "3번 리뷰")

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