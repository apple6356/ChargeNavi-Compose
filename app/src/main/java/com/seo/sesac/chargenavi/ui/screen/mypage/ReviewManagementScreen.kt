package com.seo.sesac.chargenavi.ui.screen.mypage

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.ui.screen.mypage.list.MyReviewListScreen

/**
 * 리뷰 관리 화면,
 * 자신의 리뷰 목록을 조회 및 삭제
 * */
@SuppressLint("MutableCollectionMutableState")
@Composable
fun ReviewManagementScreen(navController: NavController) {
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


            val list = mutableListOf("1번 리뷰", "2번 리뷰", "3번 리뷰", "4번 리뷰")

            // 임시 리스트
            val reviewList by remember {
                mutableStateOf(list)
            }

            MyReviewListScreen(reviewList)
        }
    }
}
