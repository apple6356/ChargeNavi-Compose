package com.seo.sesac.firestore.repository

import com.seo.sesac.data.entity.UserInfo
import com.seo.sesac.data.common.FireResult

/**
 * repository interface
 * */
interface FireStoreRepository<T> {
    suspend fun create(data: T): FireResult<UserInfo>
//    suspend fun delete(id: Long): Result<Boolean>
//    suspend fun update(data: T): Result<Boolean>
    suspend fun findById(id: String): FireResult<T>
}