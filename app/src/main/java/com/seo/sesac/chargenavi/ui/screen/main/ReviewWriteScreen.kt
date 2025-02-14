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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.mainViewModelFactory

/**
 * 리뷰 작성 화면
 * */
@Composable
fun ReviewWriteScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory)
) {

    var reviewContent by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 10.dp,
                end = 10.dp
            )
    ) {
        Column {
            // 뒤로가기
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
                text = "리뷰 작성",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            // 별점
            Text(text = "별점")

            // 사진 업로드
            Text(text = "사진 업로드")

            // 리뷰 작성 칸
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = reviewContent,
                onValueChange = {
                    reviewContent = it
                },
                textStyle = TextStyle(fontSize = 15.sp),
                placeholder = {
                    Text(
                        text = "리뷰 내용을 작성해주세요.",
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

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) { // 작성 완료 버튼
                Button(
                    onClick = {
                        // reviewContent 를 비롯한 리뷰 작성 정보를 저장하고 DetailScreen 으로 화면 전환
                        // reviewContent, 작성자, 작성 시간, 사진 등
                    }
                ) {
                    Text(
                        text = "리뷰 저장"
                    )
                }
            }
        }
    }
}