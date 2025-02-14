package com.seo.sesac.firestore.repository.firestore

import com.seo.sesac.data.common.FireResult
import com.seo.domain.entity.Review
import com.seo.sesac.domain.repository.ReviewRepository
import com.seo.sesac.firestore.datasource.firestore.ReviewDataSourceImpl

class ReviewRepositoryImpl(private val reviewDataSourceImpl: ReviewDataSourceImpl): ReviewRepository<Review> {
    override suspend fun create(data: Review): FireResult<Review> =
        reviewDataSourceImpl.create(data)

    override fun delete() {
        TODO("Not yet implemented")
    }

    override fun findByUserId() {
        TODO("Not yet implemented")
    }

    override fun findByCsId() {
        TODO("Not yet implemented")
    }
}