package com.seo.sesac.chargenavi.ui.screen.mypage.list

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.ThumbUp
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seo.sesac.chargenavi.R
import com.seo.sesac.chargenavi.common.ReadOnlyStarRatingBar
import com.seo.sesac.chargenavi.common.showToast
import com.seo.sesac.chargenavi.ui.screen.common.dividerModifier
import com.seo.sesac.chargenavi.viewmodel.ReviewViewModel
import com.seo.sesac.data.entity.Review

/**
 * 자신이 쓴 리뷰 리스트
 * */
@Composable
fun MyReviewListScreen(
    reviewList: List<Review>,
    reviewViewModel: ReviewViewModel = viewModel()
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 10.dp
            )
    ){
        items(reviewList) {reviewInfo ->

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
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
                    )  {

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
                                    showToast(R.string.cannot_self_recommend.toString())
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ThumbUp,
                                    contentDescription = null,
                                    tint = Color.LightGray
                                )
                            }

                            // 좋아요 수
                            Text(
                                text = reviewInfo.likeCount.toString(),
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // 작성 시간
                            Text(
                                text = reviewInfo.createDate,
                                fontSize = 10.sp,
                                color = Color.LightGray
                            )

                            IconButton(
                                onClick = {
                                    // 리뷰 삭제
                                    reviewViewModel.deleteReview(reviewInfo)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = null,
                                    tint = Color.Red
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

    }
}