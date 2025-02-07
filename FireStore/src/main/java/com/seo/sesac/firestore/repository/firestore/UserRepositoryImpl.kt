package com.seo.sesac.firestore.repository.firestore

import com.seo.sesac.data.entity.UserInfo
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.firestore.repository.FireStoreRepository
import com.seo.sesac.firestore.datasource.firestore.UserDataSourceImpl

/**
 * firestore user repository
 * */
class UserRepositoryImpl(private val userDataSourceImpl: UserDataSourceImpl):
    FireStoreRepository<UserInfo> {

    override suspend fun create(data: UserInfo) = FireResult.Success(userDataSourceImpl.create(data))

//    override suspend fun delete(id: Long) = userDataSourceImpl.delete(id)

//    override suspend fun update(data: UserInfo) = userDataSourceImpl.update(data)

    override suspend fun findById(id: String) = FireResult.Success(userDataSourceImpl.findById(id))

}