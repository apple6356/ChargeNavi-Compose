package com.seo.sesac.chargenavi.ui.screen.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.common.showToast
import com.seo.sesac.chargenavi.ui.navigation.NavigationRoute
import com.seo.sesac.chargenavi.ui.screen.common.dividerModifier
import com.seo.sesac.chargenavi.viewmodel.UserViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.userViewModelFactory
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.UserInfo
import kotlinx.coroutines.flow.collectLatest

/**
 * 마이페이지 화면,
 * 사용자의 간단한 정보 및 다른 화면으로 이동
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(factory = userViewModelFactory)
) {
    val context = LocalContext.current

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 10.dp,
                end = 10.dp
            )
    ) {
        Column {
            // 사용자 프로필
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 프로필 사진
                    Image(
                        painter = painterResource(R.drawable.image_user_default_profile_),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(
                                70.dp
                            )
                            .padding(
                                end = 10.dp
                            )
                    )

                    // 닉네임
                    Text(
                        text = (if (userInfo.id != "-1") userInfo.nickname else "게스트").toString(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = {
                        // 설정 화면으로 이동
                        navController.navigate(NavigationRoute.Settings.routeName)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "설정",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.dividerModifier()
            )

            MenuCard(
                title = "프로필 관리",
                description = "프로필 관리",
                onClick = {
                    if (userInfo.id != "-1") {
                        navController.navigate(NavigationRoute.EditProfile.routeName)
                    }
                }
            )

            MenuCard(
                title = "리뷰 관리",
                description = "리뷰 관리",
                onClick = {
                    if (userInfo.id != "-1") {
                        navController.navigate(NavigationRoute.ReviewManagement.routeName)
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 로그인 시 로그아웃 버튼, 로그인 되어 있지 않을 시 로그인 버튼 출력
                // naver login button
                Image(
                    painter = painterResource(R.drawable.btn_naver_short_login),
                    contentDescription = "네이버 로그인 버튼",
                    modifier = Modifier
                        .width(150.dp)
                        .padding(
                            top = 10.dp,
                            bottom = 10.dp
                        )
                        .clickable { /* naver login */
                            userViewModel.loginNaver(context)
                        },
                    contentScale = ContentScale.FillWidth
                )

            }

        }
    }
}

/**
 * mypage menu card
 * */
@Composable
fun MenuCard(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = title,
                fontSize = 30.sp
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = description
            )
        }
    }

    HorizontalDivider(
        modifier = Modifier.dividerModifier()
    )
}