package com.seo.sesac.chargenavi.ui.screen.main.List

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 리뷰 목록 화면
 * FireStore 에서 Review 데이터 받아와 구현
 * */
@Composable
fun ReviewListScreen(reviewList: List<String>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 20.dp
            )
    ) {
        items(reviewList) {review ->

            // 좋아요 상태
            var likeState by remember {
                mutableStateOf(false)
            }

            // test code : review 테스트
            Text(
                text = review
            )

            Text(
                text = "별점"
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "작성자 프로필 이미지"
                )
                Column {
                    Text(
                        text = "작성자 닉네임"
                    )
                    Text(
                        text = "작성 시간"
                    )
                }

                Row {
                    IconButton(
                        onClick = {
                            likeState = !likeState
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ThumbUp,
                            contentDescription = "좋아요",
                            tint = if (likeState) Color.Black else Color.LightGray
                        )
                    }

                    Text(text = "좋아요 수")
                }
            }
            Text(
                text = "리뷰 내용"
            )

            HorizontalDivider(
                color = Color.LightGray
            )
        }
    }
}