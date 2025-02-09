package com.seo.sesac.firestore.repository

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.SearchHistory
import com.seo.sesac.domain.repository.FireStoreRepository
import com.seo.sesac.firestore.datasource.SearchHistoryDataSourceImpl

class SearchHistoryRepository(private val searchHistoryDataSourceImpl: SearchHistoryDataSourceImpl):
    FireStoreRepository<SearchHistory> {

    override suspend fun create(data: SearchHistory) =
        FireResult.Success(searchHistoryDataSourceImpl.create(data))

    override suspend fun delete(id: Long): FireResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun update(data: SearchHistory): FireResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: String): FireResult<SearchHistory> {
        TODO("Not yet implemented")
    }

}