package com.seo.firestore.datasource.firestore

import com.seo.domain.datasource.UserDataSource
import com.seo.firestore.common.FirestoreCollectionFilter
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.UserInfo
import kotlinx.coroutines.tasks.await

/**
 * firestore user datasource
 * */
class UserDataSourceImpl: UserDataSource<UserInfo> {

    // runCatching 사용하여 가독성 좋게 예외처리

    /** user 추가 */
    override suspend fun create(data: UserInfo): FireResult<UserInfo> {

        val user = data.id?.let { findById(it) }

        return if (user?.id.equals("-1")) {
            // 신규 유저 등록
            val newUser = FirestoreCollectionFilter.getUserFirestoreCollection().add(data.toMap()).await().get().result.data as UserInfo
            FireResult.Success(newUser)
        } else {
            // 기존 유저 로그인
            FireResult.Success(user!!)
        }

    }

    /** user 정보 삭제 */
    override suspend fun delete(id: Long): FireResult<Boolean> {
        return FireResult.Success(true)
    }

    /** user 정보 갱신 */
    override suspend fun update(data: UserInfo): FireResult<Boolean> {

        return FireResult.Success(true)
    }

    /** id값으로 user 정보 조회*/
    override suspend fun findById(id: String): UserInfo {
        val resultTask = FirestoreCollectionFilter.getUserFirestoreCollection().whereEqualTo("id", id).get().await()

        return if (!resultTask.isEmpty) {
            val resultList = resultTask.toObjects(UserInfo::class.java) as MutableList<UserInfo>
            resultList.first()
        } else {
            UserInfo()
        }
    }

}

