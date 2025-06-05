package com.seo.firestore.datasource.firestore

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Review
import com.seo.firestore.common.FirestoreCollectionFilter
import kotlinx.coroutines.tasks.await

/**
 * firestore review datasource
 * */
class ReviewDataSourceImpl {

    suspend fun create(data: Review) = runCatching {
        val addTask = FirestoreCollectionFilter.getReviewCollection().add(data).await()
        val result = addTask.get().await()

        val reviewInfo = result.toObject(Review::class.java) as Review
        FireResult.Success(reviewInfo)
    }.getOrElse {
        FireResult.Failure(Exception("리뷰 저장 중 오류 발생"))
    }

    suspend fun findByUserId(userId: String) = runCatching {
        val resultTask = FirestoreCollectionFilter.getReviewCollection()
            .whereEqualTo("userId", userId)
            .get().await()

        if (!resultTask.isEmpty) {
            val reviewList = resultTask.toObjects(Review::class.java) as MutableList<Review>
            FireResult.Success(reviewList)
        } else {
            FireResult.Failure(Exception("검색 결과가 없습니다."))
        }
    }.getOrElse {
        FireResult.Failure(Exception("데이터 베이스 문제 발생: ${it.message}"))
    }

    suspend fun findByCsIdOrderByCreateTime(csId: String) = runCatching {
        val resultTask = FirestoreCollectionFilter.getReviewCollection()
            .whereEqualTo("csId", csId)
            .get().await()

        if (!resultTask.isEmpty) {
            val reviewList = resultTask.toObjects(Review::class.java) as MutableList<Review>
            FireResult.Success(reviewList)
        } else {
            FireResult.Failure(Exception("검색 결과가 없습니다."))
        }
    }.getOrElse {
        FireResult.Failure(Exception("데이터 베이스 문제 발생: ${it.message}"))
    }

    suspend fun deleteReview(id: String) = runCatching {
        val resultTask = FirestoreCollectionFilter.getReviewCollection()
            .whereEqualTo("id", id)
            .get().await()

        if (!resultTask.isEmpty) {
            FirestoreCollectionFilter.getReviewCollection().document(resultTask.documents.first().id).delete()
            FireResult.Success(true)
        } else {
            FireResult.Failure(Exception("리뷰를 찾을 수 없습니다."))
        }
    }.getOrElse {
        FireResult.Failure(Exception("리뷰 삭제 중 오류 발생: ${it.message}"))
    }
}