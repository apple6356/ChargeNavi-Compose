package com.seo.sesac.firestore.repository

import com.seo.sesac.data.entity.UserInfo
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.domain.repository.FireStoreRepository
import com.seo.sesac.firestore.datasource.UserDataSourceImpl

/**
 * firestore user repository
 * */
class UserRepositoryImpl(private val userDataSourceImpl: UserDataSourceImpl) :
    FireStoreRepository<UserInfo> {

    override suspend fun create(data: UserInfo) =
        FireResult.Success(userDataSourceImpl.create(data))

    override suspend fun delete(id: Long): FireResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun update(data: UserInfo): FireResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: String) =
        FireResult.Success(userDataSourceImpl.findById(id))

}