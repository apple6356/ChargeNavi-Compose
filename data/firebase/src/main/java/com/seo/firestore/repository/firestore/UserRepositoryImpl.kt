package com.seo.firestore.repository.firestore

import com.seo.firestore.datasource.firestore.UserDataSourceImpl
import com.seo.sesac.data.entity.UserInfo
import com.seo.sesac.data.common.FireResult
import com.seo.domain.repository.UserRepository

/**
 * firestore user repository
 * */
class UserRepositoryImpl(private val userDataSourceImpl: UserDataSourceImpl) :
    UserRepository<UserInfo> {

    override suspend fun create(data: UserInfo) =
        userDataSourceImpl.create(data)

    override suspend fun update(userInfo: UserInfo): FireResult<Boolean> =
        userDataSourceImpl.update(userInfo)

    override suspend fun findById(id: String) =
        FireResult.Success(userDataSourceImpl.findById(id))

}