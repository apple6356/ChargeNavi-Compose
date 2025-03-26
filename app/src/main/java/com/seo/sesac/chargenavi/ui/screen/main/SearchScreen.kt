package com.seo.sesac.chargenavi.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.ui.screen.main.list.SearchListScreen
import com.seo.sesac.chargenavi.viewmodel.MainViewModel
import com.seo.sesac.chargenavi.viewmodel.factory.mainViewModelFactory

/**
 * 검색 화면,
 * 1. 검색 내용이 충전소 이름과 같다면 해당 충전소 Marker 생성
 * 2. 네이버 검색 API (지역)을 검색 내용의 주소를 받아와 해당 지역의 충전소 검색 결과 Marker 생성
 * 3. 검색 결과가 없다면 검색 결과가 없다는 것을 알림
 * */
@Composable
fun SearchScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {

    // 검색 키워드
    var searchKeyword by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column {

            // 검색 창
            Row {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = searchKeyword,
                    onValueChange = {
                        searchKeyword = it
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.search_placeholder),
                            fontSize = 15.sp,
                            color = Color.LightGray
                        )
                    },
                    textStyle = TextStyle(fontSize = 15.sp),
                    singleLine = true,
                    leadingIcon = { // 뒤로가기 버튼 추가
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
                    },
                    trailingIcon = {
                        // 검색 창 초기화 버튼
                        IconButton(
                            onClick = {
                                searchKeyword = ""
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = null
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        // TextField 바탕 색
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        // TextField 밑줄 색 설정, 투명
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            mainViewModel.getEvCsList(addr = searchKeyword)
                            navController.popBackStack()
                        }
                    )
                )
            }

            HorizontalDivider()

            // 이전 검색 기록 목록
            SearchListScreen(emptyList())

        }
    }
}