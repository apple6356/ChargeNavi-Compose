package com.seo.sesac.chargenavi.viewmodel

import androidx.lifecycle.ViewModel
import com.seo.sesac.data.common.FireResult
import com.seo.sesac.data.entity.SearchHistory
import com.seo.sesac.firestore.datasource.firestore.SearchHistoryDataSourceImpl
import com.seo.sesac.firestore.repository.firestore.SearchHistoryRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * main viewmodel 에 통합
 * */
class SearchHistoryViewModel(
    private val searchHistoryRepository: SearchHistoryRepositoryImpl = SearchHistoryRepositoryImpl(
        SearchHistoryDataSourceImpl()
    )
): ViewModel() {

    val _searchHistory =
        MutableStateFlow<FireResult<MutableList<SearchHistory>>>(FireResult.DummyConstructor)
    val searchHistory get() = _searchHistory

    fun addSearchHistory(keyword: String) {

    }

}