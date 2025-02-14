package com.seo.sesac.domain.repository

import com.seo.sesac.data.common.FireResult

/**
 * repository interface
 * */
interface FireStoreRepository<T> {
    suspend fun create(data: T): FireResult<T>
    suspend fun delete(id: Long): FireResult<Boolean>
    suspend fun update(data: T): FireResult<Boolean>
    suspend fun findById(id: String): FireResult<T>
}
