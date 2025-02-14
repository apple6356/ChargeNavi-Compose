package com.seo.sesac.firestore.datasource.firestore

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Favorite
import com.seo.sesac.data.datasource.FavoriteDataSource
import com.seo.sesac.firestore.common.FirestoreCollectionFilter
import kotlinx.coroutines.tasks.await

/**
 * firestore favorite datasource
 * */
class FavoriteDataSourceImpl: FavoriteDataSource<Favorite> {

    /**
     * 즐겨찾기 추가
     * */
    override suspend fun create(data: Favorite): FireResult<Favorite> = runCatching {
        val reference = FirestoreCollectionFilter.getFavoriteFirestoreCollection().add(data).await()
        val snapshot = reference.get().await()

        val favoriteInfo = snapshot.toObject(Favorite::class.java) as Favorite
        println("favorite create : $favoriteInfo")
        FireResult.Success(favoriteInfo)
    }.getOrElse {
        FireResult.Failure(Exception("즐겨찾기 추가 중 오류 발생"))
    }

    /**
     * 즐겨찾기 삭제
     * */
    override suspend fun delete(userId: String, csId: String): FireResult<Boolean> = runCatching {
        val resultTask = FirestoreCollectionFilter.getFavoriteFirestoreCollection()
            .whereEqualTo("userId", userId)
            .whereEqualTo("csId", csId)
            .get().await()

        if (resultTask.isEmpty) {
            FireResult.Failure(Exception("해당 문서를 찾지 못 했습니다."))
        } else {
            FirestoreCollectionFilter.getFavoriteFirestoreCollection().document(resultTask.documents.first().id).delete()
            FireResult.Success(true)
        }
    }.getOrElse {
        FireResult.Failure(Exception("즐겨찾기 삭제 중 오류 발생"))
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

    suspend fun findByUserId(userId: String): FireResult<MutableList<Favorite>> = runCatching {
        val resultTask =  FirestoreCollectionFilter.getFavoriteFirestoreCollection().whereEqualTo("userId", userId).get().await()

        if (!resultTask.isEmpty) {
            val favoriteList = resultTask.toObjects(Favorite::class.java) as MutableList<Favorite>
            FireResult.Success(favoriteList)
        } else {
            FireResult.Failure(Exception("검색 결과가 없습니다."))
        }
    }.getOrElse {
        FireResult.Failure(Exception("즐겨찾기 목록을 불러오는 중 오류가 발생했습니다."))
    }

    suspend fun findByUserIdAndCsId(userId: String, csId: String): FireResult<Favorite> {
        val resultTask =
            FirestoreCollectionFilter.getFavoriteFirestoreCollection()
                .whereEqualTo("userId", userId)
                .whereEqualTo("csId", csId)
                .get().await()

        return if (!resultTask.isEmpty) {
            val result = resultTask.toObjects(Favorite::class.java).firstOrNull()
            if(result != null) {
                FireResult.Success(result)
            } else {
                FireResult.Failure(Exception("검색 결과가 없습니다."))
            }
        } else {
            FireResult.Failure(Exception("정보가 없습니다."))
        }
    }

}