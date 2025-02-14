package com.seo.sesac.chargenavi.viewmodel

import androidx.lifecycle.ViewModel
import com.seo.sesac.data.common.FireResult
import com.seo.domain.entity.Review
import com.seo.sesac.firestore.repository.firestore.ReviewRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * 리뷰 관련 viewmodel
 * 리뷰 목록 화면, 리뷰 관리 화면 -> 조회, 삭제
 * 리뷰 작성 화면 -> 저장
 * 25-02-14
 * */
class ReviewViewModel(
    private val reviewRepository : ReviewRepositoryImpl
) : ViewModel() {

    /**
     * 리뷰 목록
     * */
    val _reviewList = MutableStateFlow<FireResult<MutableList<Review>>>(FireResult.DummyConstructor)
    val reviewList get() = _reviewList

    /**
     * 리뷰 저장
     * */
/*    suspend fun wirteReview() {
        reviewRepository.create()
    }*/

}