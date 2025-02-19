package com.seo.firestore.repository.firestore

import com.seo.firestore.datasource.firestore.FavoriteDataSourceImpl
import com.seo.sesac.data.entity.Favorite
import com.seo.sesac.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(private val favoriteDataSourceImpl: FavoriteDataSourceImpl) :
    FavoriteRepository<Favorite> {

    override suspend fun create(data: Favorite) =
        favoriteDataSourceImpl.create(data)

    override suspend fun delete(userId: String, csId: String) =
        favoriteDataSourceImpl.delete(userId, csId)

    override suspend fun findByUserId(userId: String) =
        favoriteDataSourceImpl.findByUserId(userId)

    override suspend fun findByUserIdAndCsId(userId: String, csId: String) =
        favoriteDataSourceImpl.findByUserIdAndCsId(userId, csId)
}
