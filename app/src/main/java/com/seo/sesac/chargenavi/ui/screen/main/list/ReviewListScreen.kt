package com.seo.sesac.chargenavi.ui.screen.main.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seo.sesac.data.entity.Review
import com.seo.sesac.chargenavi.ui.screen.common.ReviewContentItem

/**
 * 리뷰 목록 화면
 * FireStore 에서 Review 데이터 받아와 구현
 * */
@Composable
fun ReviewListScreen(reviewList: List<Review>, userId: String) {

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
            ReviewContentItem(review, userId)
        }
    }
}