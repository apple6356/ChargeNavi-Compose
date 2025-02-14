package com.seo.sesac.firestore.repository.firestore

import com.seo.sesac.data.entity.UserInfo
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.domain.repository.FireStoreRepository
import com.seo.sesac.domain.repository.UserRepository
import com.seo.sesac.firestore.datasource.firestore.UserDataSourceImpl

/**
 * firestore user repository
 * */
class UserRepositoryImpl(private val userDataSourceImpl: UserDataSourceImpl) :
    UserRepository<UserInfo> {

    override suspend fun create(data: UserInfo) =
        userDataSourceImpl.create(data)

    override suspend fun delete(id: Long): FireResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun update(data: UserInfo): FireResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: String) =
        FireResult.Success(userDataSourceImpl.findById(id))

}