package com.seo.sesac.firestore.repository.local

import com.seo.sesac.firestore.datasource.local.LocalUserDatasource

class LocalUserRepository(
    private val localUserDatasource: LocalUserDatasource
) {
    suspend fun saveUserInfo(userId: String, nickname: String) =
        localUserDatasource.saveUserInfo(userId, nickname)

    fun getUserInfoFromDatastore() =
        localUserDatasource.getUserInfoFromDatastore()

    suspend fun clearUserInfo() =
        localUserDatasource.clearUserInfo()
}