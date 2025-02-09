package com.seo.sesac.domain.datasource

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.UserInfo

/**
 * datasource interface
 * */
interface FireStoreDataSource<T> {
    suspend fun create(data: T): T
    suspend fun delete(id: Long): FireResult<Boolean>
    suspend fun update(data: T): FireResult<Boolean>
    suspend fun findById(id: String): T
}
