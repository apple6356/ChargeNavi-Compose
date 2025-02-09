package com.seo.sesac.firestore.repository

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Favorite
import com.seo.sesac.domain.repository.FireStoreRepository
import com.seo.sesac.firestore.datasource.FavoriteDataSourceImpl

class FavoriteRepositoryImpl(private val favoriteDataSourceImpl: FavoriteDataSourceImpl) :
    FireStoreRepository<Favorite> {

    override suspend fun create(data: Favorite) =
        FireResult.Success(favoriteDataSourceImpl.create(data))

    override suspend fun delete(id: Long): FireResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun update(data: Favorite): FireResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: String) =
        FireResult.Success(favoriteDataSourceImpl.findById(id))

    suspend fun findByUserId(userId: String) =
        FireResult.Success(favoriteDataSourceImpl.findByUserId(userId))

    suspend fun findByUserIdAndCsId(userId: String, csId: Int) =
        FireResult.Success(favoriteDataSourceImpl.findByUserIdAndCsId(userId, csId))
}
