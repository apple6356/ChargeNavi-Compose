package com.seo.firestore.datasource.firestore

import com.seo.domain.datasource.FireStoreDataSource
import com.seo.firestore.common.FirestoreCollectionFilter
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.SearchHistory
import kotlinx.coroutines.tasks.await

class SearchHistoryDataSourceImpl: FireStoreDataSource<SearchHistory> {
    override suspend fun create(data: SearchHistory): FireResult<SearchHistory> {
        val searchHistory = FirestoreCollectionFilter.getSearchHistoryCollection().add(data).await().get().result.data as SearchHistory
        return FireResult.Success(searchHistory)
    }

    override suspend fun delete(id: Long): FireResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun update(data: SearchHistory): FireResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: String): SearchHistory {
        TODO("Not yet implemented")
    }

}