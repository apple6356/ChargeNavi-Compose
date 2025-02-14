package com.seo.sesac.domain.repository

import com.seo.sesac.data.common.FireResult

/**
 * favorite repository interface
 * */
interface FavoriteRepository<T> {
    suspend fun create(data: T): FireResult<T>
    suspend fun delete(userId: String, csId: String): FireResult<Boolean>
    suspend fun update(data: T): FireResult<Boolean>
    suspend fun findById(id: String): FireResult<T>

}