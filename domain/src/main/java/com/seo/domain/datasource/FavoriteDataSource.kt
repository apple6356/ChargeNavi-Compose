package com.seo.domain.datasource

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Favorite

/**
 * favorite datasource interface
 * */
interface FavoriteDataSource<T> {
    suspend fun create(data: T): FireResult<T>
    suspend fun delete(userId: String, csId: String): FireResult<Boolean>
    suspend fun findByUserId(userId: String): FireResult<MutableList<Favorite>>
    suspend fun findByUserIdAndCsId(userId: String, csId: String): FireResult<Favorite>
}