package com.seo.domain.datasource

import com.seo.sesac.data.entity.Review
import com.seo.sesac.data.common.FireResult

interface ReviewDataSource<T> {
    suspend fun create(data: Review): FireResult<T>
    fun delete()
    fun findByUserId()
    suspend fun findByCsIdOrderByCreateTime(csId: String): FireResult<MutableList<T>>
    fun findByCsIdOrderByLike()
}