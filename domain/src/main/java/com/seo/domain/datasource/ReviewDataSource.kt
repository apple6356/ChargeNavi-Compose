package com.seo.domain.datasource

import com.seo.domain.entity.Review
import com.seo.sesac.data.common.FireResult

interface ReviewDataSource<T> {
    suspend fun create(data: Review): FireResult<T>
    fun delete()
    fun findByUserId()
    fun findByCsIdOrderByCreateTime()
    fun findByCsIdOrderByLike()
}