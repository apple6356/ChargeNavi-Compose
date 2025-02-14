package com.seo.sesac.domain.repository

import com.seo.sesac.data.common.FireResult

interface ReviewRepository<T> {
    suspend fun create(data: T): FireResult<T>
    fun delete()
    fun findByUserId()
    fun findByCsId()
}