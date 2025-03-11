package com.seo.sesac.domain.repository

import com.seo.sesac.data.common.FireResult

interface ReviewRepository<T> {
    suspend fun create(data: T): FireResult<T>
    suspend fun findByUserId(userId: String): FireResult<MutableList<T>>
    suspend fun findByCsIdOrderByCreateTime(csId: String): FireResult<MutableList<T>>
    fun findByCsIdOrderByLike()
    suspend fun deleteReview(id: String): FireResult<Boolean>
}