package com.seo.sesac.firestore.repository.firestore

import com.seo.sesac.data.entity.Favorite
import com.seo.sesac.domain.repository.FavoriteRepository
import com.seo.sesac.firestore.datasource.firestore.FavoriteDataSourceImpl

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
