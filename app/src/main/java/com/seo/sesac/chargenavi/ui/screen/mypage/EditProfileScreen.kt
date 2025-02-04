package com.seo.sesac.chargenavi.ui.screen.mypage

import androidx.compose.foundation.Image
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.seo.sesac.chargenavi.R

/**
 * 프로필 수정 화면,
 * 닉네임, 프로필 사진 변경
 * 프로필 사진 변경 시 이미지 선택 후 변경
 * */
@Composable
fun EditProfileScreen(navController: NavController) {

    var nickname by remember {
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

            // 프로필 사진(임시)
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null
            )

            // 프로필 사진 변경 버튼
            Button(
                onClick = {
                    // 프로필 사진 변경
                }
            ) {
                Text(text = "프로필 사진 변경")
            }

            // 닉네임 추후 TextField 로 변경
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = nickname,
                onValueChange = {
                    nickname = it
                },
                label = {
                    Text(text = "닉네임 변경")
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

            // 변경 사항을 저장하는 버튼
            Button(
                onClick = {
                    // 변경된 프로필 사진, 닉네임 저장
                }
            ) {
                Text(text = "저장 하기")
            }
        }
    }
}