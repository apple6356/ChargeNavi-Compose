package com.seo.firestore.repository.firestore

import com.seo.firestore.datasource.firestore.ReviewDataSourceImpl
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Review
import com.seo.sesac.domain.repository.ReviewRepository

class ReviewRepositoryImpl(private val reviewDataSourceImpl: ReviewDataSourceImpl): ReviewRepository<Review> {
    override suspend fun create(data: Review): FireResult<Review> =
        reviewDataSourceImpl.create(data)

    override suspend fun findByUserId(userId: String) =
        reviewDataSourceImpl.findByUserId(userId)

    override suspend fun findByCsIdOrderByCreateTime(csId: String) =
        reviewDataSourceImpl.findByCsIdOrderByCreateTime(csId)

    override fun findByCsIdOrderByLike() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReview(id: String): FireResult<Boolean> =
        reviewDataSourceImpl.deleteReview(id)

}