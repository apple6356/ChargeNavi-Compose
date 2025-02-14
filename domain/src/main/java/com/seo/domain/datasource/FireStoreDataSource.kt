package com.seo.domain.datasource

import com.seo.sesac.data.common.FireResult

/**
 * datasource interface
 * */
interface FireStoreDataSource<T> {
    suspend fun create(data: T): FireResult<T>
    suspend fun delete(id: Long): FireResult<Boolean>
    suspend fun update(data: T): FireResult<Boolean>
    suspend fun findById(id: String): T
}
