package com.seo.sesac.firestore.datasource

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Favorite
import com.seo.sesac.domain.datasource.FireStoreDataSource
import com.seo.sesac.firestore.common.FirestoreCollectionFilter
import kotlinx.coroutines.tasks.await

/**
 * firestore favorite datasource
 * */
class FavoriteDataSourceImpl: FireStoreDataSource<Favorite> {

    override suspend fun create(data: Favorite): Favorite {
        val favoriteInfo = FirestoreCollectionFilter.getFavoriteFirestoreCollection().add(data).await().get().result.data as Favorite
        println("favorite create : $favoriteInfo")
        return favoriteInfo
    }

    override suspend fun delete(id: Long): FireResult<Boolean> {
        TODO("Not yet implemented")
//        FirestoreCollectionFilter.getFavoriteFirestoreCollection().document().delete()
    }

    override suspend fun update(data: Favorite): FireResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: String): Favorite {
        val resultTask =
            FirestoreCollectionFilter.getFavoriteFirestoreCollection().whereEqualTo("id", id).limit(1).get().await()

        return if (!resultTask.isEmpty) {
            resultTask.toObjects(Favorite::class.java) as Favorite
        } else {
            Favorite()
        }
    }

    suspend fun findByUserId(userId: String): MutableList<Favorite> {
        val resultTask =  FirestoreCollectionFilter.getFavoriteFirestoreCollection().whereEqualTo("userId", userId).get().await()

        return if (!resultTask.isEmpty) {
            resultTask.toObjects(Favorite::class.java) as MutableList<Favorite>
        } else {
            mutableListOf()
        }
    }

    suspend fun findByUserIdAndCsId(userId: String, csId: Int): Favorite {
        val resultTask =
            FirestoreCollectionFilter.getFavoriteFirestoreCollection()
                .whereEqualTo("userId", userId)
                .whereEqualTo("csId", csId)
                .limit(1).get().await()

        return if (!resultTask.isEmpty) {
            resultTask.toObjects(Favorite::class.java) as Favorite
        } else {
            Favorite()
        }
    }

}