package com.seo.sesac.chargenavi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo.sesac.data.common.FireResult
import com.seo.firestore.datasource.firestore.ReviewDataSourceImpl
import com.seo.firestore.repository.firestore.ReviewRepositoryImpl
import com.seo.sesac.data.entity.Review
import com.seo.sesac.data.entity.UserInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * 리뷰 관련 viewmodel
 * 리뷰 목록 화면, 리뷰 관리 화면 -> 조회, 삭제
 * 리뷰 작성 화면 -> 저장
 * 25-02-14
 * */
class ReviewViewModel(
    private val reviewRepository : ReviewRepositoryImpl = ReviewRepositoryImpl(ReviewDataSourceImpl())
) : ViewModel() {

    /**
     * 리뷰 목록
     * */
    val _reviewList = MutableStateFlow<FireResult<MutableList<Review>>>(FireResult.DummyConstructor)
    val reviewList get() = _reviewList

    private val _reviewCompleteEvent = MutableSharedFlow<Unit>()
    val reviewCompleteEvent = _reviewCompleteEvent.asSharedFlow()

    /**
     * 리뷰 저장
     * */
    fun writeReview(reviewContent: String, rating: Int, userInfo: UserInfo, csId: String) {
        viewModelScope.launch {
            var reviewInfo = Review()

            if (userInfo.id != null && userInfo.nickname != null) {
                reviewInfo = Review(
                    csId = csId,
                    userId = userInfo.id.toString(),
                    nickName = userInfo.nickname.toString(),
                    content = reviewContent,
                    rating = rating
                )
            }

            val result = reviewRepository.create(reviewInfo)

            if (result is FireResult.Success) {
                Log.e("RVM write", "${result.data}")
                _reviewCompleteEvent.emit(Unit)
            } else {
                Log.e("RVM write", "${(result as FireResult.Failure).exception}")
            }
        }
    }

    /**
     * 충전소의 리뷰를 작성 시간을 기준으로 정렬해 불러온다
     * */
    fun findByCsIdOrderByCreateTime(csId: String) {
        viewModelScope.launch {
            val result = reviewRepository.findByCsIdOrderByCreateTime(csId)

            if (result is FireResult.Success) {
                _reviewList.value = result
                Log.e("RVM", "findByCsIdOrderByCreateTime : ${result}")
            } else {
                Log.e("RVM", "findByCsIdOrderByCreateTime : ${(result as FireResult.Failure).exception}")
            }
        }
    }

    /**
     * 유저의 리뷰 목록
     * */
    fun findByUserId(userId: String) {
        viewModelScope.launch {
            val result = reviewRepository.findByUserId(userId)

            if (result is FireResult.Success) {
                _reviewList.value = result
                Log.e("RVM", "findByUserId : ${result}")
            } else {
                Log.e("RVM", "findByUserId : ${result}")

            }
        }
    }

    /**
     * 리뷰 삭제
     * */
    fun deleteReview(review: Review) {
        viewModelScope.launch {
            val result = reviewRepository.deleteReview(review.id)

            if (result is FireResult.Success) {
                _reviewList.value = reviewRepository.findByUserId(review.userId)
                Log.e("RVM", "deleteReview : ${result}")
            } else {
                Log.e("RVM", "deleteReview : ${result}")

            }
        }
    }

}