package com.seo.sesac.firestore.datasource

import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.SearchHistory
import com.seo.sesac.domain.datasource.FireStoreDataSource
import com.seo.sesac.firestore.common.FirestoreCollectionFilter
import kotlinx.coroutines.tasks.await

class SearchHistoryDataSourceImpl: FireStoreDataSource<SearchHistory> {
    override suspend fun create(data: SearchHistory): SearchHistory {
        val searchHistory = FirestoreCollectionFilter.getSearchHistoryCollection().add(data).await().get().result.data as SearchHistory
        return searchHistory
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