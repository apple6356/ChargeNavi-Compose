package com.seo.sesac.chargenavi.ui.screen.mypage.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.seo.sesac.chargenavi.ui.screen.common.dividerModifier

/**
 * 자신이 쓴 리뷰 리스트
 * */
@Composable
fun MyReviewListScreen(reviewList: MutableList<String>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        items(reviewList) {review ->

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(text = "별점")
                        Text(text = "리뷰 내용")
                        Text(text = "작성 시간", color = Color.LightGray)
                    }

                    IconButton(
                        onClick = {
                            // review 삭제
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "리뷰 삭제"
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .dividerModifier()
            )
        }

    }
}