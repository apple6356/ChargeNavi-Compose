package com.seo.firestore.repository.firestore

import com.seo.firestore.datasource.firestore.ReviewDataSourceImpl
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Review
import com.seo.domain.repository.ReviewRepository

/**
 * firestore review repository
 * */
class ReviewRepositoryImpl(private val reviewDataSourceImpl: ReviewDataSourceImpl):
    ReviewRepository<Review> {
    override suspend fun create(data: Review): FireResult<Review> =
        reviewDataSourceImpl.create(data)

    override suspend fun findByUserId(userId: String) =
        reviewDataSourceImpl.findByUserId(userId)

    override suspend fun findByCsIdOrderByCreateTime(csId: String) =
        reviewDataSourceImpl.findByCsIdOrderByCreateTime(csId)

    override suspend fun deleteReview(id: String): FireResult<Boolean> =
        reviewDataSourceImpl.deleteReview(id)

}