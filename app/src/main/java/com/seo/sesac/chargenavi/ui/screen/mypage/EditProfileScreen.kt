package com.seo.sesac.chargenavi.ui.screen.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.ui.screen.common.ProfileImageItem
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.userViewModelFactory
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.UserInfo
import kotlinx.coroutines.flow.collectLatest

/**
 * 프로필 수정 화면,
 * 닉네임, 프로필 사진 변경
 * 프로필 사진 변경 시 이미지 선택 후 변경
 * */
@Composable
fun EditProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(factory = userViewModelFactory)
) {

    // 유저 정보
    var userInfo by remember {
        mutableStateOf(UserInfo())
    }

    // 닉네임
    var updateNickname by remember {
        mutableStateOf("")
    }

    // 유저 정보 읽기
    LaunchedEffect(key1 = userViewModel) {
        userViewModel.userInfo.collectLatest { result ->
            if (result is FireResult.Success) {
                userInfo = result.data
                updateNickname = userInfo.nickname.toString()
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

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(R.string.edit_profile_text),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                // 프로필 사진
                ProfileImageItem(userInfo.profileImage.toString())

                /*Image(
                    painter = painterResource(R.drawable.image_user_default_profile_),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(
                            vertical = 20.dp
                        )
                )*/

                // 프로필 사진 변경 버튼
                /*TextButton(
                    onClick = {
                        // 프로필 사진 변경
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "프로필 사진 변경",
                        tint = Color.Blue
                    )
                    Text(
                        text = "프로필 사진 변경",
                        color = Color.Blue
                    )
                }*/
            }

            // 닉네임 변경
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 20.dp
                    ),
                value = updateNickname,
                onValueChange = {
                    updateNickname = it
                },
                label = {
                    Text(text = stringResource(R.string.edit_nickname_text))
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
            ) {
                // 변경 사항을 저장하는 버튼
                TextButton(onClick = { // 변경된 프로필 사진, 닉네임 저장
                    userInfo.nickname = updateNickname
                    userViewModel.userUpdate(userInfo)
                    navController.popBackStack()
                }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = null,
                        tint = Color.Blue
                    )
                    Text(
                        text = stringResource(R.string.save_profile_text),
                        color = Color.Blue
                    )
                }
            }
        }
    }
}