package com.seo.sesac.domain.repository

import com.seo.sesac.data.common.FireResult

interface ReviewRepository<T> {
    suspend fun create(data: T): FireResult<T>
    fun delete()
    fun findByUserId()
    suspend fun findByCsIdOrderByCreateTime(csId: String): FireResult<MutableList<T>>
    fun findByCsIdOrderByLike()
}