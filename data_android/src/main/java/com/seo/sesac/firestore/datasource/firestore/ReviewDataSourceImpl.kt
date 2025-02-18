package com.seo.sesac.firestore.datasource.firestore

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.firestore.common.FirestoreCollectionFilter
import com.seo.sesac.data.entity.Review
import com.seo.domain.datasource.ReviewDataSource
import kotlinx.coroutines.tasks.await

class ReviewDataSourceImpl: ReviewDataSource<Review> {

    override suspend fun create(data: Review) = runCatching {
        val addTask = FirestoreCollectionFilter.getReviewCollection().add(data).await()
        val result = addTask.get().await()

        val reviewInfo = result.toObject(Review::class.java) as Review
        println("create review: ${reviewInfo}")
        FireResult.Success(reviewInfo)
    }.getOrElse {
        FireResult.Failure(Exception("리뷰 저장 중 오류 발생"))
    }

    override fun delete() {
        TODO("Not yet implemented")
    }

    override suspend fun findByUserId(userId: String) = runCatching {
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

    override suspend fun findByCsIdOrderByCreateTime(csId: String) = runCatching {
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

    override fun findByCsIdOrderByLike() {
        TODO("Not yet implemented")
    }
}