package com.seo.sesac.chargenavi.ui.screen.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ThumbUp
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seo.sesac.chargenavi.R
import com.seo.sesac.data.entity.Review
import com.seo.sesac.chargenavi.common.ReadOnlyStarRatingBar
import com.seo.sesac.chargenavi.common.showToast

/**
 * 리뷰 출력 screen
 * */
@Composable
fun ReviewContentScreen(
    reviewInfo: Review,
    userId: String
) {

    // 좋아요 상태
    var likeState by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 5.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 작성자
                Text(
                    text = reviewInfo.nickName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                // 별점
                ReadOnlyStarRatingBar(
                    maxStars = 5,
                    rating = reviewInfo.rating
                )
            }

            // 리뷰 내용
            Text(
                text = reviewInfo.content,
                fontSize = 15.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 좋아요 버튼
                    IconButton(
                        onClick = {
                            if (reviewInfo.userId.equals(userId)) {
                                showToast(R.string.cannot_self_recommend.toString())
                            } else {
                                likeState = !likeState
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ThumbUp,
                            contentDescription = null,
                            tint = if (likeState) Color.Blue else Color.LightGray
                        )
                    }

                    // 좋아요 수
                    Text(
                        text = reviewInfo.likeCount.toString(),
                    )
                }

                // 작성 시간
                Text(
                    text = reviewInfo.createDate,
                    fontSize = 10.sp,
                    color = Color.LightGray
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .dividerModifier(),
                color = Color.LightGray
            )
        }
    }
}