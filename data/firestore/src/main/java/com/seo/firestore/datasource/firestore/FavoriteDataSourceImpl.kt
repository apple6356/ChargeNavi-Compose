package com.seo.firestore.datasource.firestore

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.Favorite
import com.seo.domain.datasource.FavoriteDataSource
import com.seo.firestore.common.FirestoreCollectionFilter
import kotlinx.coroutines.tasks.await

/**
 * firestore favorite datasource
 * 25-02-17 수정: 즐겨찾기 시 충전소의 일부 정보 함께 저장
 * */
class FavoriteDataSourceImpl: FavoriteDataSource<Favorite> {

    /**
     * 즐겨찾기 추가
     * */
    override suspend fun create(data: Favorite): FireResult<Favorite> = runCatching {

        val addRef = FirestoreCollectionFilter.getFavoriteFirestoreCollection().add(data).await()
        val result = addRef.get().await()

        val favoriteInfo = result.toObject(Favorite::class.java) as Favorite
        println("favorite create : $favoriteInfo")

        FireResult.Success(favoriteInfo)
    }.getOrElse {
        FireResult.Failure(Exception("즐겨찾기 추가 중 오류 발생: ${it.message}"))
    }

    /**
     * 즐겨찾기 삭제
     * */
    override suspend fun delete(userId: String, csId: String): FireResult<Boolean> = runCatching {
//        val favoriteDocRef = FirestoreCollectionFilter.getFavoriteFirestoreCollection().document(userId)

//        val resultTask = favoriteDocRef.get().await()

        val resultTask = FirestoreCollectionFilter.getFavoriteFirestoreCollection()
            .whereEqualTo("userId", userId)
            .whereEqualTo("csId", csId)
            .get().await()

        if (!resultTask.isEmpty()) {
            FirestoreCollectionFilter.getFavoriteFirestoreCollection().document(resultTask.documents.first().id).delete()
            FireResult.Success(true)
        } else {
            FireResult.Failure(Exception("해당 문서를 찾지 못 했습니다."))
        }
    }.getOrElse {
        FireResult.Failure(Exception("즐겨찾기 삭제 중 오류 발생: ${it.message}"))
    }

    /**
     * 유저 즐겨찾기 리스트
     * */
    override suspend fun findByUserId(userId: String): FireResult<MutableList<Favorite>> = runCatching {
        val resultTask =  FirestoreCollectionFilter.getFavoriteFirestoreCollection()
            .whereEqualTo("userId", userId).get().await()

        if (!resultTask.isEmpty) {
            val favoriteList = resultTask.toObjects(Favorite::class.java) as MutableList<Favorite>
            FireResult.Success(favoriteList)
        } else {
            FireResult.Failure(Exception("검색 결과가 없습니다."))
        }
    }.getOrElse {
        FireResult.Failure(Exception("즐겨찾기 목록을 불러오는 중 오류가 발생했습니다."))
    }

    /**
     * 유저가 충전소를 즐겨찾기 했는지 확인할 때 사용
     * */
    override suspend fun findByUserIdAndCsId(userId: String, csId: String): FireResult<Favorite> = runCatching {
        val resultTask = FirestoreCollectionFilter.getFavoriteFirestoreCollection()
            .whereEqualTo("userId", userId).whereEqualTo("csId", csId).get().await()

        val result = resultTask.toObjects(Favorite::class.java).firstOrNull()

        if (result != null) {
            FireResult.Success(result)
        } else {
            FireResult.Failure(Exception("검색 결과가 없습니다."))
        }
    }.getOrElse {
        FireResult.Failure(Exception("즐겨찾기 정보를 불러올 수 없습니다."))
    }


}