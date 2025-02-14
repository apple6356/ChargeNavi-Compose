package com.seo.sesac.firestore.datasource.firestore

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.firestore.common.FirestoreCollectionFilter
import com.seo.domain.entity.Review
import com.seo.domain.datasource.ReviewDataSource
import kotlinx.coroutines.tasks.await

class ReviewDataSourceImpl: com.seo.domain.datasource.ReviewDataSource<Review> {
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

    override fun findByUserId() {
        TODO("Not yet implemented")
    }

    override fun findByCsIdOrderByCreateTime() {
        TODO("Not yet implemented")
    }

    override fun findByCsIdOrderByLike() {
        TODO("Not yet implemented")
    }
}