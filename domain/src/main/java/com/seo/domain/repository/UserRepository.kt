package com.seo.domain.repository

import com.seo.sesac.data.common.FireResult

/**
 * user repository interface
 * */
interface UserRepository<T> {
    suspend fun create(data: T): FireResult<T>
    suspend fun update(userInfo: T): FireResult<Boolean>
    suspend fun findById(id: String): FireResult<T>
}