package com.seo.domain.repository

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Favorite

/**
 * favorite repository interface
 * */
interface FavoriteRepository<T> {
    suspend fun create(data: T): FireResult<T>
    suspend fun delete(userId: String, csId: String): FireResult<Boolean>
    suspend fun findByUserId(userId: String): FireResult<MutableList<Favorite>>
    suspend fun findByUserIdAndCsId(userId: String, csId: String): FireResult<Favorite>
}