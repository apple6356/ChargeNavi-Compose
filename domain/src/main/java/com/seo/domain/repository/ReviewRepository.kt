package com.seo.domain.repository

import com.seo.sesac.data.common.FireResult

/**
 * user review interface
 * */
interface ReviewRepository<T> {
    suspend fun create(data: T): FireResult<T>
    suspend fun findByUserId(userId: String): FireResult<MutableList<T>>
    suspend fun findByCsIdOrderByCreateTime(csId: String): FireResult<MutableList<T>>
    suspend fun deleteReview(id: String): FireResult<Boolean>
}