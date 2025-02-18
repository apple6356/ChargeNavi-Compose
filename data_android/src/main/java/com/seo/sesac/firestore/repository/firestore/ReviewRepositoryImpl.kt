package com.seo.sesac.firestore.repository.firestore

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Review
import com.seo.sesac.domain.repository.ReviewRepository
import com.seo.sesac.firestore.datasource.firestore.ReviewDataSourceImpl

class ReviewRepositoryImpl(private val reviewDataSourceImpl: ReviewDataSourceImpl): ReviewRepository<Review> {
    override suspend fun create(data: Review): FireResult<Review> =
        reviewDataSourceImpl.create(data)

    override fun delete() {
        TODO("Not yet implemented")
    }

    override suspend fun findByUserId(userId: String) =
        reviewDataSourceImpl.findByUserId(userId)

    override suspend fun findByCsIdOrderByCreateTime(csId: String) =
        reviewDataSourceImpl.findByCsIdOrderByCreateTime(csId)

    override fun findByCsIdOrderByLike() {
        TODO("Not yet implemented")
    }

}