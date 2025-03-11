package com.seo.firestore.datasource.firestore
import com.seo.firestore.common.FirestoreCollectionFilter
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.UserInfo
import kotlinx.coroutines.tasks.await

/**
 * firestore user datasource
 * */
class UserDataSourceImpl {

    // runCatching 사용하여 가독성 좋게 예외처리

    /** user 추가 */
    suspend fun create(data: UserInfo): FireResult<UserInfo> {

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

    /** user 정보 갱신 */
    suspend fun update(userInfo: UserInfo): FireResult<Boolean> = runCatching {
        val resultTask = FirestoreCollectionFilter.getUserFirestoreCollection()
            .whereEqualTo("id", userInfo.id)
            .get().await()

        if (!resultTask.isEmpty) {
            resultTask.documents.first().reference.update(mapOf("nickname" to userInfo.nickname)).await()

            FireResult.Success(true)
        } else {
            FireResult.Failure(Exception("유저 정보를 찾을 수 없습니다."))
        }
    }.getOrElse {
            FireResult.Failure(Exception("정보 업데이트 중 오류 발생 ${it.message}"))
    }

    /** id값으로 user 정보 조회*/
    suspend fun findById(id: String): UserInfo {
        val resultTask = FirestoreCollectionFilter.getUserFirestoreCollection().whereEqualTo("id", id).get().await()

        return if (!resultTask.isEmpty) {
            val resultList = resultTask.toObjects(UserInfo::class.java) as MutableList<UserInfo>
            resultList.first()
        } else {
            UserInfo()
        }
    }

}

