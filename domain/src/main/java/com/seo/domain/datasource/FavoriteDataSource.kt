package com.seo.domain.datasource

import com.seo.sesac.data.common.FireResult

/**
 * favorite datasource interface
 * */
interface FavoriteDataSource<T> {
    suspend fun create(data: T): FireResult<T>
    suspend fun delete(userId: String, csId: String): FireResult<Boolean>
    suspend fun update(data: T): FireResult<Boolean>
    suspend fun findById(id: String): T
}