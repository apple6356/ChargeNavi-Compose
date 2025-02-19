package com.seo.sesac.chargenavi.viewmodel

import androidx.lifecycle.ViewModel
import com.seo.sesac.data.common.FireResult
import com.seo.firestore.datasource.firestore.SearchHistoryDataSourceImpl
import com.seo.firestore.repository.firestore.SearchHistoryRepositoryImpl
import com.seo.sesac.data.entity.SearchHistory
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * main viewmodel 에 통합
 * */
class SearchHistoryViewModel(
    private val searchHistoryRepository: SearchHistoryRepositoryImpl = SearchHistoryRepositoryImpl(SearchHistoryDataSourceImpl())
): ViewModel() {

    val _searchHistory =
        MutableStateFlow<FireResult<MutableList<SearchHistory>>>(FireResult.DummyConstructor)
    val searchHistory get() = _searchHistory

    fun addSearchHistory(keyword: String) {

    }

}