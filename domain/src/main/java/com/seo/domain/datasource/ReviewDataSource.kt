package com.seo.domain.datasource

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Review

interface ReviewDataSource<T> {
    suspend fun create(data: Review): FireResult<T>
    fun delete()
    suspend fun findByUserId(userId: String): FireResult<MutableList<T>>
    suspend fun findByCsIdOrderByCreateTime(csId: String): FireResult<MutableList<T>>
    fun findByCsIdOrderByLike()
}